package net.unir.missi.desarrollowebfullstack.bookabook.service;

import net.unir.missi.desarrollowebfullstack.bookabook.DTO.memory.Book;

import java.util.List;


public interface IBookService {

    List<Book> getBooks(String isbn, String name, String language, String description,
                        String category, Long authorId);

    Book getBook(String bookId);

    Boolean removeBook(String bookId);

    Book createBook(Book request);

    Book updateBookAttributes(String bookId, Book updateRequest);

    Book updateBook(String bookId, Book updateRequest);
}

