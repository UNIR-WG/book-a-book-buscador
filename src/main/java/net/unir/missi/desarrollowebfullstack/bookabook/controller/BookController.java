package net.unir.missi.desarrollowebfullstack.bookabook.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.unir.missi.desarrollowebfullstack.bookabook.data.model.api.BookDto;
import net.unir.missi.desarrollowebfullstack.bookabook.data.model.api.CreateBookRequest;
import net.unir.missi.desarrollowebfullstack.bookabook.data.model.sql.BookModel;
import net.unir.missi.desarrollowebfullstack.bookabook.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Book;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Books Controller", description = "Microservicio encargado de exponer operaciones CRUD sobre libros alojados en una base de datos.")
public class BookController {

    private final BookService service;

    @GetMapping("/books")
    @Operation(
            operationId = "Obtener libros",
            description = "Operacion de lectura",
            summary = "Se devuelve una lista de todos los libros almacenados en la base de datos.")
    @ApiResponse(
            responseCode = "200",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = BookModel.class)))
    public ResponseEntity<List<BookModel>> getBooks(
            @RequestHeader Map<String, String> headers,
            @Parameter(name = "isbn", description = "Código ISBN del libro", example = "", required = false)
            @RequestParam(required = false) String isbn,
            @Parameter(name = "name", description = "Nombre del libro", example = "", required = false)
            @RequestParam(required = false) String name,
            @Parameter(name = "language", description = "Idioma del libro", example = "ES", required = false)
            @RequestParam(required = false) String language,
            @Parameter(name = "descripton", description = "Descripción del libro. No tiene que ser exacto", example = "", required = false)
            @RequestParam(required = false) String description,
            @Parameter(name = "category", description = "Categoría del libro", example = "Ficción", required = false)
            @RequestParam(required = false) String category,
            @Parameter(name = "authorId", description = "Identificador del autor", example = "1", required = false)
            @RequestParam(required = false) Long authorId) {

        log.info("headers: {}", headers);
        List<BookModel> books = service.getBooks(isbn, name, language, description, category, authorId);

        if (books != null) {
            return ResponseEntity.ok(books);
        } else {
            return ResponseEntity.ok(Collections.emptyList());
        }
    }

    @GetMapping("/books/{bookId}")
    @Operation(
            operationId = "Obtener un libro",
            description = "Operacion de lectura",
            summary = "Se devuelve un libro a partir de su identificador.")
    @ApiResponse(
            responseCode = "200",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = BookModel.class)))
    @ApiResponse(
            responseCode = "404",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class)),
            description = "No se ha encontrado el libro con el identificador indicado.")
    public ResponseEntity<BookModel> getBook(@PathVariable String bookId) {

        log.info("Request received for book {}", bookId);
        BookModel book = service.getBook(bookId);

        if (book != null) {
            return ResponseEntity.ok(book);
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
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class)))
    @ApiResponse(
            responseCode = "404",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class)),
            description = "No se ha encontrado el libro con el identificador indicado.")
    public ResponseEntity<Void> deleteBook(@PathVariable String bookId) {

        Boolean removed = service.removeBook(bookId);

        if (Boolean.TRUE.equals(removed)) {
            return ResponseEntity.ok().build();
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
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CreateBookRequest.class))))
    @ApiResponse(
            responseCode = "201",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = BookModel.class)))
    @ApiResponse(
            responseCode = "400",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class)),
            description = "Datos incorrectos introducidos.")
    public ResponseEntity<BookModel> addBook(@RequestBody CreateBookRequest request) {

        BookModel createdBook = service.createBook(request);

        if (createdBook != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(createdBook);
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
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = BookModel.class)))
    @ApiResponse(
            responseCode = "400",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class)),
            description = "Producto inválido o datos incorrectos introducidos.")
    public ResponseEntity<BookModel> patchBook(@PathVariable String bookId, @RequestBody String patchBody) {

        BookModel patched = service.updateBook(bookId, patchBody);
        if (patched != null) {
            return ResponseEntity.ok(patched);
        } else {
            return ResponseEntity.badRequest().build();
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
                    content = @Content(mediaType = "application/merge-patch+json", schema = @Schema(implementation = BookDto.class))))
    @ApiResponse(
            responseCode = "200",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = BookModel.class)))
    @ApiResponse(
            responseCode = "404",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class)),
            description = "Producto no encontrado.")
    public ResponseEntity<BookModel> updateBook(@PathVariable String bookId, @RequestBody BookDto body) {

        BookModel updated = service.updateBook(bookId, body);
        if (updated != null) {
            return ResponseEntity.ok(updated);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
