package net.unir.missi.desarrollowebfullstack.bookabook.converter.memory;

import net.unir.missi.desarrollowebfullstack.bookabook.model.ClientDocument;
import net.unir.missi.desarrollowebfullstack.bookabook.DTO.memory.Client;
import net.unir.missi.desarrollowebfullstack.bookabook.repository.BookRepository;
import net.unir.missi.desarrollowebfullstack.bookabook.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ClientMemoryConverter {

    public ClientDocument toDocument(final Client client)
    {
        if (client == null)
        {
            return null;
        }
        
        final ClientDocument document = new ClientDocument();
        document.setId(client.id());
        document.setFirstName(client.firstName());
        document.setLastName(client.lastName());
        document.setAddress(client.address());
        document.setPhoneNumber(client.phoneNumber());
        document.setEmail(client.email());

        return document;
    }

    public Client fromDocument(final ClientDocument document) {
        if (document == null) {
            return null;
        }

        return new Client(
                document.getId(),
                document.getFirstName(),
                document.getLastName(),
                document.getAddress(),
                document.getPhoneNumber(),
                document.getEmail());
    }
}
