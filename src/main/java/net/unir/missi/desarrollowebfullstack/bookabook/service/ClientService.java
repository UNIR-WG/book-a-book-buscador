package net.unir.missi.desarrollowebfullstack.bookabook.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatchException;
import com.github.fge.jsonpatch.mergepatch.JsonMergePatch;
import lombok.extern.slf4j.Slf4j;
import net.unir.missi.desarrollowebfullstack.bookabook.DTO.api.ClientResponse;
import net.unir.missi.desarrollowebfullstack.bookabook.DTO.memory.Client;
import net.unir.missi.desarrollowebfullstack.bookabook.converter.api.AuthorAPIConverter;
import net.unir.missi.desarrollowebfullstack.bookabook.converter.api.ClientAPIConverter;
import net.unir.missi.desarrollowebfullstack.bookabook.converter.memory.AuthorMemoryConverter;
import net.unir.missi.desarrollowebfullstack.bookabook.converter.memory.ClientMemoryConverter;
import net.unir.missi.desarrollowebfullstack.bookabook.model.ClientDocument;
import net.unir.missi.desarrollowebfullstack.bookabook.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ClientService implements IClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ClientMemoryConverter clientMemoryConverter;

    @Override
    public List<Client> getFilterClients(String firstName, String lastName, String address, String phoneNumber, String email) {

        if (firstName!=null
                || lastName!=null
                || address!=null
                || phoneNumber!=null
                || email!=null) {

            return clientRepository.filterClients(firstName, lastName, address, phoneNumber, email).stream().map((ClientDocument c) ->
            {
                return this.clientMemoryConverter.fromDocument(c);
            }).collect(Collectors.toList());
        }else{
            return clientRepository.getAllClients().stream().map((ClientDocument c) ->
            {
                return this.clientMemoryConverter.fromDocument(c);
            }).collect(Collectors.toList());
        }
    }

    @Override
    public Client getClient(String clientId) {
        return this.clientMemoryConverter.fromDocument(clientRepository.getClientById(Long.valueOf(clientId)));
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
    public Client addClient(Client requestClient) {

        if (requestClient != null && StringUtils.hasLength(requestClient.firstName().trim())
                && StringUtils.hasLength(requestClient.lastName().trim())
                && StringUtils.hasLength(requestClient.address().trim())
                && StringUtils.hasLength(requestClient.phoneNumber().trim())
                && StringUtils.hasLength(requestClient.email().trim())) {

            ClientDocument newClientDocument = ClientDocument.builder()
                    .firstName(requestClient.firstName())
                    .lastName(requestClient.lastName())
                    .address(requestClient.address())
                    .phoneNumber(requestClient.phoneNumber())
                    .email(requestClient.email())
                    .build();

            return this.clientMemoryConverter.fromDocument(clientRepository.addClient(newClientDocument));
        }
        else {
            return null;
        }
    }


    @Override
    public Client updateClient(String clientId, Client requestClient) {
        if (StringUtils.hasLength(requestClient.firstName().trim())
                || StringUtils.hasLength(requestClient.lastName().trim())
                || StringUtils.hasLength(requestClient.address().trim())
                || StringUtils.hasLength(requestClient.phoneNumber().trim())
                || StringUtils.hasLength(requestClient.email().trim())) {

            // Get the author to check if exists
            ClientDocument clientDocument = clientRepository.getClientById(Long.valueOf(clientId));

            if (clientDocument != null) {
                ClientDocument newClientDocument = ClientDocument.builder()
                        .id(requestClient.id())
                        .firstName(requestClient.firstName())
                        .lastName(requestClient.lastName())
                        .address(requestClient.address())
                        .phoneNumber(requestClient.phoneNumber())
                        .email(requestClient.email())
                        .build();

                return this.clientMemoryConverter.fromDocument(clientRepository.addClient(newClientDocument));
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    @Override
    public Client updateClientAttribute(String clientId, Client requestClientAttribute) {
        ClientDocument clientDocument = clientRepository.getClientById(Long.valueOf(clientId));
        if (clientDocument != null) {
            if (requestClientAttribute.firstName() != null)
            {
                clientDocument.setFirstName(requestClientAttribute.firstName());
            }
            if (requestClientAttribute.lastName() != null)
            {
                clientDocument.setFirstName(requestClientAttribute.lastName());
            }
            if (requestClientAttribute.phoneNumber() != null)
            {
                clientDocument.setFirstName(requestClientAttribute.phoneNumber());
            }
            if (requestClientAttribute.email() != null)
            {
                clientDocument.setFirstName(requestClientAttribute.email());
            }
            if (requestClientAttribute.address() != null)
            {
                clientDocument.setFirstName(requestClientAttribute.address());
            }

            clientRepository.addClient(clientDocument);
            return this.clientMemoryConverter.fromDocument(clientDocument);
        } else {
            return null;
        }
    }
}
