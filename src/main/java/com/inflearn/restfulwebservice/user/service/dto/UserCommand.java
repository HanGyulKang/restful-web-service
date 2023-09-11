package com.inflearn.restfulwebservice.user.service.dto;

import com.inflearn.restfulwebservice.user.controller.dto.UserDto;

public class UserCommand {
    private UserDto.UserRequest request;

    public UserCommand(UserDto.UserRequest request) {
        this.request = request;
    }
}
