package net.unir.missi.desarrollowebfullstack.bookabook.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatchException;
import com.github.fge.jsonpatch.mergepatch.JsonMergePatch;
import net.unir.missi.desarrollowebfullstack.bookabook.model.api.BookResponse;
import net.unir.missi.desarrollowebfullstack.bookabook.model.document.AuthorDocument;
import net.unir.missi.desarrollowebfullstack.bookabook.repository.BookRepository;
import net.unir.missi.desarrollowebfullstack.bookabook.repository.AuthorRepository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import net.unir.missi.desarrollowebfullstack.bookabook.model.document.BookDocument;
import net.unir.missi.desarrollowebfullstack.bookabook.model.api.BookRequest;

@Service
@Slf4j
public class BookService implements IBookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public List<BookResponse> getBooks(String isbn, String name, String language, String description,
                                       String category, Long authorId) {

        List<BookDocument> books;
        if (StringUtils.hasLength(isbn) || StringUtils.hasLength(name) ||
                StringUtils.hasLength(language) || StringUtils.hasLength(description)
                || StringUtils.hasLength(category) || authorId != null) {
            AuthorDocument authorDocument;
            if (authorId != null) {
                // Get author to search
                authorDocument = authorRepository.getById(authorId);
                // If the author not exit, return null
                if (authorDocument == null) {
                    return null;
                }
            } else {
                authorDocument = null;
            }
            books = bookRepository.search(isbn, name, language, description, category, authorDocument);
        } else {
            books = bookRepository.getBooks();
        }

        // Process retrieved books to return expected response
        List<BookResponse> booksResponse = new ArrayList<>();
        if (books != null) {
            for (BookDocument book : books) {
                BookResponse bookResponse = BookResponse.builder()
                        .id(book.getId())
                        .isbn(book.getIsbn())
                        .name(book.getName())
                        .language(book.getLanguage())
                        .description(book.getDescription())
                        .category(book.getCategory())
                        .authorId(book.getAuthorDocument().getId()).build();
                booksResponse.add(bookResponse);
            }
        }

        return booksResponse.isEmpty() ? null : booksResponse;
    }

    @Override
    public BookResponse getBook(String bookId) {

        // Get the book from database
        BookDocument book =  bookRepository.getById(Long.valueOf(bookId));
        // Transform the book model object to response object
        if (book != null) {
            BookResponse bookResponse = BookResponse.builder()
                    .id(book.getId())
                    .isbn(book.getIsbn())
                    .name(book.getName())
                    .language(book.getLanguage())
                    .description(book.getDescription())
                    .category(book.getCategory())
                    .authorId(book.getAuthorDocument().getId()).build();
            return bookResponse;
        } else {
            return null;
        }
    }

    @Override
    public Boolean removeBook(String bookId) {

        BookDocument book = bookRepository.getById(Long.valueOf(bookId));

        if (book != null) {
            // Remove the association with author
            book.setAuthorDocument(null);
            bookRepository.save(book);
            // Delete the book
            bookRepository.delete(book);
            return Boolean.TRUE;
        } else {
            return Boolean.FALSE;
        }
    }

    @Override
    public BookResponse createBook(BookRequest request) {

        if (request != null && StringUtils.hasLength(request.getIsbn().trim())
                && StringUtils.hasLength(request.getName().trim())
                && StringUtils.hasLength(request.getLanguage().trim())
                && StringUtils.hasLength(request.getDescription().trim())
                && StringUtils.hasLength(request.getCategory().trim())
                && request.getAuthorId() != 0) {

            // Get the author to check if exists
            AuthorDocument authorDocument = authorRepository.getById(request.getAuthorId());

            if (authorDocument != null) {
                // Author exists and book can be created

                BookDocument newBook = BookDocument.builder()
                        .isbn(request.getIsbn())
                        .name(request.getName())
                        .language(request.getLanguage())
                        .description(request.getDescription())
                        .category(request.getCategory())
                        .authorDocument(authorDocument).build();

                BookDocument createdBook = bookRepository.save(newBook);
                // Return the created book in response format
                BookResponse createdBookResponse = BookResponse.builder()
                        .id(createdBook.getId())
                        .isbn(createdBook.getIsbn())
                        .name(createdBook.getName())
                        .language(createdBook.getLanguage())
                        .description(createdBook.getDescription())
                        .category(createdBook.getCategory())
                        .authorId(createdBook.getAuthorDocument().getId()).build();
                return createdBookResponse;
            } else {
                // Author not exists
                return null;
            }
        } else {
            return null;
        }
    }

    @Override
    public BookResponse updateBook(String bookId, String request) {

        //PATCH se implementa en este caso mediante Merge Patch: https://datatracker.ietf.org/doc/html/rfc7386
        BookDocument book = bookRepository.getById(Long.valueOf(bookId));
        if (book != null) {
            // Transform book model into request format
            BookRequest bookRequest = BookRequest.builder()
                    .isbn(book.getIsbn())
                    .name(book.getName())
                    .language(book.getLanguage())
                    .description(book.getDescription())
                    .category(book.getCategory())
                    .authorId(book.getAuthorDocument().getId()).build();
            try {
                JsonMergePatch jsonMergePatch = JsonMergePatch.fromJson(objectMapper.readTree(request));
                JsonNode target = jsonMergePatch.apply(objectMapper.readTree(objectMapper.writeValueAsString(bookRequest)));
                BookRequest patchedBookRequest = objectMapper.treeToValue(target, BookRequest.class);
                if (!Objects.equals(patchedBookRequest.getAuthorId(),book.getAuthorDocument().getId())) {
                    AuthorDocument authorDocument = authorRepository.getById(patchedBookRequest.getAuthorId());
                    if (authorDocument != null) {
                        // Create the book model object
                        BookDocument patchedBook = BookDocument.builder()
                                .id(book.getId())
                                .isbn(patchedBookRequest.getIsbn())
                                .name(patchedBookRequest.getName())
                                .language(patchedBookRequest.getLanguage())
                                .description(patchedBookRequest.getDescription())
                                .category(patchedBookRequest.getCategory())
                                .authorDocument(authorDocument).build();
                        bookRepository.save(patchedBook);
                        // Transform updated book to return format
                        BookResponse updatedBookResponse = BookResponse.builder()
                                .id(patchedBook.getId())
                                .isbn(patchedBook.getIsbn())
                                .name(patchedBook.getName())
                                .language(patchedBook.getLanguage())
                                .description(patchedBook.getDescription())
                                .category(patchedBook.getCategory())
                                .authorId(patchedBook.getAuthorDocument().getId()).build();
                        return updatedBookResponse;
                    } else {
                        return null;
                    }
                } else {
                    book.update(patchedBookRequest);
                    bookRepository.save(book);
                    // Transform updated book to return format
                    BookResponse updatedBookResponse = BookResponse.builder()
                            .id(book.getId())
                            .isbn(book.getIsbn())
                            .name(book.getName())
                            .language(book.getLanguage())
                            .description(book.getDescription())
                            .category(book.getCategory())
                            .authorId(book.getAuthorDocument().getId()).build();
                    return updatedBookResponse;
                }
            } catch (JsonProcessingException | JsonPatchException e) {
                log.error("Error updating book {}", bookId, e);
                return null;
            }
        } else {
            return null;
        }
    }

    @Override
    public BookResponse updateBook(String bookId, BookRequest updateRequest) {
        BookDocument book = bookRepository.getById(Long.valueOf(bookId));
        // Check if author is different and exists
        if (book != null) {
            if (!Objects.equals(updateRequest.getAuthorId(), book.getAuthorDocument().getId())) {
                // Check if author exists
                AuthorDocument authorDocument = authorRepository.getById(updateRequest.getAuthorId());
                if (authorDocument != null) {
                    book.setAuthorDocument(authorDocument);
                    book.update(updateRequest);
                    bookRepository.save(book);
                } else {
                    return null;
                }
            } else {
                book.update(updateRequest);
                bookRepository.save(book);
            }
            // Transform updated book to return format
            BookResponse updatedBookResponse = BookResponse.builder()
                    .id(book.getId())
                    .isbn(book.getIsbn())
                    .name(book.getName())
                    .language(book.getLanguage())
                    .description(book.getDescription())
                    .category(book.getCategory())
                    .authorId(book.getAuthorDocument().getId()).build();
            return updatedBookResponse;
        } else {
            return null;
        }
    }

}
