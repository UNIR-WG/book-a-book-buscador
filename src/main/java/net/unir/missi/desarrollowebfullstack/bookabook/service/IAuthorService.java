package net.unir.missi.desarrollowebfullstack.bookabook.service;

import net.unir.missi.desarrollowebfullstack.bookabook.data.model.api.Author;
import net.unir.missi.desarrollowebfullstack.bookabook.data.model.sql.AuthorModel;

import java.util.List;

public interface IAuthorService {

    List<Author> getAllAuthors();

    Author createAuthor(Author author) throws IllegalArgumentException;

    Author getAuthorById(String idAuthor);

    Author modifyAuthorData(Author prev, Author authorData);


    Author deleteAuthor(Author prev);


}
