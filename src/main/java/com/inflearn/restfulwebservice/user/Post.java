package com.inflearn.restfulwebservice.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Post {
    @Id
    @GeneratedValue
    private Integer id;

    private String description;

    // User : Post -> 1 : (0~N) , Main : Sub -> Parent table : Child table
    // Post 데이터는 여러개가 올 수 있으나 User는 하나만 올 수 있음
    @ManyToOne(fetch = FetchType.LAZY) // Lazy : 지연 로딩 방식 (Post데이터가 로딩되는 시점에 필요한 사용자 데이터 가져 옴)
    @JsonIgnore // 외부 공개 제외
    private User user;
}
