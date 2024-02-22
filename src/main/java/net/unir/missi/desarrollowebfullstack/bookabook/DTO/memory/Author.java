package net.unir.missi.desarrollowebfullstack.bookabook.DTO.memory;

import net.unir.missi.desarrollowebfullstack.bookabook.model.BookDocument;

import java.time.LocalDate;
import java.util.List;

public record Author(Long id, String firstName, String lastName, LocalDate birthDate, String nationality, String email, String webSite, String biography, List<Long> booksWritten) {
}
