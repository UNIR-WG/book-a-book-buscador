package net.unir.missi.desarrollowebfullstack.bookabook.service;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.unir.missi.desarrollowebfullstack.bookabook.data.model.api.BookDto;
import net.unir.missi.desarrollowebfullstack.bookabook.data.repository.BookRepository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import net.unir.missi.desarrollowebfullstack.bookabook.data.model.sql.BookModel;
import net.unir.missi.desarrollowebfullstack.bookabook.data.model.api.CreateBookRequest;

@Service
@Slf4j
public class BookServiceImpl implements BookService{

    @Autowired
    private BookRepository repository;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public List<BookModel> getBooks(String isbn, String name, String language, String description,
                                    String category, Long authorId) {

        if (StringUtils.hasLength(isbn) || StringUtils.hasLength(name) ||
                StringUtils.hasLength(language) || StringUtils.hasLength(description)
                || StringUtils.hasLength(category) || authorId != 0) {
            return repository.search(isbn, name, language, description, category, authorId);
        }

        List<BookModel> books = repository.getBooks();
        return books.isEmpty() ? null : books;
    }

    @Override
    public BookModel getBook(String bookId) {
        return repository.getById(Long.valueOf(bookId));
    }

    @Override
    public Boolean removeBook(String bookId) {

        BookModel book = repository.getById(Long.valueOf(bookId));

        if (book != null) {
            repository.delete(book);
            return Boolean.TRUE;
        } else {
            return Boolean.FALSE;
        }
    }

    @Override
    public BookModel createBook(CreateBookRequest request) {

        if (request != null && StringUtils.hasLength(request.getIsbn().trim())
                && StringUtils.hasLength(request.getName().trim())
                && StringUtils.hasLength(request.getLanguage().trim())
                && StringUtils.hasLength(request.getDescription().trim())
                && StringUtils.hasLength(request.getCategory().trim())
                && request.getAuthorId() != 0) {

            BookModel book = BookModel.builder().isbn(request.getIsbn())
                    .name(request.getName())
                    .language(request.getLanguage())
                    .description(request.getDescription())
                    .category(request.getCategory())
                    .authorId(request.getAuthorId()).build();

            return repository.save(book);
        } else {
            return null;
        }
    }

    @Override
    public BookModel updateBook(String bookId, String request) {

        //PATCH se implementa en este caso mediante Merge Patch: https://datatracker.ietf.org/doc/html/rfc7386
        BookModel book = repository.getById(Long.valueOf(bookId));
        if (book != null) {
            try {
                JsonMergePatch jsonMergePatch = JsonMergePatch.fromJson(objectMapper.readTree(request));
                JsonNode target = jsonMergePatch.apply(objectMapper.readTree(objectMapper.writeValueAsString(book)));
                BookModel patched = objectMapper.treeToValue(target, BookModel.class);
                repository.save(patched);
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
    public BookModel updateBook(String bookId, BookDto updateRequest) {
        BookModel book = repository.getById(Long.valueOf(bookId));
        if (book != null) {
            book.update(updateRequest);
            repository.save(book);
            return book;
        } else {
            return null;
        }
    }

}
