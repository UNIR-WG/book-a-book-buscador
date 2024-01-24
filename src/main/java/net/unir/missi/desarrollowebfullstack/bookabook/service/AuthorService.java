package net.unir.missi.desarrollowebfullstack.bookabook.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;
import net.unir.missi.desarrollowebfullstack.bookabook.model.api.AuthorDto;
import net.unir.missi.desarrollowebfullstack.bookabook.model.sql.Author;
import net.unir.missi.desarrollowebfullstack.bookabook.model.sql.Book;
import net.unir.missi.desarrollowebfullstack.bookabook.repository.AuthorRepository;
import net.unir.missi.desarrollowebfullstack.bookabook.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class AuthorService implements IAuthorService{
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private BookRepository bookRepository;

    @Override
    public List<AuthorDto> getAllAuthors(String firstName, String lastName, LocalDate birthDate, String nationality, String email, String webSite, String biography, Long bookId) throws RuntimeException
    {

            if (firstName!=null
                    || lastName!=null
                    || birthDate!=null
                    || nationality!=null
                    || email!=null
                    || webSite!=null
                    || biography!=null
                    || bookId!=null){


                Book bookList;
                if(bookId!=null) {
                    bookList = bookRepository.getById(bookId);
                    if (bookList == null) {
                        return null;
                    }
                } else
                    bookList = null;
                return authorRepository.search(firstName,lastName, birthDate,nationality,email,webSite,biography,bookList).stream().map(AuthorDto::new).collect(Collectors.toList());

            }else {
                return authorRepository.findAll().stream().map(AuthorDto::new).collect(Collectors.toList());
            }
    }

    @Override
    public AuthorDto createAuthor(AuthorDto author) throws RuntimeException
    {
            Author authorModel = new Author(author);

            return new AuthorDto(authorRepository.save(authorModel));
    }

    @Override
    public AuthorDto getAuthorById(String idAuthor) throws RuntimeException
    {

            Author authorModel = authorRepository.getById(Long.valueOf(idAuthor));

            if(authorModel!=null)
                return new AuthorDto(authorModel);
            else
                return null;

    }

    @Override
    public AuthorDto modifyAuthorData(AuthorDto tempAuthor, AuthorDto authorData) throws RuntimeException
    {

            Author authorToChange = authorRepository.getById(tempAuthor.getId());

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

            authorRepository.save(authorToChange);

            return new AuthorDto(authorToChange);

    }

    @Override
    public AuthorDto modifyAllAuthorData(AuthorDto prev, AuthorDto authorData) throws RuntimeException
    {
            Author authorToChange = authorRepository.getById(prev.getId());
            //Si el elemento recibido del autor es nulo, significa que no existe, y por ende no debemos modificarlo
            authorToChange.setFirstName(authorData.getFirstName());

            authorToChange.setLastName(authorData.getLastName());

            authorToChange.setBirthDate(authorData.getBirthDate());

            authorToChange.setEmail(authorData.getEmail());

            authorToChange.setWebSite(authorData.getWebSite());

            authorToChange.setNationality(authorData.getNationality());

            authorToChange.setBiography(authorData.getBiography());

            authorRepository.save(authorToChange);

            return new AuthorDto(authorToChange);

    }

    @Override
    public AuthorDto deleteAuthor(AuthorDto prev) throws RuntimeException
    {
        Author author = authorRepository.getById(prev.getId());

        authorRepository.delete(author);
        return prev;

    }

}
