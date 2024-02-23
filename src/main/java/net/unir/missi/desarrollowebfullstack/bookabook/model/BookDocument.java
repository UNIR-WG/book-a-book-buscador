package net.unir.missi.desarrollowebfullstack.bookabook.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "book")
public class BookDocument {
    @Id
    @Field(
            type = FieldType.Long
    )
    private Long id;
    @Field(
            type = FieldType.Text
    )
    private String isbn;
    @Field(
            type = FieldType.Text
    )
    private String name;
    @Field(
            type = FieldType.Text
    )
    private String language;
    @Field(
            type = FieldType.Text
    )
    private String description;
    @Field(
            type = FieldType.Text
    )
    private String category;

    @org.springframework.data.annotation.Transient
    @Field(type = FieldType.Nested)
    private AuthorDocument authorDocument;

    public BookDocument(Long id, String isbn, String name, String language, String description, String category, AuthorDocument authorDocument) {
        this.id = id;
        this.isbn = isbn;
        this.name = name;
        this.language = language;
        this.description = description;
        this.category = category;
        this.authorDocument = authorDocument;
    }

    public BookDocument() {
    }

    public static BookDocumentBuilder builder() {
        return new BookDocumentBuilder();
    }

    @Override
    public String toString() {
        return "BookDocument{" +
                "id=" + id +
                ", isbn='" + isbn + '\'' +
                ", name='" + name + '\'' +
                ", language='" + language + '\'' +
                ", description='" + description + '\'' +
                ", category='" + category + '\'' +
                ", authorDocument=" + authorDocument +
                '}';
    }

    public Long getId() {
        return this.id;
    }

    public String getIsbn() {
        return this.isbn;
    }

    public String getName() {
        return this.name;
    }

    public String getLanguage() {
        return this.language;
    }

    public String getDescription() {
        return this.description;
    }

    public String getCategory() {
        return this.category;
    }

    public AuthorDocument getAuthorDocument() {
        return this.authorDocument;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @JsonIgnore
    public void setAuthorDocument(AuthorDocument authorDocument) {
        this.authorDocument = authorDocument;
    }

    public static class BookDocumentBuilder {
        private Long id;
        private String isbn;
        private String name;
        private String language;
        private String description;
        private String category;
        private AuthorDocument authorDocument;

        BookDocumentBuilder() {
        }

        public BookDocumentBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public BookDocumentBuilder isbn(String isbn) {
            this.isbn = isbn;
            return this;
        }

        public BookDocumentBuilder name(String name) {
            this.name = name;
            return this;
        }

        public BookDocumentBuilder language(String language) {
            this.language = language;
            return this;
        }

        public BookDocumentBuilder description(String description) {
            this.description = description;
            return this;
        }

        public BookDocumentBuilder category(String category) {
            this.category = category;
            return this;
        }

        public BookDocumentBuilder authorDocument(AuthorDocument authorDocument) {
            this.authorDocument = authorDocument;
            return this;
        }

        public BookDocument build() {
            return new BookDocument(id, isbn, name, language, description, category, authorDocument);
        }

        public String toString() {
            return "BookDocument.BookDocumentBuilder(id=" + this.id + ", isbn=" + this.isbn + ", name=" + this.name + ", language=" + this.language + ", description=" + this.description + ", category=" + this.category + ", authorDocument=" + this.authorDocument + ")";
        }
    }
}
