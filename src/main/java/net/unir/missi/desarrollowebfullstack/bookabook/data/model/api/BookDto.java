package net.unir.missi.desarrollowebfullstack.bookabook.data.model.api;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class BookDto {

    private String isbn;
    private String name;
    private String language;
    private String description;
    private String category;
    private Long authorId;
}
