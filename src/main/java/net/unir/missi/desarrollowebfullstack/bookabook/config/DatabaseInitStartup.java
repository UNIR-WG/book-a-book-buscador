package net.unir.missi.desarrollowebfullstack.bookabook.config;

import jakarta.validation.constraints.NotNull;
import net.unir.missi.desarrollowebfullstack.bookabook.model.AuthorDocument;
import net.unir.missi.desarrollowebfullstack.bookabook.model.BookDocument;
import net.unir.missi.desarrollowebfullstack.bookabook.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

@Component
public class DatabaseInitStartup implements ApplicationListener<ApplicationReadyEvent> {

    @Autowired
    private AuthorRepository authorRepository;

    @Override
    public void onApplicationEvent(final @NotNull ApplicationReadyEvent event) {
        try{
            if (this.authorRepository.getById(1L) != null)
            {
                //this.initializeDB();
            }
        }
        catch (Exception e)
        {
            Logger.getLogger("DB init is failing with " + Arrays.toString(e.getStackTrace()));
        }
    }

    private void initializeDB()
    {
        Logger.getGlobal().info("INITIALIZING DB");

        AuthorDocument authorDocument1 = new AuthorDocument(1L, "John", "Doe", LocalDate.of(1980, 5, 15), "American", "john.doe@example.com", "http://www.johndoe.com", "Author biography.", null);

        BookDocument book1 = new BookDocument(1L, "978-0-13-468599-1", "Introduction to Databases", "English", "Comprehensive guide to databases.", "Technology", authorDocument1);
        BookDocument book2 = new BookDocument(2L, "978-1-23-456789-0", "Data Science for Beginners", "English", "An introductory book on data science.", "Science", authorDocument1);

        List<BookDocument> booksAuthor1 = new LinkedList<>();
        booksAuthor1.add(book1);
        booksAuthor1.add(book2);
        authorDocument1.setBooksWritten(booksAuthor1);

        // Guardamos autores en BD, save de libros se hace en cascada
        this.authorRepository.save(authorDocument1);

        Logger.getGlobal().info("END INITIALIZING DB");
    }
}

