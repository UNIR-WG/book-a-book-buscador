package net.unir.missi.desarrollowebfullstack.bookabook.DTO.api;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class BookResponse {
    private Long id;
    private String isbn;
    private String name;
    private String language;
    private String description;
    private String category;
    private Long authorId;
}
