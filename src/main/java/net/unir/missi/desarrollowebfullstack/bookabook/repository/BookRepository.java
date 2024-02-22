package net.unir.missi.desarrollowebfullstack.bookabook.repository;

import net.unir.missi.desarrollowebfullstack.bookabook.model.document.AuthorDocument;
import net.unir.missi.desarrollowebfullstack.bookabook.config.search.SearchCriteria;
import net.unir.missi.desarrollowebfullstack.bookabook.config.search.SearchOperation;
import net.unir.missi.desarrollowebfullstack.bookabook.config.search.SearchStatement;
import net.unir.missi.desarrollowebfullstack.bookabook.model.document.BookDocument;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.LinkedList;
import java.util.List;

@RequiredArgsConstructor
public class BookRepository {

    private final BookElasticRepository repository;

    public List<BookDocument> getBooks() {
        List<BookDocument> ret = new LinkedList<>();
        repository.findAll().forEach(ret::add);
        return ret;
    }

    public BookDocument getById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public BookDocument save(BookDocument book) {
        return repository.save(book);
    }

    public void delete(BookDocument book) {
        repository.delete(book);
    }

    public List<BookDocument> search(String isbn, String name, String language,
                                     String description, String category, AuthorDocument authorDocument) {
        SearchCriteria<BookDocument> spec = new SearchCriteria<>();
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

        if (authorDocument != null) {
            spec.add(new SearchStatement("author", authorDocument, SearchOperation.EQUAL));
        }
        // TODO broken search in book repo
        List<BookDocument> ret = new LinkedList<>();
        repository.findAll().forEach(ret::add);
        return ret;
    }

}