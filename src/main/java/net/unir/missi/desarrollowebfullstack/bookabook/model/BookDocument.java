package net.unir.missi.desarrollowebfullstack.bookabook.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Document(indexName = "book")
public class BookDocument {
    @Id
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
    @Field(
            type = FieldType.Long
    )
    private AuthorDocument authorDocument;
}
