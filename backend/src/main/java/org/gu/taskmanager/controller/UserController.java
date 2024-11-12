package org.gu.taskmanager.controller;

import org.gu.taskmanager.entity.User;
import org.gu.taskmanager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/me")
    public User getCurrentUser(Principal principal) {
        return userRepository.findByUsername(principal.getName());
    }
}
