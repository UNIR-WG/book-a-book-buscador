package net.unir.missi.desarrollowebfullstack.bookabook.converter.memory;

import net.unir.missi.desarrollowebfullstack.bookabook.model.AuthorDocument;
import net.unir.missi.desarrollowebfullstack.bookabook.model.BookDocument;
import net.unir.missi.desarrollowebfullstack.bookabook.DTO.memory.Book;
import net.unir.missi.desarrollowebfullstack.bookabook.repository.AuthorRepository;
import net.unir.missi.desarrollowebfullstack.bookabook.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BookMemoryConverter {

    @Autowired
    private AuthorRepository repository;

    public BookDocument toDocument(final Book book)
    {
        if (book == null)
        {
            return null;
        }
        
        final BookDocument document = new BookDocument();
        document.setId(book.id());
        document.setIsbn(book.isbn());
        document.setName(book.name());
        document.setLanguage(book.language());
        document.setDescription(book.description());
        document.setCategory(book.category());

        if (repository.getById(book.id()) == null)
            throw new RuntimeException("The book with id " + book.id().toString() + " does not exist");

        document.setAuthorDocument(repository.getById(book.authorDocument()));

        return document;
    }

    public Book fromDocument(final BookDocument document) {
        if (document == null) {
            return null;
        }

        Long author = null;
        if (document.getAuthorDocument() == null)
        {
            author = null;
        }
        return new Book(
                document.getId(),
                document.getIsbn(),
                document.getName(),
                document.getLanguage(),
                document.getDescription(),
                document.getCategory(),
                author);
    }
}
