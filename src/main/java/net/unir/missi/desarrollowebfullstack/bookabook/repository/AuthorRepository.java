package net.unir.missi.desarrollowebfullstack.bookabook.repository;

import net.unir.missi.desarrollowebfullstack.bookabook.model.AuthorDocument;
import net.unir.missi.desarrollowebfullstack.bookabook.model.BookDocument;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Component
public class AuthorRepository {

    private static Long numAuthors = 2L;

    private final AuthorElasticRepository repository;

    public AuthorRepository(AuthorElasticRepository repository) {
        this.repository = repository;
    }

    public List<AuthorDocument> findAll() {
        List<AuthorDocument> ret = new LinkedList<>();
        repository.findAll().forEach(ret::add);
        return ret;
    }
    public AuthorDocument getById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public AuthorDocument save(AuthorDocument authorDocument) {
        Logger.getGlobal().info("REPO END INITIALIZING DB");
        AuthorDocument a = null;
        try
        {
            if (authorDocument.getId() == null)
            {
                authorDocument.setId(numAuthors);
                numAuthors++;
            }
            a = repository.save(authorDocument);

        }
        catch (Exception e)
        {
            Logger.getGlobal().warning("exception message" + e.getMessage());
            Logger.getGlobal().warning("exception stack trace" + Arrays.toString(e.getStackTrace()));
            Logger.getGlobal().warning("exception cause" + e.getCause());
            Logger.getGlobal().warning("exception: " + e);
        }
        Logger.getGlobal().info("REPO AFTER SAVE INITIALIZING DB");
        return a;
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
