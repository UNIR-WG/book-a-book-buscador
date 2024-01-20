package net.unir.missi.desarrollowebfullstack.bookabook.data.model.sql;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import net.unir.missi.desarrollowebfullstack.bookabook.data.model.api.AuthorRequest;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Entity
@Table(name = "authors")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Author implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotNull
    @Column(name = "firstName")
    private String firstName;
    @NotNull
    @Column(name = "lastName")
    private String lastName;
    @Column(name = "birthDate")
    private LocalDate birthDate;
    @Column(name = "nationality")
    private String nationality;
    @Column(name = "email")
    private String email;
    @Column(name = "webSite")
    private String webSite;
    @Column(name = "biography")
    private String biography;
    @Column(name = "booksWritted")
    @OneToMany(mappedBy = "author", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Book> booksWritted;

    public Author(Author author) {
        this.id = author.getId();
        this.firstName = author.getFirstName();
        this.lastName = author.getLastName();
        this.birthDate = author.getBirthDate();
        this.nationality = author.getNationality();
        this.email = author.getEmail();
        this.webSite = author.getWebSite();
        this.biography = author.getBiography();
        this.booksWritted = author.getBooksWritted();
    }

    public void addBook(Book book) {
        booksWritted.add(book);
        book.setAuthor(this);
    }

    public void removeBook(Book book) {
        booksWritted.remove(book);
        book.setAuthor(null);
    }

    public void setBooksWritted(List<Book> booksWritted) {
        this.booksWritted = booksWritted;
    }

    public Author(AuthorRequest author) {
        this.id = author.getId();
        this.firstName = author.getFirstName();
        this.lastName = author.getLastName();
        this.birthDate = author.getBirthDate();
        this.nationality = author.getNationality();
        this.email = author.getEmail();
        this.webSite = author.getWebSite();
        this.biography = author.getBiography();
        this.booksWritted = new ArrayList<>();
    }

    public List<Book> getBooksWritted() {
        return booksWritted;
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

    public LocalDate getBirthDate() {
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

    public void setBirthDate(LocalDate birthDate) {
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
