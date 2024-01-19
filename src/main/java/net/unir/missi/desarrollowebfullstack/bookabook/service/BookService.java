package net.unir.missi.desarrollowebfullstack.bookabook.service;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatchException;
import com.github.fge.jsonpatch.mergepatch.JsonMergePatch;
import net.unir.missi.desarrollowebfullstack.bookabook.data.model.api.BookResponse;
import net.unir.missi.desarrollowebfullstack.bookabook.data.repository.BookRepository;
import net.unir.missi.desarrollowebfullstack.bookabook.data.repository.AuthorRepository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import net.unir.missi.desarrollowebfullstack.bookabook.data.model.sql.Book;
import net.unir.missi.desarrollowebfullstack.bookabook.data.model.sql.Author;
import net.unir.missi.desarrollowebfullstack.bookabook.data.model.api.BookRequest;

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

        List<Book> books;
        if (StringUtils.hasLength(isbn) || StringUtils.hasLength(name) ||
                StringUtils.hasLength(language) || StringUtils.hasLength(description)
                || StringUtils.hasLength(category) || authorId != null) {
            Author author;
            if (authorId != null) {
                // Get author to search
                author = authorRepository.findById(authorId).orElse(null);
                // If the author not exit, return null
                if (author == null) {
                    return null;
                }
            } else {
                author = null;
            }
            books = bookRepository.search(isbn, name, language, description, category, author);
        } else {
            books = bookRepository.getBooks();
        }

        // Process retrieved books to return expected response
        List<BookResponse> booksResponse = new ArrayList<>();
        if (books != null) {
            for (Book book : books) {
                BookResponse bookResponse = BookResponse.builder()
                        .id(book.getId())
                        .isbn(book.getIsbn())
                        .name(book.getName())
                        .language(book.getLanguage())
                        .description(book.getDescription())
                        .category(book.getCategory())
                        .authorId(book.getAuthor().getId()).build();
                booksResponse.add(bookResponse);
            }
        }

        return booksResponse.isEmpty() ? null : booksResponse;
    }

    @Override
    public BookResponse getBook(String bookId) {

        // Get the book from database
        Book book =  bookRepository.getById(Long.valueOf(bookId));
        // Transform the book model object to response object
        if (book != null) {
            BookResponse bookResponse = BookResponse.builder()
                    .id(book.getId())
                    .isbn(book.getIsbn())
                    .name(book.getName())
                    .language(book.getLanguage())
                    .description(book.getDescription())
                    .category(book.getCategory())
                    .authorId(book.getAuthor().getId()).build();
            return bookResponse;
        } else {
            return null;
        }
    }

    @Override
    public Boolean removeBook(String bookId) {

        Book book = bookRepository.getById(Long.valueOf(bookId));

        if (book != null) {
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
            Author author = authorRepository.findById(request.getAuthorId()).orElse(null);
            if (author != null) {
                // Author exists and book can be created
                Book newBook = Book.builder()
                        .isbn(request.getIsbn())
                        .name(request.getName())
                        .language(request.getLanguage())
                        .description(request.getDescription())
                        .category(request.getCategory())
                        .author(author).build();
                Book createdBook = bookRepository.save(newBook);
                // Return the created book in response format
                BookResponse createdBookResponse = BookResponse.builder()
                        .id(createdBook.getId())
                        .isbn(createdBook.getIsbn())
                        .name(createdBook.getName())
                        .language(createdBook.getLanguage())
                        .description(createdBook.getDescription())
                        .category(createdBook.getCategory())
                        .authorId(createdBook.getAuthor().getId()).build();
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
    public Book updateBook(String bookId, String request) {

        //PATCH se implementa en este caso mediante Merge Patch: https://datatracker.ietf.org/doc/html/rfc7386
        Book book = bookRepository.getById(Long.valueOf(bookId));
        if (book != null) {
            try {
                JsonMergePatch jsonMergePatch = JsonMergePatch.fromJson(objectMapper.readTree(request));
                JsonNode target = jsonMergePatch.apply(objectMapper.readTree(objectMapper.writeValueAsString(book)));
                Book patched = objectMapper.treeToValue(target, Book.class);
                bookRepository.save(patched);
                return patched;
            } catch (JsonProcessingException | JsonPatchException e) {
                log.error("Error updating book {}", bookId, e);
                return null;
            }
        } else {
            return null;
        }
    }

    @Override
    public Book updateBook(String bookId, BookRequest updateRequest) {
        Book book = bookRepository.getById(Long.valueOf(bookId));
        if (book != null) {
            book.update(updateRequest);
            bookRepository.save(book);
            return book;
        } else {
            return null;
        }
    }

}
