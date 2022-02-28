package com.inflearn.restfulwebservice.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
// 모든 method가 prifix를 가질 경우...
@RequestMapping("/jpa")
public class UserJpaController {
    @Autowired
    private UserRepository userRepository;

    // http://localhost:8088/jpa/users
    @GetMapping("/users")
    public List<User> retrieveAllUsers() {
        //UserRepository interface가 상속받은 JpaRepository의 method를 사용.
        return userRepository.findAll();
    }

    @GetMapping("/users/{id}")
    public EntityModel<User> retrieveUser(@PathVariable int id) {
        // data가 존재할 수도 있고 존재하지 않을 수도 있기때문에 optionable data를 return한다.
        Optional<User> user = userRepository.findById(id);

        // data 존재 여부 체크
        if(!user.isPresent()) {
            throw new UserNotFoundException(String.format("ID[%s} not found", id));
        }

        // HATEOAS(Spring 2.2 이상의 경우) : 사용자가 하나의 resource에서 파생되는 여러가지 추가 작업을 확인할 수 있음
        // Spring 2.2 이상
        EntityModel<User> model = new EntityModel<>(user.get());
        WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).retrieveAllUsers());
        model.add(linkTo.withRel("all-users"));

        return model;
    }
}
