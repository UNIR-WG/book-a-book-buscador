package net.unir.missi.desarrollowebfullstack.bookabook.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import net.unir.missi.desarrollowebfullstack.bookabook.model.document.AuthorDocument;
import net.unir.missi.desarrollowebfullstack.bookabook.model.document.ClientDocument;
import net.unir.missi.desarrollowebfullstack.bookabook.config.search.SearchCriteria;
import net.unir.missi.desarrollowebfullstack.bookabook.config.search.SearchOperation;
import net.unir.missi.desarrollowebfullstack.bookabook.config.search.SearchStatement;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class ClientRepository {

    private final ClientElasticRepository repository;

    public ClientDocument getClientById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public List<ClientDocument> getAllClients() {
        List<ClientDocument> ret = new LinkedList<>();
        this.repository.findAll().forEach(ret::add);
        return ret;
    }

    public ClientDocument addClient(ClientDocument clientDocument) {
        return repository.save(clientDocument);
    }

    public void deleteClient(ClientDocument clientDocument) {
        repository.delete(clientDocument);
    }

    public List<ClientDocument> filterClients(String firstName, String lastName, String address, String phoneNumber, String email) {
        List<ClientDocument> listClientDocument = new LinkedList<>();
        repository.findAll().forEach(listClientDocument::add);

        SearchCriteria<ClientDocument> spec = new SearchCriteria<>();
        spec.add(new SearchStatement("firstName", firstName, SearchOperation.MATCH));
        spec.add(new SearchStatement("lastName", lastName, SearchOperation.MATCH));
        spec.add(new SearchStatement("address", address, SearchOperation.EQUAL));
        spec.add(new SearchStatement("phoneNumber", phoneNumber, SearchOperation.MATCH));
        spec.add(new SearchStatement("email", email, SearchOperation.EQUAL));

        spec.
        List<ClientDocument> filteredAuthorDocuments = listClientDocument.stream()
                .filter(author -> author.getBooksWritten().stream()
                        .anyMatch(book -> Objects.equals(booksWritten.getId(), book.getId())))
                .collect(Collectors.toList());

        return filteredAuthorDocuments;
    }

}