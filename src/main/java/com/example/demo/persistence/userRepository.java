package com.example.demo.persistence;

import com.example.demo.Buisnesslayout.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface userRepository extends JpaRepository<MyUser, Long> {
    MyUser findUserByEmail(String email);

}
