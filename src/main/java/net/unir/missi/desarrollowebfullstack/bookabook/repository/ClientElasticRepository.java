package net.unir.missi.desarrollowebfullstack.bookabook.repository;

import net.unir.missi.desarrollowebfullstack.bookabook.model.ClientDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ClientElasticRepository extends ElasticsearchRepository<ClientDocument, Long> {

}