package net.unir.missi.desarrollowebfullstack.bookabook.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;
import net.unir.missi.desarrollowebfullstack.bookabook.data.model.sql.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {

    List<Author> findByFirstName(String firstName);
    List<Author> findByLastName(String lastName);
    List<Author> findByBirthDate(Date birthDate);
    List<Author> findByNationality(String nationality);
    List<Author> findByEmail(String email);
    List<Author> findByWebSite(String webSite);
    List<Author> findByBiography(String biography);


}