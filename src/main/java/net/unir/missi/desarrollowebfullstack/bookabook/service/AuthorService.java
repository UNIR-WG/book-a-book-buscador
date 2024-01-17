package net.unir.missi.desarrollowebfullstack.bookabook.service;

import java.util.List;
import java.util.stream.Collectors;
import net.unir.missi.desarrollowebfullstack.bookabook.data.model.api.AuthorRequest;
import net.unir.missi.desarrollowebfullstack.bookabook.data.model.sql.Author;
import net.unir.missi.desarrollowebfullstack.bookabook.data.repository.AuthorRepository;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthorService implements IAuthorService{
    private final AuthorRepository repository;

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
            return repository.findById(Long.valueOf(idAuthor)).map(AuthorRequest::new).orElse(null);
        }catch (Exception e){
            throw new RuntimeException("Database Failed;");
        }
    }

    @Override
    public AuthorRequest modifyAuthorData(AuthorRequest tempAuthor, AuthorRequest authorData) throws RuntimeException
    {

        //Si el elemento recibido del autor es nulo, significa que no existe, y por ende no debemos modificarlo
          if(authorData.getFirstName()!=null)
              tempAuthor.setFirstName(authorData.getFirstName());

         if(authorData.getLastName()!=null)
             tempAuthor.setLastName(authorData.getLastName());

         if(authorData.getBirthDate()!=null)
             tempAuthor.setBirthDate(authorData.getBirthDate());

         if(authorData.getEmail()!=null)
             tempAuthor.setEmail(authorData.getEmail());

         if(authorData.getWebSite()!=null)
             tempAuthor.setWebSite(authorData.getWebSite());

         if(authorData.getNationality()!=null)
             tempAuthor.setNationality(authorData.getNationality());

         if(authorData.getBiography()!=null)
             tempAuthor.setBiography(authorData.getBiography());

         if(authorData.getBooksWritted()!=null)
             tempAuthor.setBooksWritted(authorData.getBooksWritted());

         Author authorModel = new Author(tempAuthor);
        try {
            repository.save(authorModel);

            return tempAuthor;
        }catch (Exception e){
            throw new RuntimeException("Database Failed;");
        }

    }

    @Override
    public AuthorRequest modifyAllAuthorData(AuthorRequest prev, AuthorRequest authorData) throws RuntimeException
    {
        prev.modifyAllParameters(authorData);
        Author authorModel = new Author(prev);
        try {
            repository.save(authorModel);
            return prev;
        }catch (Exception e){
            throw new RuntimeException("Database Failed;");
        }
    }

    @Override
    public AuthorRequest deleteAuthor(AuthorRequest prev) throws RuntimeException
    {
        Author authorModel = new Author(prev);
        try {
            repository.delete(authorModel);
            return prev;
        }catch (Exception e){
            throw new RuntimeException("Database Failed;");
        }
    }

}
