// 특정 클래스를 다른 파일에서 사용하고 싶으면 해당 파일의 경로 적어두기
// 보통 자동 생성 됨
package com.apple.shop.item;
// 상품과 관련된 API를 저장하는 곳
// 비슷한 API는 한 곳에 모아두는 것이 효율적

import com.apple.shop.comment.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

// public이 없는 경우 같은 패키지 안에서만 사용 가능
// 보통은 public을 기본적으로 사용
@Controller
//@RequiredArgsConstructor
public class ItemController {

    private final ItemRepository itemRepository;
    private final ItemService itemService;
    private final S3Service s3Service;
    private final CommentRepository commentRepository;

    @Autowired
    public ItemController(ItemRepository itemRepository, ItemService itemService, S3Service s3Service, CommentRepository commentRepository) {
        this.itemRepository = itemRepository;
        this.itemService = itemService;
        this.s3Service = s3Service;
        this.commentRepository = commentRepository;
    }

    @GetMapping("/list")
    String list(Model model) {

        List<Item> result = itemRepository.findAll();
        model.addAttribute("items", result);

        return "list.html";
    }

    @GetMapping("/write")
    String write() {
        return "write.html";
    }

    @PostMapping("/add")
    String writePost(String title, Integer price) {
        itemService.saveItem(title, price);
        return "redirect:/list";
    }

    @GetMapping("/detail/{id}")
    String detail(@PathVariable Long id, Model model) {

        commentRepository.findAllByParentId(1L);

        Optional<Item> result = itemRepository.findById(id);
        if (result.isPresent()){
            model.addAttribute("data", result.get());
            return "detail.html";
        } else {
            return "redirect:/list";
        }
    }

    @GetMapping("/edit/{id}")
    String edit(@PathVariable Long id, Model model) {
        Optional<Item> result = itemRepository.findById(id);
        if (result.isPresent()) {
            model.addAttribute("data", result.get());
            return "edit.html";
        } else {
            return "redirect:/list";
        }
    }

    @PostMapping("/edit")
    String editItem(String title, Integer price, Long id) {
        itemService.editItem(title, price, id);
        return "redirect:/list";
    }

    @GetMapping("/test1")
    String test1(@RequestParam String name) {
        System.out.println(name);
        return "redirect:/list";
    }

    @DeleteMapping("/item")
    ResponseEntity<String> deleteItem(@RequestParam Long id) {
        itemRepository.deleteById(id);
        return ResponseEntity.status(200).body("삭제완료");
    }

    @GetMapping("/presigned-url")
    @ResponseBody
    String getURL(@RequestParam String filename){
        var result = s3Service.createPresignedUrl("test/" + filename);
        return result;
    }

    @PostMapping("/search")
    String postSearch(@RequestParam String searchText) {
        // Item테이블에서 searchText가 들어있는거 찾아서 가져오기
        var result = itemRepository.fullTextSearch(searchText);
        System.out.println(result);

        return "list.html";
    }
}
