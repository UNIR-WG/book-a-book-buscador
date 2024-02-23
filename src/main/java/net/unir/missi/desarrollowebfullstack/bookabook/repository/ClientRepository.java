package net.unir.missi.desarrollowebfullstack.bookabook.repository;

import lombok.RequiredArgsConstructor;
import net.unir.missi.desarrollowebfullstack.bookabook.model.ClientDocument;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class ClientRepository {

    private static Long numClients = 1L;
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
        if (clientDocument.getId() == null)
        {
            clientDocument.setId(numClients);
            numClients++;
        }
        return repository.save(clientDocument);
    }

    public void deleteClient(ClientDocument clientDocument) {
        repository.delete(clientDocument);
    }

    public List<ClientDocument> filterClients(String firstName, String lastName, String address, String phoneNumber, String email) {
        List<ClientDocument> listClientDocument = new LinkedList<>();
        repository.findAll().forEach(listClientDocument::add);

        return listClientDocument.stream()
                .filter((ClientDocument doc) ->
                {
                    if (!doc.getFirstName().equals(firstName)) {
                        return false;
                    }
                    if (!doc.getLastName().equals(lastName)) {
                        return false;
                    }
                    if (!doc.getAddress().equals(address)) {
                        return false;
                    }
                    if (!doc.getPhoneNumber().equals(phoneNumber)) {
                        return false;
                    }
                    if (! doc.getEmail().equals(email))
                    {
                        return false;
                    }
                    return true;
                    // TODO filter by booksWritten
                })
                .collect(Collectors.toList());
    }

}