package com.wolf.ui.controller;

import com.wolf.ui.domain.Person;
import com.wolf.ui.service.PersonHystrixService;
import com.wolf.ui.service.SomeHystrixService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by wolf on 17/1/23.
 */

@RestController
public class UiController {
    @Autowired
    private SomeHystrixService someHystrixService;

    @Autowired
    private PersonHystrixService personHystrixService;

    @RequestMapping("/dispatch")
    public List<Person> sendMessage(@RequestBody String personName) {
        return personHystrixService.save(personName);
    }

    @RequestMapping(value = "/getSome", produces = {MediaType.TEXT_PLAIN_VALUE})
    public String getSome() {
        return someHystrixService.getSome();
    }
}
