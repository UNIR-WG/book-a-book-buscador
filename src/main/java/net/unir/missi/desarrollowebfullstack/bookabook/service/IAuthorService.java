package net.unir.missi.desarrollowebfullstack.bookabook.service;

import net.unir.missi.desarrollowebfullstack.bookabook.model.api.AuthorDto;

import java.time.LocalDate;
import java.util.List;

public interface IAuthorService {

    List<AuthorDto> getAllAuthors(String firstName, String lastName, LocalDate birthDate, String nationality, String email, String webSite, String biography, Long booksWritted) throws RuntimeException;

    AuthorDto createAuthor(AuthorDto author) throws RuntimeException;

    AuthorDto getAuthorById(String idAuthor) throws RuntimeException;

    AuthorDto modifyAllAuthorData(AuthorDto prev, AuthorDto authorData) throws RuntimeException;

    AuthorDto modifyAuthorData(AuthorDto prev, AuthorDto authorData) throws RuntimeException;

    AuthorDto deleteAuthor(AuthorDto prev) throws RuntimeException;


}
