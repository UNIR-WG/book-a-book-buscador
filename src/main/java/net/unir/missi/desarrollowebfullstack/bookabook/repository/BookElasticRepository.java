package net.unir.missi.desarrollowebfullstack.bookabook.repository;

import net.unir.missi.desarrollowebfullstack.bookabook.model.AuthorDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import net.unir.missi.desarrollowebfullstack.bookabook.model.BookDocument;

import java.util.List;

public interface BookElasticRepository extends ElasticsearchRepository<BookDocument, Long> {

    List<BookDocument> findByIsbn(String ISBN);

    List<BookDocument> findByName(String name);

    List<BookDocument> findByLanguage(String language);

    List<BookDocument> findByDescription(String description);

    List<BookDocument> findByCategory(String category);

    List<BookDocument> findByAuthorDocument(AuthorDocument authorDocument);

}