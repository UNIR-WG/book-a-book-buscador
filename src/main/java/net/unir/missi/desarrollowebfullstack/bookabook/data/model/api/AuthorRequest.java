package net.unir.missi.desarrollowebfullstack.bookabook.data.model.api;

import lombok.*;
import net.unir.missi.desarrollowebfullstack.bookabook.data.model.sql.Author;
import net.unir.missi.desarrollowebfullstack.bookabook.data.model.sql.Book;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@ToString
public class AuthorRequest {
    private Long id;
    private String firstName;
    private String lastName;
    private Date birthDate;
    private String nationality;
    private String email;
    private String webSite;
    private String biography;
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
        for (Book book : authorModel.getBooksWritted())
            this.booksWrittedId.add(book.getId());
    }

    public boolean isValid() {
        return !Objects.equals(this.firstName, "") && !Objects.equals(this.lastName, "")
                && this.birthDate != null && !Objects.equals(this.email, "")
                && !Objects.equals(this.nationality, "");

    }


    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setWebSite(String webSite) {
        this.webSite = webSite;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

//    public void setBooksWrittedId(List<Long> booksWrittedId) {
//        this.booksWrittedId.addAll(booksWrittedId);
//    }

}