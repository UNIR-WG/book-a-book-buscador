package net.unir.missi.desarrollowebfullstack.bookabook.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.mergepatch.JsonMergePatch;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.unir.missi.desarrollowebfullstack.bookabook.DTO.api.AuthorResponse;
import net.unir.missi.desarrollowebfullstack.bookabook.DTO.memory.Author;
import net.unir.missi.desarrollowebfullstack.bookabook.converter.api.AuthorAPIConverter;
import net.unir.missi.desarrollowebfullstack.bookabook.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Authors Controller", description = "Microservicio encargado de exponer operaciones CRUD sobre autores alojados en una base de datos.")
public class AuthorController {

    @Autowired
    private AuthorAPIConverter converter;

    private final AuthorService service;

    private final ObjectMapper objectMapper;

    @GetMapping("/authors")
    @Operation(
            operationId = "Obtener autores",
            description = "Operacion de lectura y filtrado",
            summary = "Se devuelve una lista de todos los autores almacenados en la base de datos.")
    @ApiResponse(
            responseCode = "200",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthorResponse.class)))
    public ResponseEntity<List<AuthorResponse>> getAuthors(
            @Parameter(name = "firstName")
            @RequestParam(required = false) String firstName,
            @Parameter(name = "lastName")
            @RequestParam(required = false) String lastName,
            @Parameter(name = "birthDate", example = "YYYY-MM-DD")
            @RequestParam(required = false) LocalDate birthDate,
            @Parameter(name = "nationality")
            @RequestParam(required = false) String nationality,
            @Parameter(name = "email")
            @RequestParam(required = false) String email,
            @Parameter(name = "webSite")
            @RequestParam(required = false) String webSite,
            @Parameter(name = "biography")
            @RequestParam(required = false) String biography,
            @Parameter(name = "bookId")
            @RequestParam(required = false) Long bookId)
    {
            List<Author> request = service.getAllAuthors(firstName,lastName,birthDate,nationality,email,webSite,biography,bookId);
            if (request == null)
            {
                return ResponseEntity.ok(null);
            }
            return ResponseEntity.ok(request.stream().map(
                    (Author a) ->
                    {
                        return this.converter.fromMemory(a);
                    }
            ).collect(Collectors.toList()));
    }

    @GetMapping("/authors/{idAuthor}")
    @Operation(
            operationId = "Obtener autores por su id",
            description = "Operacion de lectura y filtrado",
            summary = "Se devuelve un autor almacenados en la base de datos con un id seleccionado.")
    @ApiResponse(
            responseCode = "200",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthorResponse.class)))
    public ResponseEntity<AuthorResponse> getAuthorById(@PathVariable String idAuthor)
    {
            Author author = service.getAuthorById(idAuthor);
            if(author != null)
                return ResponseEntity.ok(this.converter.fromMemory(author));
            else
                return ResponseEntity.notFound().build();

    }

    @PostMapping("/authors")
    @Operation(
            operationId = "Insercción de un autor.",
            description = "Operacion de escritura.",
            summary = "Se devuelve el autor insertado.")
    @ApiResponse(
            responseCode = "200",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthorResponse.class)))
    public ResponseEntity<AuthorResponse> addAuthor(@RequestBody AuthorResponse authorRequested)
    {
            if(authorRequested!=null) {
                Author newAuthor = service.createAuthor(this.converter.toMemory(authorRequested));
                return ResponseEntity.status(HttpStatus.CREATED).body(this.converter.fromMemory(newAuthor));
            }
            else
                return ResponseEntity.badRequest().build();

    }

    @PutMapping("/authors/{idAuthor}")
    @Operation(
            operationId = "Modificación total de un autor.",
            description = "Operacion de escritura.",
            summary = "Se devuelve el autor modificado.")
    @ApiResponse(
            responseCode = "200",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthorResponse.class)))
    public ResponseEntity<AuthorResponse> modifyAllAuthorData(@PathVariable String idAuthor, @RequestBody AuthorResponse authorData) {
            Author tempAuthor = service.getAuthorById(idAuthor);
            if(tempAuthor != null){
                    return ResponseEntity.ok(this.converter.fromMemory(service.modifyAllAuthorData(tempAuthor, this.converter.toMemory(authorData))));
            } else
                return ResponseEntity.notFound().build();

    }

    @PatchMapping("/authors/{idAuthor}")
    @Operation(
            operationId = "Modificación parcial de un autor.",
            description = "Operacion de escritura.",
            summary = "Se devuelve el autor modificado.")
    @ApiResponse(
            responseCode = "200",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthorResponse.class)))
    public ResponseEntity<AuthorResponse> modifyAuthorData(@PathVariable String idAuthor, @RequestBody String authorData) {
        try {
            JsonMergePatch jsonMergePatch = JsonMergePatch.fromJson(objectMapper.readTree(authorData));
            JsonNode target = jsonMergePatch.apply(objectMapper.readTree(objectMapper.writeValueAsString(authorData)));
            AuthorResponse authorPatched = objectMapper.treeToValue(target, AuthorResponse.class);

            Author tempAuthor = service.getAuthorById(idAuthor);

            if (tempAuthor!=null){
                return ResponseEntity.ok(this.converter.fromMemory(service.modifyAuthorData(tempAuthor, this.converter.toMemory(authorPatched))));
            } else
                return ResponseEntity.notFound().build();

        } catch (Exception e) {
            log.error("Error modifying author {}", e.getMessage(), e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/authors/{idAuthor}")
    @Operation(
            operationId = "Borrado de un autor.",
            description = "Operacion de escritura.",
            summary = "Se devuelve el autor eliminado.")
    @ApiResponse(
            responseCode = "200",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthorResponse.class)))
    public ResponseEntity<AuthorResponse> deleteAuthor(@PathVariable String idAuthor) {
            Author prev = service.getAuthorById(idAuthor);
            if (prev != null){
                return ResponseEntity.ok(this.converter.fromMemory(service.deleteAuthor(prev)));
            } else
                return ResponseEntity.notFound().build();

    }
}
