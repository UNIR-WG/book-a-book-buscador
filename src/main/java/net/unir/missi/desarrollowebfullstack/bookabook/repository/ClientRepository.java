package net.unir.missi.desarrollowebfullstack.bookabook.repository;

import lombok.RequiredArgsConstructor;
import net.unir.missi.desarrollowebfullstack.bookabook.model.sql.Client;
import net.unir.missi.desarrollowebfullstack.bookabook.config.search.SearchCriteria;
import net.unir.missi.desarrollowebfullstack.bookabook.config.search.SearchOperation;
import net.unir.missi.desarrollowebfullstack.bookabook.config.search.SearchStatement;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ClientRepository {

    private final ClientJpaRepository repository;

    public Client getClientById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public List<Client> getAllClients() {
        return repository.findAll();
    }

    public Client addClient(Client client) {
        return repository.save(client);
    }

    public void deleteClient(Client client) {
        repository.delete(client);
    }

    private void addSearchStatement(SearchCriteria<Client> spec, String key, String value, SearchOperation operation) {
        if (StringUtils.isNotBlank(key)) {
            spec.add(new SearchStatement(key, value, operation));
        }
    }

    public List<Client> filterClients(String firstName, String lastName, String address, String phoneNumber, String email) {
        SearchCriteria<Client> spec = new SearchCriteria<>();

        this.addSearchStatement(spec,"firstName", firstName, SearchOperation.MATCH);
        this.addSearchStatement(spec, "lastName", lastName, SearchOperation.MATCH);
        this.addSearchStatement(spec, "address", address, SearchOperation.MATCH);
        this.addSearchStatement(spec, "phoneNumber", phoneNumber, SearchOperation.MATCH);
        this.addSearchStatement(spec, "email", email, SearchOperation.EQUAL);

        return repository.findAll(spec);
    }

}