package com.wolf.dao;

import com.wolf.domain.Person;
import com.wolf.support.LocalRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by wolf on 17/1/11.
 */
public interface PersonRepository extends LocalRepository<Person, Long> {

    List<Person> findByAddress(String address);

    Person findByNameAndAddress(String name, String address);

    @Query("select p from Person p where p.name = :name and p.address = :address")
    Person withNameAndAddressQuery(@Param("name") String name, @Param("address") String address);

    List<Person> withNameAndAddressNamedQuery(String name, String address);
}
