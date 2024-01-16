package net.unir.missi.desarrollowebfullstack.bookabook.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import net.unir.missi.desarrollowebfullstack.bookabook.data.model.sql.Book;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

interface BookJpaRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {

    List<Book> findByISBN(String ISBN);

    List<Book> findByName(String name);

    List<Book> findByLanguage(String language);

    List<Book> findByDescription(String description);

    List<Book> findByCategory(String category);

    List<Book> findByAuthor(Long id);

}