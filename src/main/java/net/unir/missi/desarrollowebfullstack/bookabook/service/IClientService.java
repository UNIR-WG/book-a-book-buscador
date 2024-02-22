package net.unir.missi.desarrollowebfullstack.bookabook.service;

import net.unir.missi.desarrollowebfullstack.bookabook.DTO.memory.Client;

import java.util.List;


public interface IClientService {

    List<Client> getFilterClients(String firstName, String lastName, String address, String phoneNumber, String email);

    Client getClient(String clientId);

    Client addClient(Client requestClient);

    Boolean deleteClient(String clientId);

    Client updateClient(String clientId, Client requestClient);

    Client updateClientAttribute(String clientId, Client requestClientAttribute);

}

