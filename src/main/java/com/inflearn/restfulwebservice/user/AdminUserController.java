package com.inflearn.restfulwebservice.user;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

// GET    : 조회
// POST   : 입력
// PUT    : 수정
// DELETE : 삭제

@RestController
@RequestMapping("/admin")
public class AdminUserController {
    private UserDaoService service;

    // 의존성 주입(생성자 이용)
    public AdminUserController(UserDaoService service) {
        this.service = service;
    }

    @GetMapping(path = "/users")
    public MappingJacksonValue retrieveAllUsers() {
        List<User> users = service.findAll();

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter
                .filterOutAllExcept("id", "name", "joinDate", "password");

        FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfo", filter);

        MappingJacksonValue mapping = new MappingJacksonValue(users);
        mapping.setFilters(filters);

        return mapping;
    }

    // Get /users/1 or /users/10 -> 1, 10번과 같은 경우 server로 전달할 때 String으로 전달
    @GetMapping(path = "/users/{id}")
    public MappingJacksonValue retrieveUser(@PathVariable int id) {
        User user = service.findOne(id);

        // 조회된 결과가 없을 경우(null 값을 return 받았을 경우 예외처리...
        if(user == null) {
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }

        // User Class의 JsonFilter를 활용한 방법 - S
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter
                .filterOutAllExcept("id", "name", "password", "ssn");

        FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfo", filter);

        MappingJacksonValue mapping = new MappingJacksonValue(user);
        mapping.setFilters(filters);
        // User Class의 JsonFilter를 활용한 방법 - E

        return mapping;
    }
}
