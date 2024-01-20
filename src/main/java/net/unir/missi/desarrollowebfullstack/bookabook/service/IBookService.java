package net.unir.missi.desarrollowebfullstack.bookabook.service;

import java.util.List;

import net.unir.missi.desarrollowebfullstack.bookabook.data.model.api.BookResponse;
import net.unir.missi.desarrollowebfullstack.bookabook.data.model.sql.Book;
import net.unir.missi.desarrollowebfullstack.bookabook.data.model.api.BookRequest;


public interface IBookService {

    List<BookResponse> getBooks(String isbn, String name, String language, String description,
                        String category, Long authorId);

    BookResponse getBook(String bookId);

    Boolean removeBook(String bookId);

    BookResponse createBook(BookRequest request);

    BookResponse updateBook(String bookId, String updateRequest);

    BookResponse updateBook(String bookId, BookRequest updateRequest);
}

