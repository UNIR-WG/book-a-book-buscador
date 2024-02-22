package net.unir.missi.desarrollowebfullstack.bookabook.converter.api;

import net.unir.missi.desarrollowebfullstack.bookabook.DTO.api.BookResponse;
import net.unir.missi.desarrollowebfullstack.bookabook.DTO.memory.Book;
import net.unir.missi.desarrollowebfullstack.bookabook.model.BookDocument;
import net.unir.missi.desarrollowebfullstack.bookabook.repository.BookRepository;
import org.springframework.stereotype.Component;

@Component
public class BookAPIConverter {

    public Book toMemory(final BookResponse book) {
        if (book == null) {
            return null;
        }

        return new Book(
                book.getId(),
                book.getIsbn(),
                book.getName(),
                book.getLanguage(),
                book.getDescription(),
                book.getCategory(),
                book.getAuthorId());


    }

    public BookResponse fromMemory(final Book document) {
        if (document == null) {
            return null;
        }

        final BookResponse response = new BookResponse();
        response.setId(document.id());
        response.setIsbn(document.isbn());
        response.setName(document.name());
        response.setLanguage(document.language());
        response.setDescription(document.description());
        response.setCategory(document.category());
        response.setAuthorId(document.authorDocument());

        return response;
    }
}
