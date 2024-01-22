package net.unir.missi.desarrollowebfullstack.bookabook.data.repository;

import lombok.RequiredArgsConstructor;
import net.unir.missi.desarrollowebfullstack.bookabook.data.model.sql.Client;
import net.unir.missi.desarrollowebfullstack.bookabook.data.utils.SearchCriteria;
import net.unir.missi.desarrollowebfullstack.bookabook.data.utils.SearchOperation;
import net.unir.missi.desarrollowebfullstack.bookabook.data.utils.SearchStatement;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ClientRepository {

    private final ClientJpaRepository repository;

    public List<Client> getAllClients() {
        return repository.findAll();
    }

    public Client getClientById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public Client addClient(Client client) {
        return repository.save(client);
    }

    public void deleteClient(Client client) {
        repository.delete(client);
    }

    public List<Client> filterClients(String firstName, String lastName, String address, String phoneNumber, String email) {
        SearchCriteria<Client> spec = new SearchCriteria<>();

        if (StringUtils.isNotBlank(firstName)) {
            spec.add(new SearchStatement("firstName", firstName, SearchOperation.EQUAL));
        }

        if (StringUtils.isNotBlank(lastName)) {
            spec.add(new SearchStatement("lastName", lastName, SearchOperation.EQUAL));
        }

        if (StringUtils.isNotBlank(address)) {
            spec.add(new SearchStatement("address", address, SearchOperation.MATCH));
        }

        if (StringUtils.isNotBlank(phoneNumber)) {
            spec.add(new SearchStatement("phoneNumber", phoneNumber, SearchOperation.MATCH));
        }

        if (StringUtils.isNotBlank(email)) {
            spec.add(new SearchStatement("email", email, SearchOperation.MATCH));
        }

        return repository.findAll(spec);
    }

    public List<Client> filterClientByFirstName (String firstName){
        return repository.findByFirstName(firstName);
    }

    public List<Client> filterClientByLastName (String lastName){
        return repository.findByLastName(lastName);
    }

    public List<Client> filterClientByAddress (String address){
        return repository.findByAddress(address);
    }

    public List<Client> filterClientByPhoneNumber (String phoneNumber){
        return repository.findByPhoneNumber(phoneNumber);
    }

    public List<Client> filterClientByEmail (String email){
        return repository.findByEmail(email);
    }

}