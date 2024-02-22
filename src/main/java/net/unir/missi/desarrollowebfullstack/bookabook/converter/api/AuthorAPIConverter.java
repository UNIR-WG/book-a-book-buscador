package net.unir.missi.desarrollowebfullstack.bookabook.converter.api;

import net.unir.missi.desarrollowebfullstack.bookabook.DTO.api.AuthorResponse;
import net.unir.missi.desarrollowebfullstack.bookabook.DTO.memory.Author;
import org.springframework.stereotype.Component;

@Component
public class AuthorAPIConverter {

    public Author toMemory(final AuthorResponse author)
    {
        if (author == null)
        {
            return null;
        }

        return new Author(author.getId(), author.getFirstName(), author.getLastName(), author.getBirthDate(), author.getNationality(), author.getEmail(),
                author.getWebSite(), author.getBiography(), author.getBooksWrittenId());
    }

    public AuthorResponse fromMemory(final Author document) {
        if (document == null) {
            return null;
        }

        return new AuthorResponse(
                document.id(),
                document.firstName(),
                document.lastName(),
                document.birthDate(),
                document.nationality(),
                document.email(),
                document.webSite(),
                document.biography(),
                document.booksWritten());
    }
}
