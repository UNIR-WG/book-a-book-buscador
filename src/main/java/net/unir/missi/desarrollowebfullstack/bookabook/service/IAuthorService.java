package net.unir.missi.desarrollowebfullstack.bookabook.service;

import net.unir.missi.desarrollowebfullstack.bookabook.model.api.AuthorRequest;

import java.time.LocalDate;
import java.util.List;

public interface IAuthorService {

    List<AuthorRequest> getAllAuthors(String firstName, String lastName, LocalDate birthDate, String nationality, String email, String webSite, String biography, Long booksWritted) throws RuntimeException;

    AuthorRequest createAuthor(AuthorRequest author) throws RuntimeException;

    AuthorRequest getAuthorById(String idAuthor) throws RuntimeException;

    AuthorRequest modifyAllAuthorData(AuthorRequest prev, AuthorRequest authorData) throws RuntimeException;

    AuthorRequest modifyAuthorData(AuthorRequest prev, AuthorRequest authorData) throws RuntimeException;

    AuthorRequest deleteAuthor(AuthorRequest prev) throws RuntimeException;


}
