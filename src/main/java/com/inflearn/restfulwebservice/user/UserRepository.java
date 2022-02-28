package com.inflearn.restfulwebservice.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository // Database에 관련된 bean
public interface UserRepository extends JpaRepository<User, Integer> {
}
