package net.unir.missi.desarrollowebfullstack.bookabook.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.unir.missi.desarrollowebfullstack.bookabook.model.api.DeleteResponse;
import net.unir.missi.desarrollowebfullstack.bookabook.model.api.ClientDto;
import net.unir.missi.desarrollowebfullstack.bookabook.model.document.ClientDocument;
import net.unir.missi.desarrollowebfullstack.bookabook.service.IClientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Clients Controller")
public class ClientController {

    private final IClientService clientService;

    @GetMapping("/clients")
    @Operation(
            operationId = "Obtener clientes",
            description = "Operacion de lectura.",
            summary = "Se devuelve una lista de todos los clientes a partir de una búsqueda por filtro.")
    @ApiResponse(
            responseCode = "200",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ClientDocument.class)))
    @ApiResponse(
            responseCode = "400",
            content = @Content(mediaType = "application/json", schema = @Schema()),
            description = "Datos de cliente introducidos incorrectos.")
    public ResponseEntity<List<ClientDocument>> getClients(
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

        List<ClientDocument> clientDocuments = clientService.getFilterClients(firstName, lastName, address, phoneNumber, email);
        return clientDocuments != null ? ResponseEntity.ok(clientDocuments) : ResponseEntity.notFound().build();
    }

    @GetMapping("/clients/{clientId}")
    @Operation(
            operationId = "Obtener el detalle de un cliente.",
            description = "Operacion de lectura",
            summary = "Se devuelve la información de un cliente a partir de su identificador.")
    @ApiResponse(
            responseCode = "200",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ClientDocument.class)))
    @ApiResponse(
            responseCode = "404",
            content = @Content(mediaType = "application/json", schema = @Schema()),
            description = "No se ha encontrado el cliente con el identificador indicado.")
    public ResponseEntity<ClientDocument> getClient(@PathVariable String clientId) {
        ClientDocument clientDocument = clientService.getClient(clientId);
        return clientDocument != null ? ResponseEntity.ok(clientDocument) : ResponseEntity.notFound().build();
    }

    @PostMapping("/clients")
    @Operation(
            operationId = "Registrar un nuevo cliente.",
            description = "Operación de escritura.",
            summary = "Se crea un cliente a partir de sus datos.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos del cliente a crear.",
                    required = true,
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ClientDto.class))))
    @ApiResponse(
            responseCode = "201",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ClientDocument.class)))
    @ApiResponse(
            responseCode = "400",
            content = @Content(mediaType = "application/json", schema = @Schema()),
            description = "Datos introducidos incorrectos.")
    public ResponseEntity<ClientDocument> addClient(@RequestBody ClientDto requestClient) {
        ClientDocument clientDocumentAdded = clientService.addClient(requestClient);
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
                    content = @Content(mediaType = "application/merge-patch+json", schema = @Schema(implementation = ClientDto.class))))
    @ApiResponse(
            responseCode = "200",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ClientDocument.class)))
    @ApiResponse(
            responseCode = "404",
            content = @Content(mediaType = "application/json", schema = @Schema()),
            description = "Cliente no encontrado.")
    public ResponseEntity<ClientDocument> updateClient(@PathVariable String clientId, @RequestBody ClientDto client) {
        ClientDocument updatedClientDocument = clientService.updateClient(clientId, client);
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
                    content = @Content(mediaType = "application/merge-patch+json", schema = @Schema(implementation = ClientDto.class))))
    @ApiResponse(
            responseCode = "200",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ClientDocument.class)))
    @ApiResponse(
            responseCode = "404",
            content = @Content(mediaType = "application/json", schema = @Schema()),
            description = "Cliente no encontrado.")
    public ResponseEntity<ClientDocument> updateClientAttribute(@PathVariable String clientId, @RequestBody String requestClientAttribute) {
        ClientDocument clientDocumentModified = clientService.updateClientAttribute(clientId, requestClientAttribute);
        return clientDocumentModified != null ? ResponseEntity.ok(clientDocumentModified) : ResponseEntity.notFound().build();
    }

}
