package net.unir.missi.desarrollowebfullstack.bookabook.repository;

import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.unir.missi.desarrollowebfullstack.bookabook.model.document.AuthorDocument;
import net.unir.missi.desarrollowebfullstack.bookabook.model.document.BookDocument;
import net.unir.missi.desarrollowebfullstack.bookabook.config.search.SearchCriteria;
import net.unir.missi.desarrollowebfullstack.bookabook.config.search.SearchOperation;
import net.unir.missi.desarrollowebfullstack.bookabook.config.search.SearchStatement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
public class AuthorRepository {

    private final AuthorElasticRepository repository;

    public List<AuthorDocument> findAll() {
        List<AuthorDocument> ret = new LinkedList<>();
        repository.findAll().forEach(ret::add);
        return ret;
    }
    public AuthorDocument getById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public AuthorDocument save(AuthorDocument authorDocument) {
        return repository.save(authorDocument);
    }

    public void delete(AuthorDocument authorDocument) {
        repository.delete(authorDocument);
    }

    private void addSearchStatement(SearchCriteria<AuthorDocument> spec, String key, String value, SearchOperation operation) {
        if (StringUtils.isNotBlank(key)) {
            spec.add(new SearchStatement(key, value, operation));
        }
    }

    public List<AuthorDocument> search(String firstName, String lastName, LocalDate birthDate, String nationality, String email, String webSite, String biography, BookDocument booksWritten) {

        SearchCriteria<AuthorDocument> spec = new SearchCriteria<>();
        List<AuthorDocument> listAuthorDocument = new LinkedList<>();
        repository.findAll().forEach(listAuthorDocument::add);
        List<AuthorDocument> filteredAuthorDocuments;

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
            filteredAuthorDocuments = listAuthorDocument.stream()
                    .filter(author -> author.getBooksWritten().stream()
                            .anyMatch(book -> Objects.equals(booksWritten.getId(), book.getId())))
                    .collect(Collectors.toList());
        }else{
            filteredAuthorDocuments = listAuthorDocument;
        }

        return filteredAuthorDocuments;
    }



}
