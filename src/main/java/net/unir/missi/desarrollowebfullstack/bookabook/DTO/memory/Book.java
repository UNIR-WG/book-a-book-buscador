package net.unir.missi.desarrollowebfullstack.bookabook.DTO.memory;

import java.util.List;

public record Book(Long id, String isbn, String name, String language, String description, String category, Long authorDocument) {
}
