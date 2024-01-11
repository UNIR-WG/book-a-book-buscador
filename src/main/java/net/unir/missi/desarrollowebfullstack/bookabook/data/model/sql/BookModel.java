package net.unir.missi.desarrollowebfullstack.bookabook.data.model.sql;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "books")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class BookModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "isbn")
    private String ISBN;
    @Column(name = "name")
    private String name;
    @Column(name = "language")
    private String language;
    @Column(name = "description")
    private String description;
    @Column(name = "category")
    private String category;
    @Column(name = "authorId")
    @ManyToOne
    @JoinColumn(name = "author_id")
    private AuthorModel author;
}
