package com.dahye.myhome.repository;

import com.dahye.myhome.model.Board;
import com.dahye.myhome.model.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository  extends JpaRepository<User, Long> {
    @EntityGraph(attributePaths = {"boards"}) //User.java에 있는 변수 명
    List<User> findAll(); //fetch 무시되고 join으로 한번에 읽어들임
    User findByUsername(String username);
}

