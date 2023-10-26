package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryOld {


    private final EntityManager em;

    @PersistenceUnit
    private EntityManagerFactory enf;// 팩토리를 주입 받고 싶으면
    //저장
    public void save(Member member){
        em.persist(member);
    }
    //단건 조회
    public Member findOne(Long id){
        return em.find(Member.class, id);
    }
    //다중 조회
    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class).getResultList();
        //from 의 대상이 테이블이 아니라 엔티티
    }

    public List<Member> findByName(String name){
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }

}
