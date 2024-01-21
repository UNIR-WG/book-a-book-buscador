package net.unir.missi.desarrollowebfullstack.bookabook.controller;

import com.github.fge.jsonpatch.mergepatch.JsonMergePatch;
import io.swagger.v3.oas.annotations.Parameter;
import net.unir.missi.desarrollowebfullstack.bookabook.data.model.sql.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import net.unir.missi.desarrollowebfullstack.bookabook.data.model.api.AuthorRequest;
import net.unir.missi.desarrollowebfullstack.bookabook.service.AuthorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Objects;


@RestController
@RequiredArgsConstructor
@Slf4j
public class AuthorController {

    private final AuthorService service;

    @Autowired
    private final ObjectMapper objectMapper;

    @GetMapping("/authors")
    public ResponseEntity<List<AuthorRequest>> getAuthors(
            @Parameter(name = "firstName", example = "Juan")
            @RequestParam(required = false) String firstName,
            @Parameter(name = "lastName", example = "Garcia")
            @RequestParam(required = false) String lastName,
            @Parameter(name = "birthDate", example = "2024-01-20")
            @RequestParam(required = false) LocalDate birthDate,
            @Parameter(name = "nationality", example = "spanish")
            @RequestParam(required = false) String nationality,
            @Parameter(name = "email", example = "example@example.com")
            @RequestParam(required = false) String email,
            @Parameter(name = "webSite", example = "bokkabook.com")
            @RequestParam(required = false) String webSite,
            @Parameter(name = "biography")
            @RequestParam(required = false) String biography,
            @Parameter(name = "bookId")
            @RequestParam(required = false) Long booksWritted)
    {

        try {
            List<AuthorRequest> request = service.getAllAuthors(firstName,lastName,birthDate,nationality,email,webSite,biography,booksWritted);
            return ResponseEntity.ok(Objects.requireNonNullElse(request, Collections.emptyList()));
        } catch (Exception e) {
            log.error("Error getting authors list {}", e.getMessage(), e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/authors/{idAuthor}")
    public ResponseEntity<AuthorRequest> getAuthorById(@PathVariable String idAuthor)
    {
        try {
            AuthorRequest author = service.getAuthorById(idAuthor);
            log.error("Error AUTHOR {}", author);

            if(author!=null)
                return ResponseEntity.ok(author);
            else
                return ResponseEntity.notFound().build();

        } catch (Exception e) {
            log.error("Error getting author {}", e.getMessage(), e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/authors")
    public ResponseEntity<AuthorRequest> addAuthor(@RequestBody AuthorRequest authorRequested)
    {
        try {
            if(authorRequested!=null) {
                AuthorRequest newAuthor = service.createAuthor(authorRequested);
                return ResponseEntity.status(HttpStatus.CREATED).body(newAuthor);
            }
            else
                return ResponseEntity.badRequest().build();

        } catch (Exception e) {
            log.error("Error adding author {}", e.getMessage(), e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("/authors/{idAuthor}")
    public ResponseEntity<AuthorRequest> modifyAllAuthorData(@PathVariable String idAuthor, @RequestBody AuthorRequest authorData) {
        try {
            AuthorRequest tempAuthor = service.getAuthorById(idAuthor);
            //Si el autor del id que recibimos es nulo, no hacemos la modificacion
            if(tempAuthor!=null){
                    return ResponseEntity.ok(service.modifyAllAuthorData(tempAuthor, authorData));
            } else
                return ResponseEntity.notFound().build();

        } catch (Exception e) {
            log.error("Error modifying author {}", e.getMessage(), e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @PatchMapping("/authors/{idAuthor}")
    public ResponseEntity<AuthorRequest> modifyAuthorData(@PathVariable String idAuthor, @RequestBody String authorData) {
        try {
            JsonMergePatch jsonMergePatch = JsonMergePatch.fromJson(objectMapper.readTree(authorData));
            JsonNode target = jsonMergePatch.apply(objectMapper.readTree(objectMapper.writeValueAsString(authorData)));
            AuthorRequest authorPatched = objectMapper.treeToValue(target, AuthorRequest.class);

            AuthorRequest tempAuthor = service.getAuthorById(idAuthor);

            if(tempAuthor!=null){
                return ResponseEntity.ok(service.modifyAuthorData(tempAuthor, authorPatched));
            } else
                return ResponseEntity.notFound().build();

        } catch (Exception e) {
            log.error("Error modifying author {}", e.getMessage(), e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/authors/{idAuthor}")
    public ResponseEntity<AuthorRequest> deleteAuthor(@PathVariable String idAuthor) {
        try {
            AuthorRequest prev = service.getAuthorById(idAuthor);
            if(prev!=null){
                return ResponseEntity.ok(service.deleteAuthor(prev));
            } else
                return ResponseEntity.notFound().build();

        } catch (Exception e) {
            log.error("Error deleting author {}", e.getMessage(), e);
            return ResponseEntity.internalServerError().build();
        }
    }
}
