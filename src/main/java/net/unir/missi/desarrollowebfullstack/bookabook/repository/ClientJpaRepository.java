package net.unir.missi.desarrollowebfullstack.bookabook.repository;

import net.unir.missi.desarrollowebfullstack.bookabook.model.sql.Book;
import net.unir.missi.desarrollowebfullstack.bookabook.model.sql.Client;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
interface ClientJpaRepository extends ElasticsearchRepository<Client, Long> {

}