package net.unir.missi.desarrollowebfullstack.bookabook.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.unir.missi.desarrollowebfullstack.bookabook.model.AuthorDocument;
import net.unir.missi.desarrollowebfullstack.bookabook.model.BookDocument;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Component
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

    public List<AuthorDocument> search(String firstName, String lastName, LocalDate birthDate, String nationality,
                                       String email, String webSite, String biography, BookDocument booksWritten) {

        List<AuthorDocument> listAuthorDocument = new LinkedList<>();
        repository.findAll().forEach(listAuthorDocument::add);

        return listAuthorDocument.stream()
                .filter((AuthorDocument doc) ->
                {
                    if (!doc.getFirstName().equals(firstName)) {
                        return false;
                    }
                    if (!doc.getLastName().equals(lastName)) {
                        return false;
                    }
                    if (!doc.getBirthDate().equals(birthDate)) {
                        return false;
                    }
                    if (!doc.getNationality().equals(nationality)) {
                        return false;
                    }
                    if (! doc.getEmail().equals(email))
                    {
                        return false;
                    }
                    if (! doc.getWebSite().equals(webSite))
                    {
                        return false;
                    }
                    if (! doc.getBiography().equals(biography))
                    {
                        return false;
                    }
                    return true;
                    // TODO filter by booksWritten
                })
                .collect(Collectors.toList());
    }



}
