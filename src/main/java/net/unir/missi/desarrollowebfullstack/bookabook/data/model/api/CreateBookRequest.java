package net.unir.missi.desarrollowebfullstack.bookabook.data.model.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateBookRequest {

    private String isbn;
    private String name;
    private String language;
    private String description;
    private String category;
    private Long authorId;
}
