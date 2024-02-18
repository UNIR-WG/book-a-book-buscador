package net.unir.missi.desarrollowebfullstack.bookabook.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import net.unir.missi.desarrollowebfullstack.bookabook.model.sql.Author;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorJpaRepository extends ElasticsearchRepository<Author, Long> {

}