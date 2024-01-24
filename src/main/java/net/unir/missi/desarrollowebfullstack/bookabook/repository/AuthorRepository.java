package net.unir.missi.desarrollowebfullstack.bookabook.repository;

import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.unir.missi.desarrollowebfullstack.bookabook.model.sql.Author;
import net.unir.missi.desarrollowebfullstack.bookabook.model.sql.Book;
import net.unir.missi.desarrollowebfullstack.bookabook.config.search.SearchCriteria;
import net.unir.missi.desarrollowebfullstack.bookabook.config.search.SearchOperation;
import net.unir.missi.desarrollowebfullstack.bookabook.config.search.SearchStatement;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
@Slf4j
public class AuthorRepository {

    private final AuthorJpaRepository authorJpaRepository;


    public List<Author> findAll() {
        return authorJpaRepository.findAll();
    }
    public Author getById(Long id) {
        return authorJpaRepository.findById(id).orElse(null);
    }

    public Author save(Author author) {
        return authorJpaRepository.save(author);
    }

    public void delete(Author author) {
        authorJpaRepository.delete(author);
    }

    private void addSearchStatement(SearchCriteria<Author> spec, String key, String value, SearchOperation operation) {
        if (StringUtils.isNotBlank(key)) {
            spec.add(new SearchStatement(key, value, operation));
        }
    }

    public List<Author> search(String firstName, String lastName, LocalDate birthDate, String nationality, String email, String webSite, String biography, Book booksWritten) {

        SearchCriteria<Author> spec = new SearchCriteria<>();
        List<Author> listAuthor = authorJpaRepository.findAll(spec);
        List<Author> filteredAuthors;

        this.addSearchStatement(spec, "firstName", firstName, SearchOperation.MATCH);
        this.addSearchStatement(spec, "lastName", lastName, SearchOperation.MATCH);
        this.addSearchStatement(spec, "nationality", nationality, SearchOperation.EQUAL);
        this.addSearchStatement(spec, "email", email, SearchOperation.EQUAL);
        this.addSearchStatement(spec, "webSite", webSite, SearchOperation.MATCH);
        this.addSearchStatement(spec, "biography", biography, SearchOperation.MATCH);

        if(birthDate!=null) {
            if (StringUtils.isNotBlank(String.valueOf(birthDate))) {
                spec.add(new SearchStatement("birthDate", birthDate, SearchOperation.EQUAL));
            }
        }

        if (booksWritten != null) {
            filteredAuthors = listAuthor.stream()
                    .filter(author -> author.getBooksWritten().stream()
                            .anyMatch(book -> Objects.equals(booksWritten.getId(), book.getId())))
                    .collect(Collectors.toList());
        }else{
            filteredAuthors = listAuthor;
        }

        return filteredAuthors;
    }



}
