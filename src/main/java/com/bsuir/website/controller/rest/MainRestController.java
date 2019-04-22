package com.bsuir.website.controller.rest;


import com.bsuir.website.constants.JsonViews;
import com.bsuir.website.entity.Advert;
import com.bsuir.website.entity.User;
import com.bsuir.website.repository.AdvertRepository;
import com.bsuir.website.repository.UserRepository;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class MainRestController {

    private final UserRepository userRepository;

    private final AdvertRepository advertRepository;

    @Autowired
    public MainRestController(UserRepository userRepository, AdvertRepository advertRepository) {
        this.userRepository = userRepository;
        this.advertRepository = advertRepository;
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/adverts")
    @JsonView(JsonViews.Public.class)
    public List<Advert> getAllAdverts() {
        return advertRepository.findAll();
    }




}
