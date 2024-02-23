package net.unir.missi.desarrollowebfullstack.bookabook.repository;

import lombok.RequiredArgsConstructor;
import net.unir.missi.desarrollowebfullstack.bookabook.model.AuthorDocument;
import net.unir.missi.desarrollowebfullstack.bookabook.model.BookDocument;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Supplier;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class BookRepository {

    private static Long numBooks = 3L;
    private final BookElasticRepository repository;

    public List<BookDocument> getBooks() {
        List<BookDocument> ret = new LinkedList<>();
        repository.findAll().forEach(ret::add);

        Logger.getGlobal().warning("list content:" + ret.toString());
        Logger.getGlobal().warning("ffins all repo" + repository.findAll().toString());
        //Logger.getGlobal().warning((Supplier<String>) repository.findAll());
        return ret;
    }

    public BookDocument getById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public BookDocument save(BookDocument book) {
        if (book.getId() == null)
        {
            book.setId(numBooks);
            numBooks++;
        }
        BookDocument r = repository.save(book);
        return r;
    }

    public void delete(BookDocument book) {
        repository.delete(book);
    }

    public List<BookDocument> search(String isbn, String name, String language,
                                     String description, String category, AuthorDocument authorDocument) {

        List<BookDocument> listBookDocument = new LinkedList<>();
        repository.findAll().forEach(listBookDocument::add);

        return listBookDocument.stream()
                .filter((BookDocument doc) ->
                {
                    if (!doc.getIsbn().equals(isbn)) {
                        return false;
                    }
                    if (!doc.getName().equals(name)) {
                        return false;
                    }
                    if (!doc.getLanguage().equals(language)) {
                        return false;
                    }
                    if (!doc.getDescription().equals(description)) {
                        return false;
                    }
                    if (!doc.getCategory().equals(category)) {
                        return false;
                    }
                    // TODO filter by authorDocument
                    return true;
                })
                .collect(Collectors.toList());
    }

}