package net.unir.missi.desarrollowebfullstack.bookabook.controller;

import org.springframework.http.HttpStatus;
import net.unir.missi.desarrollowebfullstack.bookabook.data.model.api.Author;
import net.unir.missi.desarrollowebfullstack.bookabook.service.AuthorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;



import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;


@RestController
@RequiredArgsConstructor
@Slf4j
public class AuthorController {

    private final AuthorService service;

    @GetMapping("/authors")
    public ResponseEntity<List<Author>> getAllAuthors()
    {
        try {
            return ResponseEntity.ok(service.getAllAuthors());
        } catch (Exception e) {
            log.error("Error retrieving authors {}", e.getMessage(), e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/authors/{idAuthor}")
    public ResponseEntity<Object> getAuthorById(@PathVariable String idAuthor)
    {
        try {
            if(service.getAuthorById(idAuthor).isPresent()) {
                return ResponseEntity.ok(service.getAuthorById(idAuthor));
            } else
                return ResponseEntity.notFound().build();


        } catch (Exception e) {
            log.error("Error modifying author {}", e.getMessage(), e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/authors")
    public ResponseEntity<Author> addAuthor(@RequestBody Author authorRequested)
    {
        try {
            Author newAuthor = service.createAuthor(authorRequested);
            if(newAuthor==null)
                return ResponseEntity.badRequest().build();
            else
                return ResponseEntity.status(HttpStatus.CREATED).body(newAuthor);


        } catch (Exception e) {
            log.error("Error retrieving author {}", e.getMessage(), e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @PatchMapping("/authors/{idAuthor}")
    public ResponseEntity<Author> modifyAuthorData(@PathVariable String idAuthor, @RequestBody Author authorData) {
        try {
            Author prev;
            if(service.getAuthorById(idAuthor).isPresent()) {
                prev = service.getAuthorById(idAuthor).get();
                return ResponseEntity.ok(service.modifyAuthorData(prev, authorData));
            } else
                return ResponseEntity.notFound().build();


        } catch (Exception e) {
            log.error("Error modifying author {}", e.getMessage(), e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/authors/{idAuthor}")
    public ResponseEntity<Author> deleteAuthor(@PathVariable String idAuthor) {
        try {
            Author prev;
            if(service.getAuthorById(idAuthor).isPresent()) {
                prev = service.getAuthorById(idAuthor).get();
                return ResponseEntity.ok(service.deleteAuthor(prev));
            } else
                return ResponseEntity.notFound().build();


        } catch (Exception e) {
            log.error("Error deleting author {}", e.getMessage(), e);
            return ResponseEntity.internalServerError().build();
        }
    }
}
