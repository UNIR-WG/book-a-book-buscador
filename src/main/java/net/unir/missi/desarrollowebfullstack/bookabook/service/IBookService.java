package net.unir.missi.desarrollowebfullstack.bookabook.service;

import java.util.List;

import net.unir.missi.desarrollowebfullstack.bookabook.data.model.sql.Book;
import net.unir.missi.desarrollowebfullstack.bookabook.data.model.api.BookRequest;


public interface IBookService {

    List<Book> getBooks(String isbn, String name, String language, String description,
                        String category, Long authorId);

    Book getBook(String bookId);

    Boolean removeBook(String bookId);

    Book createBook(BookRequest request);

    Book updateBook(String bookId, String updateRequest);

    Book updateBook(String bookId, BookRequest updateRequest);
}

