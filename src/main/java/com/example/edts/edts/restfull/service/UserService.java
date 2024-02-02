package com.example.edts.edts.restfull.service;

import com.example.edts.edts.restfull.dto.RegisterUserRequest;
import com.example.edts.edts.restfull.model.User;
import com.example.edts.edts.restfull.repository.UserRepository;
import com.example.edts.edts.restfull.security.BCrypt;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;



@Service
@Slf4j
public class UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    ValidationService validationService;
    @Transactional
    public void register(RegisterUserRequest request) {
        validationService.validate(request);
        User userEmail = userRepository.findByEmail(request.getEmail());
        if (userEmail != null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already registered");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(BCrypt.hashpw(request.getPassword(), BCrypt.gensalt()));
        user.setEmail(request.getEmail());

        userRepository.save(user);

    }
}
