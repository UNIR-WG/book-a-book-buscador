package net.unir.missi.desarrollowebfullstack.bookabook.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;
import net.unir.missi.desarrollowebfullstack.bookabook.data.model.sql.AuthorModel;

public interface AuthorRepository extends JpaRepository<AuthorModel, Long> {

    List<AuthorModel> findByFirstName(String firstName);
    List<AuthorModel> findByLastName(String lastName);
    List<AuthorModel> findByBirthDate(Date birthDate);
    List<AuthorModel> findByNationality(String nationality);
    List<AuthorModel> findByEmail(String email);
    List<AuthorModel> findByWebSite(String webSite);
    List<AuthorModel> findByBiography(String biography);


}