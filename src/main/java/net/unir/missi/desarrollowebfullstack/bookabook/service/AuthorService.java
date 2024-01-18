package net.unir.missi.desarrollowebfullstack.bookabook.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;
import net.unir.missi.desarrollowebfullstack.bookabook.data.model.api.AuthorRequest;
import net.unir.missi.desarrollowebfullstack.bookabook.data.model.sql.Author;
import net.unir.missi.desarrollowebfullstack.bookabook.data.model.sql.Book;
import net.unir.missi.desarrollowebfullstack.bookabook.data.repository.AuthorRepository;
import net.unir.missi.desarrollowebfullstack.bookabook.data.repository.BookRepository;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthorService implements IAuthorService{
    private final AuthorRepository repository;
    private final BookRepository bookRepository;

    @Override
    public List<AuthorRequest> getAllAuthors() throws RuntimeException
    {
        try {
            return repository.findAll().stream().map(AuthorRequest::new).collect(Collectors.toList());
        }catch (Exception e){
            throw new RuntimeException("Database Failed;");
        }
    }

    @Override
    public AuthorRequest createAuthor(AuthorRequest author) throws RuntimeException
    {
        try {
            Author authorModel = new Author(author);
            repository.save(authorModel);
            return author;
        }catch (Exception e){
            throw new RuntimeException("Database Failed;");
        }
    }

    @Override
    public AuthorRequest getAuthorById(String idAuthor) throws RuntimeException
    {
        try {
            Author authorModel = repository.findById(Long.valueOf(idAuthor)).map(Author::new).orElse(null);
            log.error("ERROR ID: "+authorModel);
            if(authorModel!=null)
                return new AuthorRequest(authorModel);
            else
                return null;
        }catch (Exception e){
            throw new RuntimeException("Database Failed;"+e.getMessage());
        }
    }

    @Override
    public AuthorRequest modifyAuthorData(AuthorRequest tempAuthor, AuthorRequest authorData) throws RuntimeException
    {
        try {
            Optional<Author> optionalAuthor = repository.findById(tempAuthor.getId());
            if(optionalAuthor.isPresent()) {
                Author authorToChange = optionalAuthor.get();
                //Si el elemento recibido del autor es nulo, significa que no existe, y por ende no debemos modificarlo
                if (authorData.getFirstName() != null)
                    authorToChange.setFirstName(authorData.getFirstName());

                if (authorData.getLastName() != null)
                    authorToChange.setLastName(authorData.getLastName());

                if (authorData.getBirthDate() != null)
                    authorToChange.setBirthDate(authorData.getBirthDate());

                if (authorData.getEmail() != null)
                    authorToChange.setEmail(authorData.getEmail());

                if (authorData.getWebSite() != null)
                    authorToChange.setWebSite(authorData.getWebSite());

                if (authorData.getNationality() != null)
                    authorToChange.setNationality(authorData.getNationality());

                if (authorData.getBiography() != null)
                    authorToChange.setBiography(authorData.getBiography());

                repository.save(authorToChange);

                return new AuthorRequest(authorToChange);
            }else
                throw new RuntimeException("Database Failed;");
        }catch (Exception e){
            throw new RuntimeException("Database Failed;");
        }

    }

    @Override
    public AuthorRequest modifyAllAuthorData(AuthorRequest prev, AuthorRequest authorData) throws RuntimeException
    {
        try {
            Optional<Author> optionalAuthor = repository.findById(prev.getId());
            if(optionalAuthor.isPresent()) {
                Author authorToChange = optionalAuthor.get();
                //Si el elemento recibido del autor es nulo, significa que no existe, y por ende no debemos modificarlo
                    authorToChange.setFirstName(authorData.getFirstName());

                    authorToChange.setLastName(authorData.getLastName());

                    authorToChange.setBirthDate(authorData.getBirthDate());

                    authorToChange.setEmail(authorData.getEmail());

                    authorToChange.setWebSite(authorData.getWebSite());

                    authorToChange.setNationality(authorData.getNationality());

                    authorToChange.setBiography(authorData.getBiography());

                repository.save(authorToChange);

                return new AuthorRequest(authorToChange);
            }else
                throw new RuntimeException("Database Failed;");
        }catch (Exception e){
            throw new RuntimeException("Database Failed;");
        }
    }

    @Override
    public AuthorRequest deleteAuthor(AuthorRequest prev) throws RuntimeException
    {
        Optional<Author> optionalAuthor = repository.findById(prev.getId());
        try {
            if(optionalAuthor.isPresent()) {
                repository.delete(optionalAuthor.get());
                return prev;
            }else{
                throw new RuntimeException("Database Failed;");
            }
        }catch (Exception e){
            throw new RuntimeException("Database Failed;");
        }
    }

}
