package net.unir.missi.desarrollowebfullstack.bookabook.data.model.sql;

import jakarta.persistence.*;
import lombok.*;
import net.unir.missi.desarrollowebfullstack.bookabook.data.model.api.Author;

import java.util.Date;


@Entity
@Table(name = "authors")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class AuthorModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "firstName")
    private String firstName;
    @Column(name = "lastName")
    private String lastName;
    @Column(name = "birthDate")
    private Date birthDate;
    @Column(name = "nationality")
    private String nationality;
    @Column(name = "email")
    private String email;
    @Column(name = "webSite")
    private String webSite;
    @Column(name = "biography")
    private String biography;


    public AuthorModel(Author author) {
        this.id = author.getId();
        this.firstName = author.getFirstName();
        this.lastName = author.getLastName();
        this.birthDate = author.getBirthDate();
        this.nationality = author.getNationality();
        this.email = author.getEmail();
        this.webSite = author.getWebSite();
        this.biography = author.getBiography();
    }


}
