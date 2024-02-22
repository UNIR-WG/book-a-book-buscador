package net.unir.missi.desarrollowebfullstack.bookabook.service;

import net.unir.missi.desarrollowebfullstack.bookabook.DTO.memory.Author;

import java.time.LocalDate;
import java.util.List;

public interface IAuthorService {

    List<Author> getAllAuthors(String firstName, String lastName, LocalDate birthDate, String nationality, String email, String webSite, String biography, Long booksWritten) throws RuntimeException;

    Author createAuthor(Author author) throws RuntimeException;

    Author getAuthorById(String idAuthor) throws RuntimeException;

    Author modifyAllAuthorData(Author prev, Author authorData) throws RuntimeException;

    Author modifyAuthorData(Author prev, Author authorData) throws RuntimeException;

    Author deleteAuthor(Author prev) throws RuntimeException;
}
