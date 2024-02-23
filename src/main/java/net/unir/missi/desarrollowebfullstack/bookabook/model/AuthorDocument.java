package net.unir.missi.desarrollowebfullstack.bookabook.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDate;
import java.util.List;

@Document(indexName = "author")
public class AuthorDocument {
    @Id
    @Field(
            type = FieldType.Long
    )
    private Long id;
    @Field(
            type = FieldType.Text
    )
    private String firstName;
    @Field(
            type = FieldType.Text
    )
    private String lastName;
    @Field(
            type = FieldType.Date,
            format = DateFormat.date
    )
    private LocalDate birthDate;
    @Field(
            type = FieldType.Text
    )
    private String nationality;
    @Field(
            type = FieldType.Text
    )
    private String email;
    @Field(
            type = FieldType.Text
    )
    private String webSite;
    @Field(
            type = FieldType.Text
    )
    private String biography;

    @org.springframework.data.annotation.Transient
    @Field(type = FieldType.Nested)
    private List<BookDocument> booksWritten;

    public AuthorDocument(Long id, String firstName, String lastName, LocalDate birthDate, String nationality, String email, String webSite, String biography, List<BookDocument> booksWritten) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.nationality = nationality;
        this.email = email;
        this.webSite = webSite;
        this.biography = biography;
        this.booksWritten = booksWritten;
    }

    public AuthorDocument() {
    }

    public static AuthorDocumentBuilder builder() {
        return new AuthorDocumentBuilder();
    }

    @Override
    public String toString() {
        return "AuthorDocument{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birthDate=" + birthDate +
                ", nationality='" + nationality + '\'' +
                ", email='" + email + '\'' +
                ", webSite='" + webSite + '\'' +
                ", biography='" + biography + '\'' +
                ", booksWritten=" + booksWritten +
                '}';
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

    public List<BookDocument> getBooksWritten() {
        return this.booksWritten;
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

    @JsonIgnore
    public void setBooksWritten(List<BookDocument> booksWritten) {
        this.booksWritten = booksWritten;
    }

    public static class AuthorDocumentBuilder {
        private Long id;
        private String firstName;
        private String lastName;
        private LocalDate birthDate;
        private String nationality;
        private String email;
        private String webSite;
        private String biography;
        private List<BookDocument> booksWritten;

        AuthorDocumentBuilder() {
        }

        public AuthorDocumentBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public AuthorDocumentBuilder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public AuthorDocumentBuilder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public AuthorDocumentBuilder birthDate(LocalDate birthDate) {
            this.birthDate = birthDate;
            return this;
        }

        public AuthorDocumentBuilder nationality(String nationality) {
            this.nationality = nationality;
            return this;
        }

        public AuthorDocumentBuilder email(String email) {
            this.email = email;
            return this;
        }

        public AuthorDocumentBuilder webSite(String webSite) {
            this.webSite = webSite;
            return this;
        }

        public AuthorDocumentBuilder biography(String biography) {
            this.biography = biography;
            return this;
        }

        public AuthorDocumentBuilder booksWritten(List<BookDocument> booksWritten) {
            this.booksWritten = booksWritten;
            return this;
        }

        public AuthorDocument build() {
            return new AuthorDocument(id, firstName, lastName, birthDate, nationality, email, webSite, biography, booksWritten);
        }

        public String toString() {
            return "AuthorDocument.AuthorDocumentBuilder(id=" + this.id + ", firstName=" + this.firstName + ", lastName=" + this.lastName + ", birthDate=" + this.birthDate + ", nationality=" + this.nationality + ", email=" + this.email + ", webSite=" + this.webSite + ", biography=" + this.biography + ", booksWritten=" + this.booksWritten + ")";
        }
    }
}
