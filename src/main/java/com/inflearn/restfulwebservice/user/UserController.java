package com.inflearn.restfulwebservice.user;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

// GET    : 조회
// POST   : 입력
// PUT    : 수정
// DELETE : 삭제

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
    public EntityModel<User> retrieveUser(@PathVariable int id) {
        User user = service.findOne(id);

        // 조회된 결과가 없을 경우(null 값을 return 받았을 경우 예외처리...)
        if(user == null) {
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }

        // HATEOAS(Spring 2.2 이상의 경우) : 하나의 resource에서 파생되는 여러가지 추가 작업을 확인할 수 있음
        // Spring 2.2 이상
        //EntityModel<User> model = new EntityModel(user);
        // Spring 2.6.* 이상
        EntityModel<User> model = EntityModel.of(user);
        WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).retrieveAllUsers());
        model.add(linkTo.withRel("all-users"));

        // Spring 2.1.8.RELEASE
        /*
        Resource<User> resource = new Resource<>(user);
        ControllerLinkBuilder linkTo = ControllerLinkBuilder.linkTo(
            ControllerLinkBuilder.methodOn(this.getClass()).retrieveAllUsers());
        )
        resource.add(linkTo.withRel("all-users"));

        return resource;
         */

        return model;
    }

    // 상단의 전체 유저 조회 메소드와 path가 같지만 각각 Get, Post 방식으로 서로 다르기때문에 둘은 엄연히 다른 메소드이다.
    @PostMapping(path = "/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        User savedUser = service.save(user);

        // 현재 요청된 Request URI에 path(저장된 User의 ID값)를 붙이고, Uri로 변환한 것을 location 변수에 담는다.
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(savedUser.getId())
                        .toUri();

        // 저장 후 return시 200 OK가 아닌 201 Created로 Status 결과를 반환한다. 명확한 결과를 반환하는 것이 좋은 Rest API 설계이다.
        // 성공 코드 값(Status 결과)을 입력, 수정, 삭제 모두 200 OK로 반환하는 것은 좋지 않은 Rest API 설계이다.
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping(path = "/users/{id}")
    public void deleteUser(@PathVariable int id) {
        User user = service.deleteById(id);

        if(user == null) {
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }
    }

    @PutMapping(path = "/users")
    public ResponseEntity<User> modifyUser(@RequestBody User user) {
        // User 정보 변경(Name)
        User modifiedUser = service.modifyById(user);

        // 수정하고자 하는 User가 없을 시 요청받은 데이터의 id값을 message에 담아서 Exception 발생
        if(modifiedUser == null) {
            throw new UserNotFoundException(String.format("ID[%s] not found", user.getId()));
        }

        // URI에 변경된 name을 붙인 후 return
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                        .path("/{name}")
                        .buildAndExpand(modifiedUser.getName())
                        .toUri();

        return ResponseEntity.created(location).build();
    }
}
