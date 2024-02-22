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
@Document(indexName = "client")
public class ClientDocument {
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
            type = FieldType.Text
    )
    private String address;
    @Field(
            type = FieldType.Text
    )
    private String phoneNumber;
    @Field(
            type = FieldType.Text
    )
    private String email;
}
