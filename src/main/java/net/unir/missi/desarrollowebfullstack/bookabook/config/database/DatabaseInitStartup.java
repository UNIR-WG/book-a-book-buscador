package net.unir.missi.desarrollowebfullstack.bookabook.config.database;

import jakarta.validation.constraints.NotNull;
import net.unir.missi.desarrollowebfullstack.bookabook.model.sql.Author;
import net.unir.missi.desarrollowebfullstack.bookabook.model.sql.Book;
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
                this.initializeDB();
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

        // Creamos autores sin libros
        Author author1 = new Author(1L, "John", "Doe", LocalDate.of(1980, 5, 15), "American", "john.doe@example.com", "http://www.johndoe.com", "Author biography.", null);
        /*new Author(2, 'Jane', 'Smith', '1975-08-22', 'British', 'jane.smith@example.com', 'http://www.janesmith.com', 'Another author biography.');
        new Author(3, 'Carlos', 'García', '1990-11-10', 'Spanish', 'carlos.garcia@example.com', 'http://www.carlosgarcia.com', 'Biografía del autor.');
        new Author(4, 'Laura', 'López', '1988-03-25', 'Mexican', 'laura.lopez@example.com', 'http://www.lauralopez.com', 'Biografía de Laura.');
        */

        // Creamos libros con autores asignados
        Book book1 = new Book(1L, "978-0-13-468599-1", "Introduction to Databases", "English", "Comprehensive guide to databases.", "Technology", author1);
        Book book2 = new Book(2L, "978-1-23-456789-0", "Data Science for Beginners", "English", "An introductory book on data science.", "Science", author1);
        /*(3, '978-2-34-567890-1', 'Programming in Python', 'English', 'Learn Python programming language.', 'Programming', 3),
        (4, '978-3-45-678901-2', 'Historia de la Informática', 'Spanish', 'Una exploración de la historia de la informática.', 'Tecnología', 4),
        (5, '978-4-56-789012-3', 'Machine Learning Basics', 'English', 'Fundamentals of machine learning.', 'Science', 1),
        (6, '978-5-67-890123-4', 'Advanced SQL Techniques', 'English', 'Mastering advanced SQL queries.', 'Technology', 2),
        (7, '978-6-78-901234-5', 'Matemáticas para Programadores', 'Spanish', 'Conceptos matemáticos aplicados a la programación.', 'Ciencia', 3),
        (8, '978-7-89-012345-6', 'Web Development with JavaScript', 'English', 'Building dynamic web applications.', 'Programming', 4),
        (9, '978-8-90-123456-7', 'Introducción a la Inteligencia Artificial', 'Spanish', 'Principios fundamentales de la inteligencia artificial.', 'Ciencia', 1),
        (10, '978-9-01-234567-8', 'Cybersecurity Essentials', 'English', 'Protecting systems and networks from cyber threats.', 'Technology', 2),
        (11, '978-0-12-345678-9', 'Big Data Analytics', 'English', 'Analyzing large datasets for insights.', 'Science', 3),
        (12, '978-1-23-456789-0', 'Desarrollo Ágil de Software', 'Spanish', 'Prácticas ágiles en el desarrollo de software.', 'Tecnología', 4),
        (13, '978-2-34-567890-1', 'JavaScript Frameworks', 'English', 'Exploring popular JavaScript frameworks.', 'Programming', 1),
        (14, '978-3-45-678901-2', 'Estadísticas para Data Science', 'Spanish', 'Conceptos estadísticos aplicados a la ciencia de datos.', 'Ciencia', 1),
        (15, '978-4-56-789012-3', 'Cloud Computing Basics', 'English', 'Understanding cloud computing concepts.', 'Technology', 2),
        (16, '978-5-67-890123-4', 'Python Web Frameworks', 'English', 'Building web applications with Python frameworks.', 'Programming', 4),
        (17, '978-6-78-901234-5', 'Programación Orientada a Objetos', 'Spanish', 'Principios de programación orientada a objetos.', 'Ciencia', 1),
        (18, '978-7-89-012345-6', 'Artificial Neural Networks', 'English', 'Understanding artificial neural networks.', 'Science', 2),
        (19, '978-8-90-123456-7', 'Seguridad Informática Avanzada', 'Spanish', 'Principios avanzados de seguridad informática.', 'Tecnología', 3);*/

        // Creamos arrays de libros para asignar a los autores (ser congruentes con la asignación en el otro lado de la
        // relación)
        // Libros del autor 1
        List<Book> booksAuthor1 = new LinkedList<>();
        booksAuthor1.add(book1);
        booksAuthor1.add(book2);
        author1.setBooksWritted(booksAuthor1);

        // Guardamos autores en BD, save de libros se hace en cascada
        this.authorRepository.save(author1);

        Logger.getGlobal().info("END INITIALIZING DB");
    }
}

