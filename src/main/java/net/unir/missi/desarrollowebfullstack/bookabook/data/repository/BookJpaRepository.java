package net.unir.missi.desarrollowebfullstack.bookabook.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import net.unir.missi.desarrollowebfullstack.bookabook.data.model.sql.BookModel;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

interface BookJpaRepository extends JpaRepository<BookModel, Long>, JpaSpecificationExecutor<BookModel> {

    List<BookModel> findByISBN(String ISBN);

    List<BookModel> findByName(String name);

    List<BookModel> findByLanguage(String language);

    List<BookModel> findByDescription(String description);

    List<BookModel> findByCategory(String category);

    List<BookModel> findByAuthor(Long id);

}