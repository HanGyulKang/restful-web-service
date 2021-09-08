package com.inflearn.restfulwebservice.user;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class UserController {
    private UserDaoService service;

    // 의존성 주입(생성자 이용)
    public UserController(UserDaoService service) {
        this.service = service;
    }

    @GetMapping(path = "/users")
    public List<User> retrieveAllUsers() {
        return service.findAll();
    }

    // Get /users/1 or /users/10 -> 1, 10번과 같은 경우 server로 전달할 때 String으로 전달
    @GetMapping(path = "/users/{id}")
    public User retrieveUser(@PathVariable int id) {
        return service.findOne(id);
    }

    // 상단의 전체 유저 조회 메소드와 path가 같지만 각각 Get, Post 방식으로 서로 다르기때문에 둘은 엄연히 다른 메소드이다.
    @PostMapping(path = "/users")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User savedUser = service.save(user);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(savedUser.getId())
                        .toUri();

        // 저장 후 return시 200 OK가 아닌 201 Created로 Status 결과를 반환한다. 명확한 결과를 반환하는 것이 좋은 Rest API 설계이다.
        // 성공 코드 값(Status 결과)을 입력, 수정, 삭제 모두 200 OK로 반환하는 것은 좋지 않은 Rest API 설계이다.
        return ResponseEntity.created(location).build();
    }
}
