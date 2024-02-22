package net.unir.missi.desarrollowebfullstack.bookabook.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;
import net.unir.missi.desarrollowebfullstack.bookabook.model.api.AuthorDto;
import net.unir.missi.desarrollowebfullstack.bookabook.model.document.AuthorDocument;
import net.unir.missi.desarrollowebfullstack.bookabook.model.document.BookDocument;
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


                BookDocument bookList;
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
            AuthorDocument authorDocumentModel = new AuthorDocument(author);

            return new AuthorDto(authorRepository.save(authorDocumentModel));
    }

    @Override
    public AuthorDto getAuthorById(String idAuthor) throws RuntimeException
    {

            AuthorDocument authorDocumentModel = authorRepository.getById(Long.valueOf(idAuthor));

            if(authorDocumentModel !=null)
                return new AuthorDto(authorDocumentModel);
            else
                return null;

    }

    @Override
    public AuthorDto modifyAuthorData(AuthorDto tempAuthor, AuthorDto authorData) throws RuntimeException
    {

            AuthorDocument authorDocumentToChange = authorRepository.getById(tempAuthor.getId());

            if (authorData.getFirstName() != null)
                authorDocumentToChange.setFirstName(authorData.getFirstName());

            if (authorData.getLastName() != null)
                authorDocumentToChange.setLastName(authorData.getLastName());

            if (authorData.getBirthDate() != null)
                authorDocumentToChange.setBirthDate(authorData.getBirthDate());

            if (authorData.getEmail() != null)
                authorDocumentToChange.setEmail(authorData.getEmail());

            if (authorData.getWebSite() != null)
                authorDocumentToChange.setWebSite(authorData.getWebSite());

            if (authorData.getNationality() != null)
                authorDocumentToChange.setNationality(authorData.getNationality());

            if (authorData.getBiography() != null)
                authorDocumentToChange.setBiography(authorData.getBiography());

            authorRepository.save(authorDocumentToChange);

            return new AuthorDto(authorDocumentToChange);

    }

    @Override
    public AuthorDto modifyAllAuthorData(AuthorDto prev, AuthorDto authorData) throws RuntimeException
    {
            AuthorDocument authorDocumentToChange = authorRepository.getById(prev.getId());

            authorDocumentToChange.setFirstName(authorData.getFirstName());

            authorDocumentToChange.setLastName(authorData.getLastName());

            authorDocumentToChange.setBirthDate(authorData.getBirthDate());

            authorDocumentToChange.setEmail(authorData.getEmail());

            authorDocumentToChange.setWebSite(authorData.getWebSite());

            authorDocumentToChange.setNationality(authorData.getNationality());

            authorDocumentToChange.setBiography(authorData.getBiography());

            authorRepository.save(authorDocumentToChange);

            return new AuthorDto(authorDocumentToChange);

    }

    @Override
    public AuthorDto deleteAuthor(AuthorDto prev) throws RuntimeException
    {
        AuthorDocument authorDocument = authorRepository.getById(prev.getId());

        authorRepository.delete(authorDocument);
        return prev;

    }

}
