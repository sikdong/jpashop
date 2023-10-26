package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;


@Repository
@RequiredArgsConstructor
public class ItemRepository {
    private final EntityManager em;

    //상품 저장
    public void save(Item item){
        if(item.getId() == null){
            em.persist(item);
        } else {
            Item merge = em.merge(item);//여기서 파라미터로 넘어온 item은 영속성 컨텍스트가 아니다, merge가 영속성 컨텍스트
        }
        //
        // 주의 : 변경감지 기능을 사용하면 원하는 속성만 선택해서 변경할 수 있지만 병합을 사용하면 모든 속성이 변경된다.
        // 병합시 값이 없으면 null로 업데이트 할 위험도 있다(병합은 모든 필드를 교체한다.)
    }

    public Item findOne(Long id){
        return em.find(Item.class, id);
    }

    public List<Item> findAll(){
        return em.createQuery("select i from Item i", Item.class).getResultList();
    }
}
