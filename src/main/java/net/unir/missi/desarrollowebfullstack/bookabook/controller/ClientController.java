package net.unir.missi.desarrollowebfullstack.bookabook.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.unir.missi.desarrollowebfullstack.bookabook.DTO.api.ClientResponse;
import net.unir.missi.desarrollowebfullstack.bookabook.DTO.api.DeleteResponse;
import net.unir.missi.desarrollowebfullstack.bookabook.DTO.memory.Client;
import net.unir.missi.desarrollowebfullstack.bookabook.converter.api.ClientAPIConverter;
import net.unir.missi.desarrollowebfullstack.bookabook.service.IClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Clients Controller")
public class ClientController {

    @Autowired
    private ClientAPIConverter converter;

    private final IClientService clientService;

    @GetMapping("/clients")
    @Operation(
            operationId = "Obtener clientes",
            description = "Operacion de lectura.",
            summary = "Se devuelve una lista de todos los clientes a partir de una búsqueda por filtro.")
    @ApiResponse(
            responseCode = "200",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ClientResponse.class)))
    @ApiResponse(
            responseCode = "400",
            content = @Content(mediaType = "application/json", schema = @Schema()),
            description = "Datos de cliente introducidos incorrectos.")
    public ResponseEntity<List<ClientResponse>> getClients(
            @Parameter(name = "firstName", description = "Nombre")
            @RequestParam(required = false) String firstName,
            @Parameter(name = "lastName", description = "Apellido")
            @RequestParam(required = false) String lastName,
            @Parameter(name = "address", description = "Dirección")
            @RequestParam(required = false) String address,
            @Parameter(name = "phoneNumber", description = "Teléfono")
            @RequestParam(required = false) String phoneNumber,
            @Parameter(name = "email", description = "Email")
            @RequestParam(required = false) String email) {

        List<Client> clientDocuments = clientService.getFilterClients(firstName, lastName, address, phoneNumber, email);
        if (clientDocuments == null)
        {
            return ResponseEntity.ok(null);
        }
        return ResponseEntity.ok(clientDocuments.stream().map(
                (Client a) ->
                {
                    return this.converter.fromMemory(a);
                }
        ).collect(Collectors.toList()));
    }

    @GetMapping("/clients/{clientId}")
    @Operation(
            operationId = "Obtener el detalle de un cliente.",
            description = "Operacion de lectura",
            summary = "Se devuelve la información de un cliente a partir de su identificador.")
    @ApiResponse(
            responseCode = "200",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ClientResponse.class)))
    @ApiResponse(
            responseCode = "404",
            content = @Content(mediaType = "application/json", schema = @Schema()),
            description = "No se ha encontrado el cliente con el identificador indicado.")
    public ResponseEntity<ClientResponse> getClient(@PathVariable String clientId) {
        Client clientDocument = clientService.getClient(clientId);
        return clientDocument != null ? ResponseEntity.ok(this.converter.fromMemory(clientDocument)) : ResponseEntity.notFound().build();
    }

    @PostMapping("/clients")
    @Operation(
            operationId = "Registrar un nuevo cliente.",
            description = "Operación de escritura.",
            summary = "Se crea un cliente a partir de sus datos.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos del cliente a crear.",
                    required = true,
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ClientResponse.class))))
    @ApiResponse(
            responseCode = "201",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ClientResponse.class)))
    @ApiResponse(
            responseCode = "400",
            content = @Content(mediaType = "application/json", schema = @Schema()),
            description = "Datos introducidos incorrectos.")
    public ResponseEntity<ClientResponse> addClient(@RequestBody ClientResponse requestClient) {
        ClientResponse clientDocumentAdded = this.converter.fromMemory(clientService.addClient(this.converter.toMemory(requestClient)));
        return clientDocumentAdded != null ? ResponseEntity.status(HttpStatus.CREATED).body(clientDocumentAdded) : ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/clients/{clientId}")
    @Operation(
            operationId = "Dar de baja un cliente.",
            description = "Operacion de escritura.",
            summary = "Se elimina un cliente a partir de su identificador.")
    @ApiResponse(
            responseCode = "200",
            content = @Content(mediaType = "application/json", schema = @Schema()))
    @ApiResponse(
            responseCode = "404",
            content = @Content(mediaType = "application/json", schema = @Schema()),
            description = "No se ha encontrado el cliente con el identificador indicado.")
    public ResponseEntity<DeleteResponse> deleteClient(@PathVariable String clientId) {
        Boolean clientDeleted = clientService.deleteClient(clientId);
        DeleteResponse deleteResponse = DeleteResponse.builder().message("Client deleted").build();
        return Boolean.TRUE.equals(clientDeleted) ? ResponseEntity.ok(deleteResponse) : ResponseEntity.notFound().build();
    }

    @PutMapping("/clients/{clientId}")
    @Operation(
            operationId = "Modificar totalmente un cliente.",
            description = "Operación de escritura.",
            summary = "Se modifica totalmente un cliente.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos del cliente a actualizar.",
                    required = true,
                    content = @Content(mediaType = "application/merge-patch+json", schema = @Schema(implementation = ClientResponse.class))))
    @ApiResponse(
            responseCode = "200",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ClientResponse.class)))
    @ApiResponse(
            responseCode = "404",
            content = @Content(mediaType = "application/json", schema = @Schema()),
            description = "Cliente no encontrado.")
    public ResponseEntity<ClientResponse> updateClient(@PathVariable String clientId, @RequestBody ClientResponse client) {
        ClientResponse updatedClientDocument = this.converter.fromMemory(clientService.updateClient(clientId, this.converter.toMemory(client)));
        return updatedClientDocument != null ? ResponseEntity.ok(updatedClientDocument) : ResponseEntity.notFound().build();
    }

    @PatchMapping("/clients/{clientId}")
    @Operation(
            operationId = "Modificar parcialmente un cliente.",
            description = "RFC 7386. Operación de escritura.",
            summary = "RFC 7386. Se modifica parcialmente un cliente.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos del cliente a modificar.",
                    required = true,
                    content = @Content(mediaType = "application/merge-patch+json", schema = @Schema(implementation = ClientResponse.class))))
    @ApiResponse(
            responseCode = "200",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ClientResponse.class)))
    @ApiResponse(
            responseCode = "404",
            content = @Content(mediaType = "application/json", schema = @Schema()),
            description = "Cliente no encontrado.")
    public ResponseEntity<ClientResponse> updateClientAttribute(@PathVariable String clientId, @RequestBody ClientResponse requestClientAttribute) {
        ClientResponse clientDocumentModified = this.converter.fromMemory(clientService.updateClientAttribute(clientId, this.converter.toMemory(requestClientAttribute)));
        return clientDocumentModified != null ? ResponseEntity.ok(clientDocumentModified) : ResponseEntity.notFound().build();
    }

}
