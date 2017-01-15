package com.wolf.controller;

import com.wolf.dao.PersonRepository;
import com.wolf.domain.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by wolf on 17/1/11.
 */

@RestController
public class PersonController {

    @Autowired
    PersonRepository personRepository;

    @RequestMapping("/save")
    public Person save(String name, String address, Integer age) {
        Person p = personRepository.save(new Person(name, age, address));
        return p;
    }

    @RequestMapping("/findByAddress")
    public List<Person> findByAddress(String address) {
        List<Person> people = personRepository.findByAddress(address);
        return people;
    }

    @RequestMapping("findByNameAndAddress")
    public Person findByNameAndAddress(String name, String address) {
        Person p = personRepository.findByNameAndAddress(name, address);
        return p;
    }

    @RequestMapping("withNameAndAddressQuery")
    public Person withNameAndAddressQuery(String name, String address) {
        Person p = personRepository.withNameAndAddressQuery(name, address);
        return p;
    }

    @RequestMapping("withNameAndAddressNamedQuery")
    public List<Person> withNameAndAddressNamedQuery(String name, String address) {
        List<Person> people = personRepository.withNameAndAddressNamedQuery(name, address);
        return people;
    }

    @RequestMapping("/findAllAndSort")
    public List<Person> findAllAndSort(){
        List<Person> people = personRepository.findAll(new Sort(Sort.Direction.ASC, "age"));
        return people;
    }

    /**
     * 分页
     * @param page 从0开始
     * @param size
     * @return
     */
    @RequestMapping("/page")
    public Page<Person> page(Integer page, Integer size){
        Page<Person> pagePeople = personRepository.findAll(new PageRequest(page, size));
        System.out.println(pagePeople.getContent().size());
        return pagePeople;
    }

    @RequestMapping("/auto")
    public Page<Person> autoLike(Person person){

        Page<Person> pagePeople = personRepository.findByAuto(person,
                new PageRequest(0, 2, new Sort(Sort.Direction.DESC, "age")));
        System.out.println(pagePeople.getContent().size());
        return pagePeople;
    }
}
