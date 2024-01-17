package net.unir.missi.desarrollowebfullstack.bookabook.service;

import net.unir.missi.desarrollowebfullstack.bookabook.data.model.api.AuthorRequest;

import java.util.List;

public interface IAuthorService {

    List<AuthorRequest> getAllAuthors();

    AuthorRequest createAuthor(AuthorRequest author) throws IllegalArgumentException;

    AuthorRequest getAuthorById(String idAuthor);

    AuthorRequest modifyAllAuthorData(AuthorRequest prev, AuthorRequest authorData);

    AuthorRequest modifyAuthorData(AuthorRequest prev, AuthorRequest authorData);

    AuthorRequest deleteAuthor(AuthorRequest prev);


}
