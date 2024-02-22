package net.unir.missi.desarrollowebfullstack.bookabook.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatchException;
import com.github.fge.jsonpatch.mergepatch.JsonMergePatch;
import lombok.extern.slf4j.Slf4j;
import net.unir.missi.desarrollowebfullstack.bookabook.DTO.api.BookResponse;
import net.unir.missi.desarrollowebfullstack.bookabook.DTO.memory.Book;
import net.unir.missi.desarrollowebfullstack.bookabook.converter.api.BookAPIConverter;
import net.unir.missi.desarrollowebfullstack.bookabook.converter.memory.BookMemoryConverter;
import net.unir.missi.desarrollowebfullstack.bookabook.model.AuthorDocument;
import net.unir.missi.desarrollowebfullstack.bookabook.model.BookDocument;
import net.unir.missi.desarrollowebfullstack.bookabook.repository.AuthorRepository;
import net.unir.missi.desarrollowebfullstack.bookabook.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
public class BookService implements IBookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BookMemoryConverter bookMemoryConverter;

    @Override
    public List<Book> getBooks(String isbn, String name, String language, String description,
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
        return books.stream().map(
                (BookDocument b) ->
                {
                    return this.bookMemoryConverter.fromDocument(b);
                }
        )
                .collect(Collectors.toList());
    }

    @Override
    public Book getBook(String bookId) {
        return this.bookMemoryConverter.fromDocument(this.bookRepository.getById(Long.valueOf(bookId)));
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
    public Book createBook(Book request) {

        if (request != null && StringUtils.hasLength(request.isbn().trim())
                && StringUtils.hasLength(request.name().trim())
                && StringUtils.hasLength(request.language().trim())
                && StringUtils.hasLength(request.description().trim())
                && StringUtils.hasLength(request.category().trim())
                && request.authorDocument() != 0) {

            // Get the author to check if exists
            AuthorDocument authorDocument = authorRepository.getById(request.authorDocument());

            if (authorDocument != null) {
                // Author exists and book can be created

                BookDocument newBook = BookDocument.builder()
                        .isbn(request.isbn())
                        .name(request.name())
                        .language(request.language())
                        .description(request.description())
                        .category(request.category())
                        .authorDocument(authorDocument).build();

                BookDocument createdBook = bookRepository.save(newBook);

                return this.bookMemoryConverter.fromDocument(createdBook);
            } else {
                // Author not exists
                return null;
            }
        } else {
            return null;
        }
    }

    @Override
    public Book updateBookAttributes(String bookId, Book request) {
        BookDocument book = bookRepository.getById(Long.valueOf(bookId));
        if (book == null) {
            return null;
        }

        if (request.name() != null)
        {
            book.setName(request.name());
        }
        if (request.category() != null)
        {
            book.setCategory(request.category());
        }
        if (request.description() != null)
        {
            book.setDescription(request.description());
        }
        if (request.isbn() != null)
        {
            book.setIsbn(request.isbn());
        }
        if (request.language()!= null)
        {
            book.setLanguage(request.language());
        }

        return this.bookMemoryConverter.fromDocument(bookRepository.save(book));
    }

    @Override
    public Book updateBook(String bookId, Book updateRequest) {
        BookDocument book = bookRepository.getById(Long.valueOf(bookId));
        // Check if author exists
        if (book == null) {
            return null;
        }

        BookDocument bookDocument = this.bookMemoryConverter.toDocument(updateRequest);
        bookDocument.setId(book.getId());
        return this.bookMemoryConverter.fromDocument(this.bookRepository.save(bookDocument));
    }

}
