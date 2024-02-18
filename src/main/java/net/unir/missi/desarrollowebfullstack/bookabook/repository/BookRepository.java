package net.unir.missi.desarrollowebfullstack.bookabook.repository;

import net.unir.missi.desarrollowebfullstack.bookabook.model.sql.Author;
import net.unir.missi.desarrollowebfullstack.bookabook.config.search.SearchCriteria;
import net.unir.missi.desarrollowebfullstack.bookabook.config.search.SearchOperation;
import net.unir.missi.desarrollowebfullstack.bookabook.config.search.SearchStatement;
import net.unir.missi.desarrollowebfullstack.bookabook.model.sql.Book;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.LinkedList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class BookRepository {

    private final BookJpaRepository repository;

    public List<Book> getBooks() {
        List<Book> ret = new LinkedList<>();
        repository.findAll().forEach(ret::add);
        return ret;
    }

    public Book getById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public Book save(Book book) {
        return repository.save(book);
    }

    public void delete(Book book) {
        repository.delete(book);
    }

    public List<Book> search(String isbn, String name, String language,
                             String description, String category, Author author) {
        SearchCriteria<Book> spec = new SearchCriteria<>();
        if (StringUtils.isNotBlank(isbn)) {
            spec.add(new SearchStatement("isbn", isbn, SearchOperation.MATCH));
        }

        if (StringUtils.isNotBlank(name)) {
            spec.add(new SearchStatement("name", name, SearchOperation.MATCH));
        }

        if (StringUtils.isNotBlank(language)) {
            spec.add(new SearchStatement("language", language, SearchOperation.EQUAL));
        }

        if (StringUtils.isNotBlank(description)) {
            spec.add(new SearchStatement("description", description, SearchOperation.MATCH));
        }

        if (StringUtils.isNotBlank(category)) {
            spec.add(new SearchStatement("category", category, SearchOperation.EQUAL));
        }

        if (author != null) {
            spec.add(new SearchStatement("author", author, SearchOperation.EQUAL));
        }
        // TODO broken search in book repo
        List<Book> ret = new LinkedList<>();
        repository.findAll().forEach(ret::add);
        return ret;
    }

}