package com.inflearn.restfulwebservice.user;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/* business logic은 Service 클래스에서 처리한다. */
// Annotation을 이용하여 클래스의 역할을 지정
@Service
public class UserDaoService {
    private static List<User> users = new ArrayList<>();

    private static int userCount = 3;

    static {
        users.add(new User(1, "Kenneth", new Date()));
        users.add(new User(2, "Alice", new Date()));
        users.add(new User(3, "Elena", new Date()));
    }

    public List<User> findAll() {
        return users;
    }

    public User save(User user) {
        if(user.getId() == null) {
            user.setId(++userCount);
        }

        users.add(user);
        return user;
    }

    public User findOne(int id) {
        for(User user : users) {
            if(user.getId() == id) {
                return user;
            }
        }

        return null;
    }

    public User deleteById(int id) {
        // Iterator : 열거형 데이터 / 배열 또는 List에 순차적으로 접근하기 위함
        Iterator<User> iterator = users.iterator();

        while(iterator.hasNext()) {
            User user = iterator.next();

            if(user.getId() == id) {
                iterator.remove();
                return user;
            }
        }

        return null;
    }
}
