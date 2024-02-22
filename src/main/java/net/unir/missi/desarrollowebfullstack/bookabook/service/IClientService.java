package net.unir.missi.desarrollowebfullstack.bookabook.service;

import net.unir.missi.desarrollowebfullstack.bookabook.model.api.ClientDto;
import net.unir.missi.desarrollowebfullstack.bookabook.model.document.ClientDocument;

import java.util.List;


public interface IClientService {

    List<ClientDocument> getFilterClients(String firstName, String lastName, String address, String phoneNumber, String email);

    ClientDocument getClient(String clientId);

    ClientDocument addClient(ClientDto requestClient);

    Boolean deleteClient(String clientId);

    ClientDocument updateClient(String clientId, ClientDto requestClient);

    ClientDocument updateClientAttribute(String clientId, String requestClientAttribute);

}

