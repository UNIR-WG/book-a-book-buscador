package net.unir.missi.desarrollowebfullstack.bookabook.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.text.SimpleDateFormat;
import java.util.List;
import net.unir.missi.desarrollowebfullstack.bookabook.model.sql.Author;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorJpaRepository extends JpaRepository<Author, Long>, JpaSpecificationExecutor<Author> {

    List<Author> findByFirstName(String firstName);
    List<Author> findByLastName(String lastName);
    List<Author> findByBirthDate(SimpleDateFormat birthDate);
    List<Author> findByNationality(String nationality);
    List<Author> findByEmail(String email);
    List<Author> findByWebSite(String webSite);
    List<Author> findByBiography(String biography);

}