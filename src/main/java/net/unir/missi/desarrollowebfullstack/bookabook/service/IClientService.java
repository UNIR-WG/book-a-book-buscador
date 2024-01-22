package net.unir.missi.desarrollowebfullstack.bookabook.service;

import net.unir.missi.desarrollowebfullstack.bookabook.data.model.api.ClientDto;
import net.unir.missi.desarrollowebfullstack.bookabook.data.model.sql.Client;

import java.util.List;


public interface IClientService {

    List<Client> getAllClients();

    List<Client> getFilterClients(String firstName, String lastName, String address, String phoneNumber, String email);

    List<Client> getClientByFirstName(String firstName);

    List<Client> getClientByLastName(String lastName);

    List<Client> getClientByAddress(String address);

    List<Client> getClientByPhoneNumber(String phoneNumber);

    List<Client> getClientByEmail(String email);

    Client getClient(String clientId);

    Client addClient(ClientDto requestClient);

    Boolean deleteClient(String clientId);

    Client updateClient(String clientId, ClientDto requestClient);

    Client updateClientAttribute(String clientId, String requestClientAttribute);

}

