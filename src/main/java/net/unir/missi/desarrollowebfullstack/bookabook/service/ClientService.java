package net.unir.missi.desarrollowebfullstack.bookabook.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatchException;
import com.github.fge.jsonpatch.mergepatch.JsonMergePatch;
import lombok.extern.slf4j.Slf4j;
import net.unir.missi.desarrollowebfullstack.bookabook.data.model.api.ClientDto;
import net.unir.missi.desarrollowebfullstack.bookabook.data.model.sql.Client;
import net.unir.missi.desarrollowebfullstack.bookabook.data.repository.ClientRepository;
import net.unir.missi.desarrollowebfullstack.bookabook.data.utils.SearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class ClientService implements IClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public List<Client> getAllClients(){
        return clientRepository.getAllClients();
    }

    @Override
    public List<Client> getFilterClients(String firstName, String lastName, String address, String phoneNumber, String email) {
        return clientRepository.filterClients(firstName, lastName, address, phoneNumber, email);
    }

    @Override
    public List<Client> getClientByFirstName(String firstName) {
        return clientRepository.filterClientByFirstName(firstName);
    }

    @Override
    public List<Client> getClientByLastName(String lastName) {
        return clientRepository.filterClientByLastName(lastName);
    }

    @Override
    public List<Client> getClientByAddress(String address) {
        return clientRepository.filterClientByAddress(address);
    }

    @Override
    public List<Client> getClientByPhoneNumber(String phoneNumber) {
        return clientRepository.filterClientByPhoneNumber(phoneNumber);
    }

    @Override
    public List<Client> getClientByEmail(String email) {
        return clientRepository.filterClientByEmail(email);
    }

    @Override
    public Client getClient(String clientId) {
        return clientRepository.getClientById(Long.valueOf(clientId));
    }

    @Override
    public Boolean deleteClient(String clientId) {

        Client client = clientRepository.getClientById(Long.valueOf(clientId));

        if (client != null) {
            clientRepository.deleteClient(client);
            return Boolean.TRUE;
        } else {
            return Boolean.FALSE;
        }
    }

    @Override
    public Client addClient(ClientDto requestClient) {

        if (requestClient != null && StringUtils.hasLength(requestClient.getFirstName().trim())
                && StringUtils.hasLength(requestClient.getLastName().trim())
                && StringUtils.hasLength(requestClient.getAddress().trim())
                && StringUtils.hasLength(requestClient.getPhoneNumber().trim())
                && StringUtils.hasLength(requestClient.getEmail().trim())) {

            Client newClient = Client.builder()
                    .firstName(requestClient.getFirstName())
                    .lastName(requestClient.getLastName())
                    .address(requestClient.getAddress())
                    .phoneNumber(requestClient.getPhoneNumber())
                    .email(requestClient.getEmail())
                    .build();

            return clientRepository.addClient(newClient);
        }
        else {
            return null;
        }
    }


    @Override
    public Client updateClient(String clientId, ClientDto requestClient) {
        if (StringUtils.hasLength(requestClient.getFirstName().trim())
                || StringUtils.hasLength(requestClient.getLastName().trim())
                || StringUtils.hasLength(requestClient.getAddress().trim())
                || StringUtils.hasLength(requestClient.getPhoneNumber().trim())
                || StringUtils.hasLength(requestClient.getEmail().trim())) {

            // Get the author to check if exists
            Client client = clientRepository.getClientById(Long.valueOf(clientId));

            if (client != null) {
                Client newClient = Client.builder()
                        .id(client.getId())
                        .firstName(requestClient.getFirstName())
                        .lastName(requestClient.getLastName())
                        .address(requestClient.getAddress())
                        .phoneNumber(requestClient.getPhoneNumber())
                        .email(requestClient.getEmail())
                        .build();

                return clientRepository.addClient(newClient);
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    @Override
    public Client updateClientAttribute(String clientId, String requestClientAttribute) {
        Client client = clientRepository.getClientById(Long.valueOf(clientId));
        if (client != null) {
            Client clientSelected = Client.builder()
                    .id(client.getId())
                    .firstName(client.getFirstName())
                    .lastName(client.getLastName())
                    .address(client.getAddress())
                    .phoneNumber(client.getPhoneNumber())
                    .email(client.getEmail())
                    .build();
            try {
                JsonMergePatch jsonMergePatch = JsonMergePatch.fromJson(objectMapper.readTree(requestClientAttribute));
                JsonNode target = jsonMergePatch.apply(objectMapper.readTree(objectMapper.writeValueAsString(clientSelected)));
                Client patchedClient = objectMapper.treeToValue(target, Client.class);
                clientRepository.addClient(patchedClient);
                return patchedClient;
            } catch (JsonProcessingException | JsonPatchException e) {
                log.error("Error updating client {}", clientId, e);
                return null;
            }
        } else {
            return null;
        }
    }
}
