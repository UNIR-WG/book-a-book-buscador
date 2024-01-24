package net.unir.missi.desarrollowebfullstack.bookabook.service;

import net.unir.missi.desarrollowebfullstack.bookabook.model.api.ClientDto;
import net.unir.missi.desarrollowebfullstack.bookabook.model.sql.Client;

import java.util.List;


public interface IClientService {

    List<Client> getFilterClients(String firstName, String lastName, String address, String phoneNumber, String email);

    Client getClient(String clientId);

    Client addClient(ClientDto requestClient);

    Boolean deleteClient(String clientId);

    Client updateClient(String clientId, ClientDto requestClient);

    Client updateClientAttribute(String clientId, String requestClientAttribute);

}

