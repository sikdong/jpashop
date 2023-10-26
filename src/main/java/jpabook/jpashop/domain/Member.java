package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "member") //order 테이블에 있는 member에 의해 매핑
    //연관 관계의 주인은 foreign key가 있는 곳
    //mappedby는 주인이 아님을 표시
    private List<Order> orders = new ArrayList<>();
}
