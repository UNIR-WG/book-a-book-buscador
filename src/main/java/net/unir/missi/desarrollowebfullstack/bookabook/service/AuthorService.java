package net.unir.missi.desarrollowebfullstack.bookabook.service;

import java.util.List;
import java.util.stream.Collectors;
import net.unir.missi.desarrollowebfullstack.bookabook.data.model.api.Author;
import net.unir.missi.desarrollowebfullstack.bookabook.data.model.sql.AuthorModel;
import net.unir.missi.desarrollowebfullstack.bookabook.data.repository.AuthorRepository;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthorService {
    private final AuthorRepository repository;

    public List<Author> getAllAuthors(){
        return repository.findAll().stream().map(Author::new).collect(Collectors.toList());
    }

    public Author createAuthor(Author author) throws IllegalArgumentException {
        if(author.isValid())
        {
            AuthorModel authorModel = new AuthorModel(author);
            repository.save(authorModel);
            return author;
        }else
            return null;
    }

    public Author getAuthorById(String idAuthor)
    {
        return repository.findById(Long.valueOf(idAuthor)).map(Author::new).orElse(null);
    }

    public Author modifyAuthorData(Author prev, Author authorData)
    {
        Author author = repository.findById(prev.getId()).map(Author::new).orElse(null);

         if (author!= null) {
             AuthorModel authorModel = new AuthorModel(author);

             authorModel.setBiography(authorData.getBiography());
             authorModel.setEmail(authorData.getEmail());
             authorModel.setBirthDate(authorData.getBirthDate());
             authorModel.setFirstName(authorData.getFirstName());
             authorModel.setLastName(authorData.getLastName());
             authorModel.setWebSite(authorData.getWebSite());

            repository.save(authorModel);

            prev.setBiography(authorData.getBiography());
            prev.setEmail(authorData.getEmail());
            prev.setBirthDate(authorData.getBirthDate());
            prev.setFirstName(authorData.getFirstName());
            prev.setLastName(authorData.getLastName());
            prev.setWebSite(authorData.getWebSite());

            return prev;
        }else
            return null;
    }

    public Author deleteAuthor(Author prev)
    {
        try {
            AuthorModel authorModel = new AuthorModel(prev);
            repository.delete(authorModel);
            return prev;
        }catch (Exception e){
            return null;
        }
    }

}
