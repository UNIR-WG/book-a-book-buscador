package net.unir.missi.desarrollowebfullstack.bookabook.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Document(indexName = "author", alwaysWriteMapping = true)
@Getter
@Setter
public class AuthorDocument {
    @Id
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

    private List<BookDocument> booksWritten;

}
