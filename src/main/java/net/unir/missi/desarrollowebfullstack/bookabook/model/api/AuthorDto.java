package net.unir.missi.desarrollowebfullstack.bookabook.model.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import net.unir.missi.desarrollowebfullstack.bookabook.model.document.AuthorDocument;
import net.unir.missi.desarrollowebfullstack.bookabook.model.document.BookDocument;

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

    public AuthorDto(AuthorDocument authorDocumentModel) {
        this.id = authorDocumentModel.getId();
        this.firstName = authorDocumentModel.getFirstName();
        this.lastName = authorDocumentModel.getLastName();
        this.birthDate = authorDocumentModel.getBirthDate();
        this.nationality = authorDocumentModel.getNationality();
        this.email = authorDocumentModel.getEmail();
        this.webSite = authorDocumentModel.getWebSite();
        this.biography = authorDocumentModel.getBiography();
        List<Long> tempBook = new ArrayList<>();
        if (authorDocumentModel.getBooksWritten() != null || !authorDocumentModel.getBooksWritten().isEmpty() ){
            for (BookDocument book : authorDocumentModel.getBooksWritten())
                tempBook.add(book.getId());
        }

        this.booksWrittenId = tempBook;

    }



}