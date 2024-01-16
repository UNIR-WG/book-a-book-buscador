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
        if(author.isValid())
        {
            Author authorModel = new Author(author);
            repository.save(authorModel);
            return author;
        }else
            return null;
    }

    @Override
    public AuthorRequest getAuthorById(String idAuthor)
    {
        return repository.findById(Long.valueOf(idAuthor)).map(AuthorRequest::new).orElse(null);
    }

    @Override
    public AuthorRequest modifyAuthorData(AuthorRequest prev, AuthorRequest authorData)
    {
        AuthorRequest author = repository.findById(prev.getId()).map(AuthorRequest::new).orElse(null);

         if (author!= null) {
             Author authorModel = new Author(author);

             authorModel.setBiography(authorData.getBiography());
             authorModel.setEmail(authorData.getEmail());
             authorModel.setBirthDate(authorData.getBirthDate());
             authorModel.setFirstName(authorData.getFirstName());
             authorModel.setLastName(authorData.getLastName());
             authorModel.setWebSite(authorData.getWebSite());
             authorModel.setBooksWritted(authorData.getBooksWritted());

            repository.save(authorModel);

            return author;
        }else
            return null;
    }

    @Override
    public AuthorRequest deleteAuthor(AuthorRequest prev)
    {
        try {
            Author authorModel = new Author(prev);
            repository.delete(authorModel);
            return prev;
        }catch (Exception e){
            return null;
        }
    }

}
