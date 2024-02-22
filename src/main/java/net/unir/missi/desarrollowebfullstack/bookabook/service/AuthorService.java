package net.unir.missi.desarrollowebfullstack.bookabook.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;
import net.unir.missi.desarrollowebfullstack.bookabook.DTO.api.AuthorResponse;
import net.unir.missi.desarrollowebfullstack.bookabook.DTO.memory.Author;
import net.unir.missi.desarrollowebfullstack.bookabook.converter.api.AuthorAPIConverter;
import net.unir.missi.desarrollowebfullstack.bookabook.converter.api.BookAPIConverter;
import net.unir.missi.desarrollowebfullstack.bookabook.converter.memory.AuthorMemoryConverter;
import net.unir.missi.desarrollowebfullstack.bookabook.converter.memory.BookMemoryConverter;
import net.unir.missi.desarrollowebfullstack.bookabook.model.AuthorDocument;
import net.unir.missi.desarrollowebfullstack.bookabook.model.BookDocument;
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

    @Autowired
    private AuthorMemoryConverter authorMemoryConverter;

    @Override
    public List<Author> getAllAuthors(String firstName, String lastName, LocalDate birthDate, String nationality, String email, String webSite, String biography, Long bookId) throws RuntimeException
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
                return authorRepository.search(firstName,lastName, birthDate,nationality,email,webSite,biography,bookList).stream().map(
                        (AuthorDocument d) -> {
                            return this.authorMemoryConverter.fromDocument(d);
                        })
                        .collect(Collectors.toList());

            }else {
                return authorRepository.findAll().stream().map( (AuthorDocument d) -> {
                    return this.authorMemoryConverter.fromDocument(d);
                }).collect(Collectors.toList());
            }
    }

    @Override
    public Author createAuthor(Author author) throws RuntimeException
    {
        return this.authorMemoryConverter.fromDocument(this.authorRepository.save(this.authorMemoryConverter.toDocument(author)));
    }

    @Override
    public Author getAuthorById(String idAuthor) throws RuntimeException
    {
            AuthorDocument authorDocumentModel = this.authorRepository.getById(Long.valueOf(idAuthor));
            if (authorDocumentModel != null)
                return this.authorMemoryConverter.fromDocument(authorDocumentModel);
            else
                return null;
    }

    @Override
    public Author modifyAuthorData(Author tempAuthor, Author authorData) throws RuntimeException
    {
            AuthorDocument authorDocumentToChange = authorRepository.getById(tempAuthor.id());

            if (authorData.firstName() != null)
                authorDocumentToChange.setFirstName(authorData.firstName());
            if (authorData.lastName() != null)
                authorDocumentToChange.setLastName(authorData.lastName());
            if (authorData.birthDate() != null)
                authorDocumentToChange.setBirthDate(authorData.birthDate());
            if (authorData.email() != null)
                authorDocumentToChange.setEmail(authorData.email());
            if (authorData.webSite() != null)
                authorDocumentToChange.setWebSite(authorData.webSite());
            if (authorData.nationality() != null)
                authorDocumentToChange.setNationality(authorData.nationality());
            if (authorData.biography() != null)
                authorDocumentToChange.setBiography(authorData.biography());

            return this.authorMemoryConverter.fromDocument(this.authorRepository.save(authorDocumentToChange));
    }

    @Override
    public Author modifyAllAuthorData(Author prev, Author authorData) throws RuntimeException
    {
            AuthorDocument authorDocumentToChange = this.authorRepository.getById(prev.id());
            authorDocumentToChange.setFirstName(authorData.firstName());
            authorDocumentToChange.setLastName(authorData.lastName());
            authorDocumentToChange.setBirthDate(authorData.birthDate());
            authorDocumentToChange.setEmail(authorData.email());
            authorDocumentToChange.setWebSite(authorData.webSite());
            authorDocumentToChange.setNationality(authorData.nationality());
            authorDocumentToChange.setBiography(authorData.biography());

            return this.authorMemoryConverter.fromDocument(this.authorRepository.save(authorDocumentToChange));
    }

    @Override
    public Author deleteAuthor(Author prev) throws RuntimeException
    {
        AuthorDocument authorDocument = authorRepository.getById(prev.id());

        authorRepository.delete(authorDocument);
        return prev;

    }

}
