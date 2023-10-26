package jpabook.jpashop.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Order.
 */
@Entity
@Table(name="orders")
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {
    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "order" , cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY , cascade = CascadeType.ALL)
    // 원래 JPA는 persist를 일일이 다 해줘야 하지만 cascade는 persist를 전파하여 일일이 하지 않아도 된다
    @JoinColumn(name = "delivery_id") // 연관관계의 주인을 의미
    private Delivery delivery;

    private LocalDateTime orderDate; //주문시간

    @Enumerated(EnumType.STRING)
    private OrderStatus status; //주문상태 [ORDER, CANCEL]

    /**
     * Set member.
     *
     * @param member the member
     */
//==연관관계 메서드==//
    //양방향 설정이 될 수 있도록
    public void setMember(Member member){
        this.member = member;
        member.getOrders().add(this);
    }

    /**
     * Add order item.
     *
     * @param orderItem the order item
     */
    public void addOrderItem(OrderItem orderItem){
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    /**
     * Set delivery.
     *
     * @param delivery the delivery
     */
    public void setDelivery(Delivery delivery){
        this.delivery = delivery;
        delivery.setOrder(this);
    }

    /**
     * Create order order.
     *
     * @param member     the member
     * @param delivery   the delivery
     * @param orderItems the order items
     * @return the order
     */
//==생성 메서드==//
    public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems){
        Order order = new Order();
        order.setMember(member);
        order.setDelivery(delivery);
        for(OrderItem orderItem : orderItems){
            order.addOrderItem(orderItem);
        }
        order.setStatus(OrderStatus.ORDER);
        order.setOrderDate(LocalDateTime.now());
        return order;
    }

    /**
     * Cancel.
     */
//==비즈니스 로직 ==//
    /*
    * 주문 취소
    */
    public void cancel(){
        if(delivery.getStatus() == DeliveryStatus.COMP) {
            throw new IllegalStateException("이미 배송 완료된 상품은 취소가 불가능합니다.");
        }
        this.setStatus(OrderStatus.CANCEL);
        orderItems.forEach(OrderItem::cancel);
    }

    /**
     * Get total price int.
     *
     * @return the int
     */
//==조회 로직 ==//
    public int getTotalPrice(){
        int totalPrice = orderItems.stream()
                .mapToInt(OrderItem::getTotalPrice)
                .sum();

        return totalPrice;
    }
}
