package net.unir.missi.desarrollowebfullstack.bookabook.data.model.sql;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import net.unir.missi.desarrollowebfullstack.bookabook.data.model.api.BookRequest;
import net.unir.missi.desarrollowebfullstack.bookabook.data.model.sql.Author;

@Entity
@Table(name = "books")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "isbn")
    private String isbn;
    @Column(name = "name")
    private String name;
    @Column(name = "language")
    private String language;
    @Column(name = "description")
    private String description;
    @Column(name = "category")
    private String category;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "author_id")
    private Author author;

    public void update(BookRequest bookRequest) {
        this.isbn = bookRequest.getIsbn();
        this.name = bookRequest.getName();
        this.language = bookRequest.getLanguage();
        this.description = bookRequest.getDescription();
        this.category = bookRequest.getCategory();
    }

}
