package net.unir.missi.desarrollowebfullstack.bookabook.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import net.unir.missi.desarrollowebfullstack.bookabook.model.document.AuthorDocument;
import org.springframework.stereotype.Repository;

public interface AuthorElasticRepository extends ElasticsearchRepository<AuthorDocument, Long> {

}