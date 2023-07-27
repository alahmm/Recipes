package com.example.demo.Buisnesslayout;


import com.example.demo.persistence.userRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class userService {
    private final userRepository userRepository;

    @Autowired
    public userService(userRepository userRepository) {
        this.userRepository = userRepository;
    }

    public MyUser findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    public void save(MyUser toSave) {
        userRepository.save(toSave);
    }
    public long getHowManyElementsUsers() {

        return userRepository.count() + 1;
    }
    public List<MyUser> getUsers() {
        return userRepository.findAll();
    }
}
