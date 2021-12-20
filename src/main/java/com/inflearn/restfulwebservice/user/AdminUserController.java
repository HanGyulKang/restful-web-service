package com.inflearn.restfulwebservice.user;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.beans.BeanUtils;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
//    @GetMapping(path = "/v1/users/{id}") // 1. url을 이용한 버전 관리
//    @GetMapping(value = "/users/{id}", params = "version=1") // 2. parameter를 이용한 버전 관리
//    @GetMapping(value = "/users/{id}", headers = "API-VERSION=1") // 3. header를 이용한 버전 관리
    @GetMapping(value = "/users/{id}", produces = "application/vnd.company.apiV1+xml") // 4. MIME을 이용한 버전 관리
    public MappingJacksonValue retrieveUserV1(@PathVariable int id) {
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

//    @GetMapping(path = "/v2/users/{id}")
//    @GetMapping(value = "/users/{id}", params = "version=2")
//    @GetMapping(value = "/users/{id}", headers = "API-VERSION=2")
    @GetMapping(value = "/users/{id}", produces = "application/vnd.company.apiV2+json")
    public MappingJacksonValue retrieveUserV2(@PathVariable int id) {
        User user = service.findOne(id);

        // 조회된 결과가 없을 경우(null 값을 return 받았을 경우 예외처리...
        if(user == null) {
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }

        UserV2 userV2 = new UserV2();
        BeanUtils.copyProperties(user, userV2);
        userV2.setGrade("VIP");

        // User Class의 JsonFilter를 활용한 방법 - S
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter
                .filterOutAllExcept("id", "name", "joinDate", "grade");

        FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfoV2", filter);

        MappingJacksonValue mapping = new MappingJacksonValue(userV2);
        mapping.setFilters(filters);
        // User Class의 JsonFilter를 활용한 방법 - E

        return mapping;
    }
}
