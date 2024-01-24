package net.unir.missi.desarrollowebfullstack.bookabook.model.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import net.unir.missi.desarrollowebfullstack.bookabook.model.sql.Author;
import net.unir.missi.desarrollowebfullstack.bookabook.model.sql.Book;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class AuthorDto {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private String nationality;
    private String email;
    private String webSite;
    private String biography;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<Long> booksWrittenId;

    public AuthorDto(Author authorModel) {
        this.id = authorModel.getId();
        this.firstName = authorModel.getFirstName();
        this.lastName = authorModel.getLastName();
        this.birthDate = authorModel.getBirthDate();
        this.nationality = authorModel.getNationality();
        this.email = authorModel.getEmail();
        this.webSite = authorModel.getWebSite();
        this.biography = authorModel.getBiography();
        List<Long> tempBook = new ArrayList<>();
        if (authorModel.getBooksWritten() != null || !authorModel.getBooksWritten().isEmpty() ){
            for (Book book : authorModel.getBooksWritten())
                tempBook.add(book.getId());
        }

        this.booksWrittenId = tempBook;

    }



}