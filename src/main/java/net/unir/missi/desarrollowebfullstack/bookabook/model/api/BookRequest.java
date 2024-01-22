package net.unir.missi.desarrollowebfullstack.bookabook.model.api;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class BookRequest {

    private String isbn;
    private String name;
    private String language;
    private String description;
    private String category;
    private Long authorId;
}
