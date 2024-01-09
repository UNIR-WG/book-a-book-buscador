package net.unir.missi.desarrollowebfullstack.bookabook.data.model.sql;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.ToString;
import net.unir.missi.desarrollowebfullstack.bookabook.data.model.api.Author;

import java.util.Date;

@Entity
@Table(name = "authors")
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

    public AuthorModel(Long id, String firstName, String lastName, Date birthDate, String nationality, String email, String webSite, String biography) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.nationality = nationality;
        this.email = email;
        this.webSite = webSite;
        this.biography = biography;
    }
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
    public AuthorModel() {
    }


    public Long getId() {
        return this.id;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public Date getBirthDate() {
        return this.birthDate;
    }

    public String getNationality() {
        return this.nationality;
    }

    public String getEmail() {
        return this.email;
    }

    public String getWebSite() {
        return this.webSite;
    }

    public String getBiography() {
        return this.biography;
    }

    public void setId(Long id) {
        this.id = id;
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
}
