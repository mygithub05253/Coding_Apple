package com.apple.shop.sales;

import com.apple.shop.item.Item;
import com.apple.shop.item.ItemRepository;
import com.apple.shop.member.CustomUser;
import com.apple.shop.member.Member;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SalesService {
    private final SalesRepository salesRepository;
    private final ItemRepository itemRepository;

    @Transactional
    public void addSales(Long id, String title, Integer price, Integer count, Authentication auth) {

        //1. 재고빼기
        Optional<Item> result = itemRepository.findById(id);
        if (result.isPresent()){
            var item = result.get();
            item.setCount(item.getCount() - count);
            itemRepository.save(item);
        }

        //2. sales테이블에 행추가
        Sales sales = new Sales();
        sales.setCount(count);
        sales.setPrice(price);
        sales.setItemName(title);
        CustomUser user = (CustomUser) auth.getPrincipal();
        var member = new Member();
        member.setId(user.id);
        sales.setMember(member);
        salesRepository.save(sales);
    }
}
