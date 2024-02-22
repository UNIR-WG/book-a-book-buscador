package net.unir.missi.desarrollowebfullstack.bookabook.model.document;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import net.unir.missi.desarrollowebfullstack.bookabook.model.api.BookRequest;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "books")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class BookDocument {
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
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "author_id")
    @JsonBackReference
    private AuthorDocument authorDocument;

    public void update(BookRequest bookRequest) {
        this.isbn = bookRequest.getIsbn();
        this.name = bookRequest.getName();
        this.language = bookRequest.getLanguage();
        this.description = bookRequest.getDescription();
        this.category = bookRequest.getCategory();
    }

}
