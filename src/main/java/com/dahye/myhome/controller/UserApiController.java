package com.dahye.myhome.controller;
import java.util.List;

import com.dahye.myhome.model.Board;
import com.dahye.myhome.model.User;
import com.dahye.myhome.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

@RestController
@RequestMapping("/api")
@Slf4j
class UserApiController { // ctrl+R 모두 바꾸기
    //final
    @Autowired
    private UserRepository repository;
/*

        UserApiController(UserRepository repository) {
            this.repository = repository;
        }
*/


    // Aggregate root
    // tag::get-aggregate-root[]
    @GetMapping("/users")
    List<User> all() {
            List<User> users = repository.findAll();
            log.debug(" users.get(0).getBoards().size() 호출 전");
            log.debug(" users.get(0).getBoards().size() : {}",users.get(0).getBoards().size());// LAZY이기에 이 때 board 호출
            log.debug(" users.get(0).getBoards().size() 호출 후");
            return users;
    }
    // end::get-aggregate-root[]

    @PostMapping("/users")
    User newUser(@RequestBody User newUser) {
        return repository.save(newUser);
    }

    // Single item

    @GetMapping("/users/{id}")
    User one(@PathVariable Long id) {

        return repository.findById(id).orElse(null);
        //.orElseThrow(() -> new UserNotFoundException(id));
    }

    @PutMapping("/users/{id}")
    User replaceUser(@RequestBody User newUser, @PathVariable Long id) {

        return repository.findById(id)
                .map(user -> {
                    //user.setTitle(newUser.getTitle());
                    //user.setContent(newUser.getContent())

                    // user.setBoards(newUser.getBoards());

                    user.getBoards().clear();
                    user.getBoards().addAll(newUser.getBoards());
                    for(Board board : user.getBoards()){
                        board.setUser((user));
                    }
                    return repository.save(user);
                }) //ctrl+alt+b 구현체를 찾아갈 수 있음
                .orElseGet(() -> {
                    newUser.setId(id);
                    return repository.save(newUser);
                });
    }

    @DeleteMapping("/users/{id}")
    void deleteUser(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
