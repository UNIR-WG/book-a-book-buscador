package net.unir.missi.desarrollowebfullstack.bookabook.service;

import java.util.List;
import java.util.Optional;

import net.unir.missi.desarrollowebfullstack.bookabook.data.model.api.Author;
import net.unir.missi.desarrollowebfullstack.bookabook.data.repository.AuthorRepository;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthorService {
    private final AuthorRepository repository;

    public List<Author> getAllAuthors(){
        return repository.findAll().stream().toList();
    }

    public Author createAuthor(Author author) throws IllegalArgumentException {
        if(author.isValid())
        {
            repository.save(author);
            return author;
        }else
            return null;
    }

    public Optional<Author> getAuthorById(String idAuthor)
    {
        return repository.findById(Long.valueOf(idAuthor));
    }

    public Author modifyAuthorData(Author prev, Author authorData)
    {
        Author author = repository.findById(prev.getId()).get();

        author.setBiography(authorData.getBiography());
        author.setEmail(authorData.getEmail());
        author.setBirthDate(authorData.getBirthDate());
        author.setFirstName(authorData.getFirstName());
        author.setLastName(authorData.getLastName());
        author.setWebSite(authorData.getWebSite());
        repository.save(author);

        prev.setBiography(authorData.getBiography());
        prev.setEmail(authorData.getEmail());
        prev.setBirthDate(authorData.getBirthDate());
        prev.setFirstName(authorData.getFirstName());
        prev.setLastName(authorData.getLastName());
        prev.setWebSite(authorData.getWebSite());

        return prev;
    }

    public Author deleteAuthor(Author prev)
    {
        try {
            repository.delete(prev);
            return prev;
        }catch (Exception e){
            return null;
        }
    }

}
