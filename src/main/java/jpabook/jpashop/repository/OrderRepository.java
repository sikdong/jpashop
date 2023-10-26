package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * The type Order repository.
 */
@Repository
@RequiredArgsConstructor
public class OrderRepository {

    private final EntityManager em;

    /**
     * Save.
     *
     * @param order the order
     */
    public void save(Order order){
        em.persist(order);
    }

    /**
     * Find one order.
     *
     * @param id the id
     * @return the order
     */
    public Order findOne(Long id){
        return em.find(Order.class, id);

    }

    /**
     * Find all list.
     *
     * @param orderSearch the order search
     * @return the list
     */
    public List<Order> findAll(OrderSearch orderSearch){


        return em.createQuery("select o from Order o join o.member m" +
                " where o.status = :status " +
                " and m.name like :name"
                ,Order.class)
                .setParameter("status", orderSearch.getOrderStatus())
                .setParameter("name" , orderSearch.getMemberName())
                .setMaxResults(1000)
                .getResultList();
    }

    public List<Order> findAllWithMemberDelivery() {
        return em.createQuery(
                "select o from Order o" +
                        " join fetch o.member m"+
                        " join fetch o.delivery d", Order.class
        ).getResultList();
    }

    public List<SimpleOrderQueryDto> findOrderDtos() {
        return em.createQuery(
                "select new jpabook.jpashop.repository.SimpleOrderQueryDto(o.id, m.name, o.orderDate, o.status, d.address)" +
                        "from Order o" +
                        " join o.member m" +
                        " join o.delivery d", SimpleOrderQueryDto.class
        ).getResultList();
    }

    public List<Order> findAllWithItem() {
        return em.createQuery(
                "select distinct o from Order o" +
                        " join fetch o.member m" +
                        " join fetch o.delivery d" +
                        " join fetch o.orderItems oi" +
                        " join fetch oi.item i", Order.class)
                .getResultList();
                //distinct : db에 distinct 를 날림, pk가 같으면 중복을 제거해서 리스트에 담아줌
    }

    /**
     * JPA Criteria
     * 권장하는 방법은 아님
     */
//    public List<Order> findAllByCriteria(OrderSearch orderSearch) {
//
//    }
}
