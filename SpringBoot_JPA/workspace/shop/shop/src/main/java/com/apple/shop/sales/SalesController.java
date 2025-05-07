package com.apple.shop.sales;

import com.apple.shop.item.ItemRepository;
import com.apple.shop.member.CustomUser;
import com.apple.shop.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class SalesController {
    private final SalesRepository salesRepository;
    private final ItemRepository itemRepository;

    @PostMapping("/order")
    String postOrder(@RequestParam String title,
                     @RequestParam Integer price,
                     @RequestParam Integer count,
                     Authentication auth) {
        //재고빼기
        var result = itemRepository.findById(id);
        if (result.isPresent()){
            var item = result.get();
            item.setCount(item.getCount() - 1);
            itemRepository.save(item);
        }

        Sales sales = new Sales();
        sales.setPrice(price);
        sales.setCount(count);
        sales.setItemName(title);
        CustomUser user = (CustomUser) auth.getPrincipal();

        //member컬럼에 데이터추가하려면
        var member = new Member();
        member.setId(user.id);
        sales.setMember(member);
        salesRepository.save(sales);

        return "list.html";
    }

    @GetMapping("/order/all")
    String getOrderAll(){
        List<Sales> result = salesRepository.findAll();
        System.out.println(result.get(0));
        return "list.html";
    }
}

class SalesDto {
    public String itemName;
    public Integer price;
    public String username;
}
