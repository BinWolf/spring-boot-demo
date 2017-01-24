package com.wolf.person.dao;

import com.wolf.person.domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by wolf on 17/1/23.
 */
public interface PersonRepository extends JpaRepository<Person, Long> {

}
