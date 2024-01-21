package net.unir.missi.desarrollowebfullstack.bookabook.data.repository;

import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.unir.missi.desarrollowebfullstack.bookabook.data.model.sql.Author;
import net.unir.missi.desarrollowebfullstack.bookabook.data.model.sql.Book;
import net.unir.missi.desarrollowebfullstack.bookabook.data.utils.SearchCriteria;
import net.unir.missi.desarrollowebfullstack.bookabook.data.utils.SearchOperation;
import net.unir.missi.desarrollowebfullstack.bookabook.data.utils.SearchStatement;
import org.springframework.stereotype.Repository;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
@Slf4j
public class AuthorRepository {

    private final AuthorJpaRepository authorJpaRepository;


    public List<Author> findAll() {
        return authorJpaRepository.findAll();
    }
    public Author getById(Long id) {
        return authorJpaRepository.findById(id).orElse(null);
    }

    public Author save(Author author) {
        return authorJpaRepository.save(author);
    }

    public void delete(Author author) {
        authorJpaRepository.delete(author);
    }

    public List<Author> search(String firstName, String lastName, LocalDate birthDate, String nationality, String email, String webSite, String biography, Book booksWritted) {
        SearchCriteria<Author> spec = new SearchCriteria<>();

        if(firstName!=null)
            if (StringUtils.isNotBlank(firstName)) {
                spec.add(new SearchStatement("firstName", firstName, SearchOperation.EQUAL));
            }
        if(lastName!=null)
            if (StringUtils.isNotBlank(lastName)) {
                spec.add(new SearchStatement("lastName", lastName, SearchOperation.EQUAL));
            }
        if(birthDate!=null)
            if (StringUtils.isNotBlank(String.valueOf(birthDate))) {
                spec.add(new SearchStatement("birthDate", birthDate, SearchOperation.MATCH));
            }
        if(nationality!=null)
            if (StringUtils.isNotBlank(nationality)) {
                spec.add(new SearchStatement("nationality", nationality, SearchOperation.EQUAL));
            }
        if(email!=null)
            if (StringUtils.isNotBlank(email)) {
                spec.add(new SearchStatement("email", email, SearchOperation.EQUAL));
            }
        if(webSite!=null)
            if (StringUtils.isNotBlank(webSite)) {
                spec.add(new SearchStatement("webSite", webSite, SearchOperation.MATCH));
            }
        if(biography!=null)
            if (StringUtils.isNotBlank(biography)) {
                spec.add(new SearchStatement("biography", biography, SearchOperation.MATCH));
            }
//        if(booksWritted!=null)
//            {
//                spec.add(new SearchStatement("booksWritted", booksWritted, SearchOperation.EQUAL));
//            }


        List<Author> listAuthor = authorJpaRepository.findAll(spec);
        List<Author> filteredAuthors = new ArrayList<>();

        if (booksWritted != null) {
            filteredAuthors = listAuthor.stream()
                    .filter(author -> author.getBooksWritted().stream()
                            .anyMatch(book -> Objects.equals(booksWritted.getId(), book.getId())))
                    .collect(Collectors.toList());
        }else{
            filteredAuthors = listAuthor;
        }

        return filteredAuthors;
    }



}
