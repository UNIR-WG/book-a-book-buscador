package net.unir.missi.desarrollowebfullstack.bookabook.data.repository;

import net.unir.missi.desarrollowebfullstack.bookabook.data.model.sql.Client;
import net.unir.missi.desarrollowebfullstack.bookabook.data.utils.SearchCriteria;
import net.unir.missi.desarrollowebfullstack.bookabook.data.utils.SearchOperation;
import net.unir.missi.desarrollowebfullstack.bookabook.data.utils.SearchStatement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

interface ClientJpaRepository extends JpaRepository<Client, Long>, JpaSpecificationExecutor<Client> {

    default List<Client> findByFirstName(String firstName){
        SearchCriteria<Client> spec = new SearchCriteria<>();
        spec.add(new SearchStatement("firstName", firstName, SearchOperation.EQUAL));
        return findAll(spec);
    }

    default List<Client> findByLastName(String lastName){
        SearchCriteria<Client> spec = new SearchCriteria<>();
        spec.add(new SearchStatement("lastName", lastName, SearchOperation.EQUAL));
        return findAll(spec);
    }

    default List<Client> findByAddress(String address){
        SearchCriteria<Client> spec = new SearchCriteria<>();
        spec.add(new SearchStatement("address", address, SearchOperation.EQUAL));
        return findAll(spec);
    }

    default List<Client> findByPhoneNumber(String phoneNumber){
        SearchCriteria<Client> spec = new SearchCriteria<>();
        spec.add(new SearchStatement("phoneNumber", phoneNumber, SearchOperation.EQUAL));
        return findAll(spec);
    };

    default List<Client> findByEmail(String email){
        SearchCriteria<Client> spec = new SearchCriteria<>();
        spec.add(new SearchStatement("email", email, SearchOperation.EQUAL));
        return findAll(spec);
    }

}