package net.unir.missi.desarrollowebfullstack.bookabook.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatchException;
import com.github.fge.jsonpatch.mergepatch.JsonMergePatch;
import lombok.extern.slf4j.Slf4j;
import net.unir.missi.desarrollowebfullstack.bookabook.model.api.ClientDto;
import net.unir.missi.desarrollowebfullstack.bookabook.model.document.ClientDocument;
import net.unir.missi.desarrollowebfullstack.bookabook.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@Slf4j
public class ClientService implements IClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public List<ClientDocument> getFilterClients(String firstName, String lastName, String address, String phoneNumber, String email) {

        if (firstName!=null
                || lastName!=null
                || address!=null
                || phoneNumber!=null
                || email!=null) {

            return clientRepository.filterClients(firstName, lastName, address, phoneNumber, email);
        }else{
            return clientRepository.getAllClients();
        }
    }

    @Override
    public ClientDocument getClient(String clientId) {
        return clientRepository.getClientById(Long.valueOf(clientId));
    }

    @Override
    public Boolean deleteClient(String clientId) {

        ClientDocument clientDocument = clientRepository.getClientById(Long.valueOf(clientId));

        if (clientDocument != null) {
            clientRepository.deleteClient(clientDocument);
            return Boolean.TRUE;
        } else {
            return Boolean.FALSE;
        }
    }

    @Override
    public ClientDocument addClient(ClientDto requestClient) {

        if (requestClient != null && StringUtils.hasLength(requestClient.getFirstName().trim())
                && StringUtils.hasLength(requestClient.getLastName().trim())
                && StringUtils.hasLength(requestClient.getAddress().trim())
                && StringUtils.hasLength(requestClient.getPhoneNumber().trim())
                && StringUtils.hasLength(requestClient.getEmail().trim())) {

            ClientDocument newClientDocument = ClientDocument.builder()
                    .firstName(requestClient.getFirstName())
                    .lastName(requestClient.getLastName())
                    .address(requestClient.getAddress())
                    .phoneNumber(requestClient.getPhoneNumber())
                    .email(requestClient.getEmail())
                    .build();

            return clientRepository.addClient(newClientDocument);
        }
        else {
            return null;
        }
    }


    @Override
    public ClientDocument updateClient(String clientId, ClientDto requestClient) {
        if (StringUtils.hasLength(requestClient.getFirstName().trim())
                || StringUtils.hasLength(requestClient.getLastName().trim())
                || StringUtils.hasLength(requestClient.getAddress().trim())
                || StringUtils.hasLength(requestClient.getPhoneNumber().trim())
                || StringUtils.hasLength(requestClient.getEmail().trim())) {

            // Get the author to check if exists
            ClientDocument clientDocument = clientRepository.getClientById(Long.valueOf(clientId));

            if (clientDocument != null) {
                ClientDocument newClientDocument = ClientDocument.builder()
                        .id(clientDocument.getId())
                        .firstName(requestClient.getFirstName())
                        .lastName(requestClient.getLastName())
                        .address(requestClient.getAddress())
                        .phoneNumber(requestClient.getPhoneNumber())
                        .email(requestClient.getEmail())
                        .build();

                return clientRepository.addClient(newClientDocument);
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    @Override
    public ClientDocument updateClientAttribute(String clientId, String requestClientAttribute) {
        ClientDocument clientDocument = clientRepository.getClientById(Long.valueOf(clientId));
        if (clientDocument != null) {
            ClientDocument clientDocumentSelected = ClientDocument.builder()
                    .id(clientDocument.getId())
                    .firstName(clientDocument.getFirstName())
                    .lastName(clientDocument.getLastName())
                    .address(clientDocument.getAddress())
                    .phoneNumber(clientDocument.getPhoneNumber())
                    .email(clientDocument.getEmail())
                    .build();
            try {
                JsonMergePatch jsonMergePatch = JsonMergePatch.fromJson(objectMapper.readTree(requestClientAttribute));
                JsonNode target = jsonMergePatch.apply(objectMapper.readTree(objectMapper.writeValueAsString(clientDocumentSelected)));
                ClientDocument patchedClientDocument = objectMapper.treeToValue(target, ClientDocument.class);
                clientRepository.addClient(patchedClientDocument);
                return patchedClientDocument;
            } catch (JsonProcessingException | JsonPatchException e) {
                log.error("Error updating client {}", clientId, e);
                return null;
            }
        } else {
            return null;
        }
    }
}
