package net.unir.missi.desarrollowebfullstack.bookabook.converter.api;

import net.unir.missi.desarrollowebfullstack.bookabook.DTO.api.ClientResponse;
import net.unir.missi.desarrollowebfullstack.bookabook.DTO.memory.Client;
import net.unir.missi.desarrollowebfullstack.bookabook.model.ClientDocument;
import org.springframework.stereotype.Component;

@Component
public class ClientAPIConverter {

    public Client toMemory(final ClientResponse client)
    {
        if (client == null)
        {
            return null;
        }

        return new Client(
                client.getId(),
                client.getFirstName(),
                client.getLastName(),
                client.getAddress(),
                client.getPhoneNumber(),
                client.getEmail());
    }

    public ClientResponse fromMemory(final Client document) {
        if (document == null) {
            return null;
        }

        ClientResponse response = new ClientResponse();
        response.setId(document.id());
        response.setFirstName(document.firstName());
        response.setLastName(document.lastName());
        response.setAddress(document.address());
        response.setPhoneNumber(document.phoneNumber());
        response.setEmail(document.email());

        return response;
    }
}
