package net.unir.missi.desarrollowebfullstack.bookabook.service;

import net.unir.missi.desarrollowebfullstack.bookabook.data.model.api.AuthorRequest;

import java.util.List;

public interface IAuthorService {

    List<AuthorRequest> getAllAuthors() throws RuntimeException;

    AuthorRequest createAuthor(AuthorRequest author) throws RuntimeException;

    AuthorRequest getAuthorById(String idAuthor) throws RuntimeException;

    AuthorRequest modifyAllAuthorData(AuthorRequest prev, AuthorRequest authorData) throws RuntimeException;

    AuthorRequest modifyAuthorData(AuthorRequest prev, AuthorRequest authorData) throws RuntimeException;

    AuthorRequest deleteAuthor(AuthorRequest prev) throws RuntimeException;


}
