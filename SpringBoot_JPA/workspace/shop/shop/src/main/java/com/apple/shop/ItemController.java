// 특정 클래스를 다른 파일에서 사용하고 싶으면 해당 파일의 경로 적어두기
// 보통 자동 생성 됨
package com.apple.shop;
// 상품과 관련된 API를 저장하는 곳
// 비슷한 API는 한 곳에 모아두는 것이 효율적

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

// public이 없는 경우 같은 패키지 안에서만 사용 가능
// 보통은 public을 기본적으로 사용
@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemRepository itemRepository;

    @GetMapping("/list")
    String list(Model model) {

        List<Item> result = itemRepository.findAll();
        model.addAttribute("items", result);

        var a = new Item();
        System.out.println(a.toString());

        return "list.html";
    }
}
