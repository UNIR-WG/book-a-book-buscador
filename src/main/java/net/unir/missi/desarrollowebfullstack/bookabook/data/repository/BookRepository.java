package net.unir.missi.desarrollowebfullstack.bookabook.data.repository;

import net.unir.missi.desarrollowebfullstack.bookabook.data.utils.SearchCriteria;
import net.unir.missi.desarrollowebfullstack.bookabook.data.utils.SearchOperation;
import net.unir.missi.desarrollowebfullstack.bookabook.data.utils.SearchStatement;
import net.unir.missi.desarrollowebfullstack.bookabook.data.model.sql.BookModel;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class BookRepository {

    private final BookJpaRepository repository;

    public List<BookModel> getBooks() {
        return repository.findAll();
    }

    public BookModel getById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public BookModel save(BookModel book) {
        return repository.save(book);
    }

    public void delete(BookModel book) {
        repository.delete(book);
    }

    public List<BookModel> search(String isbn, String name, String language,
                                  String description, String category, Long authorId) {
        SearchCriteria<BookModel> spec = new SearchCriteria<>();
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

        if (authorId != 0) {
            spec.add(new SearchStatement("authorId", authorId, SearchOperation.EQUAL));
        }
        return repository.findAll(spec);
    }

}