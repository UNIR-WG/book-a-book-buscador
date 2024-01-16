package net.unir.missi.desarrollowebfullstack.bookabook.service;

import java.util.List;

import net.unir.missi.desarrollowebfullstack.bookabook.data.model.sql.BookModel;
import net.unir.missi.desarrollowebfullstack.bookabook.data.model.api.BookDto;
import net.unir.missi.desarrollowebfullstack.bookabook.data.model.api.CreateBookRequest;


public interface BookService {

    List<BookModel> getBooks(String isbn, String name, String language, String description,
                             String category, Long authorId);

    BookModel getBook(String bookId);

    Boolean removeBook(String bookId);

    BookModel createBook(CreateBookRequest request);

    BookModel updateBook(String bookId, String updateRequest);

    BookModel updateBook(String bookId, BookDto updateRequest);
}

