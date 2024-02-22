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
import net.unir.missi.desarrollowebfullstack.bookabook.DTO.api.BookResponse;
import net.unir.missi.desarrollowebfullstack.bookabook.DTO.api.DeleteResponse;
import net.unir.missi.desarrollowebfullstack.bookabook.DTO.memory.Book;
import net.unir.missi.desarrollowebfullstack.bookabook.converter.api.BookAPIConverter;
import net.unir.missi.desarrollowebfullstack.bookabook.model.BookDocument;
import net.unir.missi.desarrollowebfullstack.bookabook.service.IBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Books Controller", description = "Microservicio encargado de exponer operaciones CRUD sobre libros alojados en una base de datos.")
public class BookController {

    @Autowired
    private BookAPIConverter converter;

    private final IBookService service;

    private final ObjectMapper objectMapper;

    @GetMapping("/books")
    @Operation(
            operationId = "Obtener libros",
            description = "Operacion de lectura",
            summary = "Se devuelve una lista de todos los libros almacenados en la base de datos.")
    @ApiResponse(
            responseCode = "200",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = BookDocument.class)))
    public ResponseEntity<List<BookResponse>> getBooks(
            @RequestHeader Map<String, String> headers,
            @Parameter(name = "isbn", description = "Código ISBN del libro")  //Regex ?
            @RequestParam(required = false) String isbn,
            @Parameter(name = "name", description = "Nombre del libro")
            @RequestParam(required = false) String name,
            @Parameter(name = "language", description = "Idioma del libro (ES, EN)")  //Enum ?
            @RequestParam(required = false) String language,
            @Parameter(name = "descripton", description = "Descripción del libro")
            @RequestParam(required = false) String description,
            @Parameter(name = "category", description = "Categoría del libro")  //Enum ?
            @RequestParam(required = false) String category,
            @Parameter(name = "authorId", description = "Identificador del autor")
            @RequestParam(required = false) Long authorId) {

        log.info("headers: {}", headers);
        List<Book> books = service.getBooks(isbn, name, language, description, category, authorId);
        if (books == null)
        {
            return ResponseEntity.ok(null);
        }
        return ResponseEntity.ok(books.stream().map(
                (Book a) ->
                {
                    return this.converter.fromMemory(a);
                }
        ).collect(Collectors.toList()));
    }

    @GetMapping("/books/{bookId}")
    @Operation(
            operationId = "Obtener un libro",
            description = "Operacion de lectura",
            summary = "Se devuelve un libro a partir de su identificador.")
    @ApiResponse(
            responseCode = "200",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = BookDocument.class)))
    @ApiResponse(
            responseCode = "404",
            content = @Content(mediaType = "application/json", schema = @Schema()),
            description = "No se ha encontrado el libro con el identificador indicado.")
    public ResponseEntity<BookResponse> getBook(@PathVariable String bookId) {

        log.info("Request received for book {}", bookId);
        Book book = service.getBook(bookId);

        if (book != null) {
            return ResponseEntity.ok(this.converter.fromMemory(book));
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    @DeleteMapping("/books/{bookId}")
    @Operation(
            operationId = "Eliminar un libro",
            description = "Operacion de escritura",
            summary = "Se elimina un libro a partir de su identificador.")
    @ApiResponse(
            responseCode = "200",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = DeleteResponse.class)))
    @ApiResponse(
            responseCode = "404",
            content = @Content(mediaType = "application/json", schema = @Schema()),
            description = "No se ha encontrado el libro con el identificador indicado.")
    public ResponseEntity<DeleteResponse> deleteBook(@PathVariable String bookId) {

        Boolean removed = service.removeBook(bookId);

        if (Boolean.TRUE.equals(removed)) {
            DeleteResponse deleteResponse = DeleteResponse.builder().message("Book deleted").build();
            return ResponseEntity.ok(deleteResponse);
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    @PostMapping("/books")
    @Operation(
            operationId = "Insertar un libro",
            description = "Operacion de escritura",
            summary = "Se crea un libro a partir de sus datos.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos del libro a crear.",
                    required = true,
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = BookResponse.class))))
    @ApiResponse(
            responseCode = "201",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = BookDocument.class)))
    @ApiResponse(
            responseCode = "400",
            content = @Content(mediaType = "application/json", schema = @Schema()),
            description = "Datos incorrectos introducidos.")
    public ResponseEntity<BookResponse> addBook(@RequestBody BookResponse request) {

        Book createdBook = service.createBook(this.converter.toMemory(request));

        if (createdBook != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(this.converter.fromMemory(createdBook));
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PatchMapping("/books/{bookId}")
    @Operation(
            operationId = "Modificar parcialmente un libro",
            description = "RFC 7386. Operacion de escritura",
            summary = "RFC 7386. Se modifica parcialmente un libro.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos del libro a crear.",
                    required = true,
                    content = @Content(mediaType = "application/merge-patch+json", schema = @Schema(implementation = String.class))))
    @ApiResponse(
            responseCode = "200",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = BookDocument.class)))
    @ApiResponse(
            responseCode = "400",
            content = @Content(mediaType = "application/json", schema = @Schema()),
            description = "Producto inválido o datos incorrectos introducidos.")
    public ResponseEntity<BookResponse> patchBook(@PathVariable String bookId, @RequestBody String patchBody) {
        try
        {
            JsonMergePatch jsonMergePatch = JsonMergePatch.fromJson(objectMapper.readTree(patchBody));
            JsonNode target = jsonMergePatch.apply(objectMapper.readTree(objectMapper.writeValueAsString(patchBody)));
            BookResponse bookPatched = objectMapper.treeToValue(target, BookResponse.class);

            Book tempBook = service.getBook(bookId);

            if (tempBook != null) {
                return ResponseEntity.ok(this.converter.fromMemory(this.service.updateBookAttributes(bookId, this.converter.toMemory(bookPatched))));
            } else {
                return ResponseEntity.badRequest().build();
            }
        }
        catch (Exception e) {
            log.error("Error modifying author {}", e.getMessage(), e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("/books/{bookId}")
    @Operation(
            operationId = "Modificar totalmente un libro",
            description = "Operacion de escritura",
            summary = "Se modifica totalmente un libro.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos del libro a actualizar.",
                    required = true,
                    content = @Content(mediaType = "application/merge-patch+json", schema = @Schema(implementation = BookResponse.class))))
    @ApiResponse(
            responseCode = "200",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = BookDocument.class)))
    @ApiResponse(
            responseCode = "404",
            content = @Content(mediaType = "application/json", schema = @Schema()),
            description = "Producto no encontrado.")
    public ResponseEntity<BookResponse> updateBook(@PathVariable String bookId, @RequestBody BookResponse body) {
        BookResponse updatedBook = this.converter.fromMemory(service.updateBook(bookId, this.converter.toMemory(body)));
        if (updatedBook != null) {
            return ResponseEntity.ok(updatedBook);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
