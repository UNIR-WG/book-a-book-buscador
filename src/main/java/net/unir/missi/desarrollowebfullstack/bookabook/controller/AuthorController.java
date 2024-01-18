package net.unir.missi.desarrollowebfullstack.bookabook.controller;

import org.springframework.http.HttpStatus;
import net.unir.missi.desarrollowebfullstack.bookabook.data.model.api.AuthorRequest;
import net.unir.missi.desarrollowebfullstack.bookabook.service.AuthorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
    public ResponseEntity<List<AuthorRequest>> getAllAuthors()
    {
        try {
            return ResponseEntity.ok(service.getAllAuthors());
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
            if(authorRequested!=null && authorRequested.isValid()) {
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
    public ResponseEntity<AuthorRequest> modifyAuthorData(@PathVariable String idAuthor, @RequestBody AuthorRequest authorData) {
        try {
            AuthorRequest tempAuthor = service.getAuthorById(idAuthor);
            if(tempAuthor!=null){
                return ResponseEntity.ok(service.modifyAuthorData(tempAuthor, authorData));
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
