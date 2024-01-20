package net.unir.missi.desarrollowebfullstack.bookabook.data.model.api;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import net.unir.missi.desarrollowebfullstack.bookabook.data.model.sql.Author;
import net.unir.missi.desarrollowebfullstack.bookabook.data.model.sql.Book;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Builder
@Getter
@ToString
public class AuthorRequest {
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
    private List<Long> booksWrittedId;


    public AuthorRequest(Author authorModel) {
        this.id = authorModel.getId();
        this.firstName = authorModel.getFirstName();
        this.lastName = authorModel.getLastName();
        this.birthDate = authorModel.getBirthDate();
        this.nationality = authorModel.getNationality();
        this.email = authorModel.getEmail();
        this.webSite = authorModel.getWebSite();
        this.biography = authorModel.getBiography();
        if (authorModel.getBooksWritted() != null)
            for (Book book : authorModel.getBooksWritted())
                this.booksWrittedId.add(book.getId());
        else {
            this.booksWrittedId = new ArrayList<>();
        }
    }

    public AuthorRequest(Long id, String firstName, String lastName, LocalDate birthDate, String nationality, String email, String webSite, String biography, List<Long> booksWrittedId) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.nationality = nationality;
        this.email = email;
        this.webSite = webSite;
        this.biography = biography;
        this.booksWrittedId = booksWrittedId;
    }

}