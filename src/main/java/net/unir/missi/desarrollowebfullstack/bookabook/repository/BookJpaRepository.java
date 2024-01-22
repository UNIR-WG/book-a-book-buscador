package net.unir.missi.desarrollowebfullstack.bookabook.repository;

import net.unir.missi.desarrollowebfullstack.bookabook.model.sql.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import net.unir.missi.desarrollowebfullstack.bookabook.model.sql.Book;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
interface BookJpaRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {

    List<Book> findByIsbn(String ISBN);

    List<Book> findByName(String name);

    List<Book> findByLanguage(String language);

    List<Book> findByDescription(String description);

    List<Book> findByCategory(String category);

    List<Book> findByAuthor(Author author);

}