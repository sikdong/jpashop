package jpabook.jpashop.service;

import jpabook.jpashop.domain.Item;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional
    public void saveItem(Item item){
        itemRepository.save(item);
    }

    @Transactional
    public void updateItem(Long itemId, Book bookParam){
        Item fidnItem = itemRepository.findOne(itemId); // 영속 상태 즉 JPA가 관리함,
                                // /준영속은 JPA가 관리하지 않아 SET으로 해도 UPDATE 쿼리 안 날림
        fidnItem.setPrice(bookParam.getPrice());
        fidnItem.setName(bookParam.getName());
        fidnItem.setStockQuantity(bookParam.getStockQuantity());
    }

    public List<Item> findItems(){
       return itemRepository.findAll();
    }

    public Item findOne(Long itemId){
        return itemRepository.findOne(itemId);
    }
}
