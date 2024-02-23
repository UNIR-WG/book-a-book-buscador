package net.unir.missi.desarrollowebfullstack.bookabook.converter.memory;

import net.unir.missi.desarrollowebfullstack.bookabook.model.AuthorDocument;
import net.unir.missi.desarrollowebfullstack.bookabook.DTO.memory.Author;
import net.unir.missi.desarrollowebfullstack.bookabook.model.BookDocument;
import net.unir.missi.desarrollowebfullstack.bookabook.repository.AuthorRepository;
import net.unir.missi.desarrollowebfullstack.bookabook.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class AuthorMemoryConverter {

    @Autowired
    private BookRepository repository;

    public AuthorDocument toDocument(final Author author)
    {
        if (author == null)
        {
            return null;
        }

        final AuthorDocument document = new AuthorDocument();
        document.setId(author.id());
        document.setFirstName(author.firstName());
        document.setLastName(author.lastName());
        document.setBirthDate(author.birthDate());
        document.setNationality(author.nationality());
        document.setEmail(author.email());
        document.setWebSite(author.webSite());
        document.setBiography(author.biography());

        if (author.booksWritten() != null)
        {
            List<BookDocument> booksWritten = new LinkedList<>();
            for (Long id : author.booksWritten())
            {
                if (repository.getById(id) == null)
                {
                    throw new RuntimeException("The book with id " + id.toString() + " does not exist");
                }
                booksWritten.add(repository.getById(id));
            }
            document.setBooksWritten(booksWritten);
        }
        else
        {
            document.setBooksWritten(new LinkedList<>());
        }

        return document;
    }

    public Author fromDocument(final AuthorDocument document) {
        if (document == null) {
            return null;
        }

        List<Long> bookIds = new LinkedList<Long>();
        if (document.getBooksWritten() != null)
        {
            bookIds = document.getBooksWritten()
                    .stream().map(BookDocument::getId)
                    .collect(Collectors.toList());
        }

        return new Author(
                document.getId(),
                document.getFirstName(),
                document.getLastName(),
                document.getBirthDate(),
                document.getNationality(),
                document.getEmail(),
                document.getWebSite(),
                document.getBiography(),

                bookIds);
    }
}
