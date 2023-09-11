package com.inflearn.restfulwebservice.user.controller;

import com.inflearn.restfulwebservice.user.controller.dto.UserDto;
import com.inflearn.restfulwebservice.user.service.UserService;
import com.inflearn.restfulwebservice.user.service.dto.UserCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@RequestMapping("/apis/v1")
@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @PostMapping("/user")
    public ResponseEntity<Object> registeredUser(@Valid @RequestBody UserDto.UserRequest request) {
        userService.registeredUser(new UserCommand(request));

        return ResponseEntity.ok().build();
    }

}
