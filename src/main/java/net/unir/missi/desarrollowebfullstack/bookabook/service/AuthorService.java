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
    public List<AuthorRequest> getAllAuthors(){
        return repository.findAll().stream().map(AuthorRequest::new).collect(Collectors.toList());
    }

    @Override
    public AuthorRequest createAuthor(AuthorRequest author) throws IllegalArgumentException {

            Author authorModel = new Author(author);
            repository.save(authorModel);
            return author;

    }

    @Override
    public AuthorRequest getAuthorById(String idAuthor)
    {
        return repository.findById(Long.valueOf(idAuthor)).map(AuthorRequest::new).orElse(null);
    }

    @Override
    public AuthorRequest modifyAuthorData(AuthorRequest tempAuthor, AuthorRequest authorData)
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

         repository.save(authorModel);

        return tempAuthor;

    }

    @Override
    public AuthorRequest modifyAllAuthorData(AuthorRequest prev, AuthorRequest authorData)
    {
        prev.modifyAllParameters(authorData);
        Author authorModel = new Author(prev);
        repository.save(authorModel);

        return prev;
    }

    @Override
    public AuthorRequest deleteAuthor(AuthorRequest prev)
    {
            Author authorModel = new Author(prev);
            repository.delete(authorModel);
            return prev;
    }

}
