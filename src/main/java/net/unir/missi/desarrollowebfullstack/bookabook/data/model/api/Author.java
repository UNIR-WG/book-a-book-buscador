package net.unir.missi.desarrollowebfullstack.bookabook.data.model.api;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.unir.missi.desarrollowebfullstack.bookabook.data.model.sql.AuthorModel;

import java.util.Date;
import java.util.Objects;


@Getter
@Setter
@ToString
public class Author {
    private Long id;
    private String firstName;
    private String lastName;
    private Date birthDate;
    private String nationality;
    private String email;
    private String webSite;
    private String biography;

    public Author(AuthorModel authorModel) {
        this.id = authorModel.getId();
        this.firstName = authorModel.getFirstName();
        this.lastName = authorModel.getLastName();
        this.birthDate = authorModel.getBirthDate();
        this.nationality = authorModel.getNationality();
        this.email = authorModel.getEmail();
        this.webSite = authorModel.getWebSite();
        this.biography = authorModel.getBiography();
    }

    public boolean isValid() {
        return !Objects.equals(this.firstName, "") && !Objects.equals(this.lastName, "")
                && this.birthDate != null && !Objects.equals(this.email, "")
                && !Objects.equals(this.nationality, "");

    }

}