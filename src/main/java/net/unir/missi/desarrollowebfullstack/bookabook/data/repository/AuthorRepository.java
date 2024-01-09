package net.unir.missi.desarrollowebfullstack.bookabook.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import net.unir.missi.desarrollowebfullstack.bookabook.data.model.sql.AuthorModel;

public interface AuthorRepository extends JpaRepository<AuthorModel, Long> {


}