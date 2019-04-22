package com.bsuir.website.service;

import com.bsuir.website.entity.User;
import com.bsuir.website.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User loginUserOrReturnNull(String login, String password) {

        return userRepository.findUserByLogin(login);
    }
}
