package com.flinter.shop.item;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/list")
    public String list(Model model) {

        List<Item> result = itemService.findAll();
        model.addAttribute("items", result);
        return "list";
    }

    @GetMapping("/write")
    public String write() {
        return "write";
    }

    @PostMapping("/add")
    public String addPost(@RequestParam(name = "title") String title,
                          @RequestParam(name = "price") Integer price,
                          Model model) {


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        if (title.isEmpty() || title.length() > 255) {
            model.addAttribute("error", "제목을 입력하세요.");
            model.addAttribute("title", title);
            model.addAttribute("price", price);
            return "write";
        }

        if (price < 0) {
            model.addAttribute("error", "가격이 유효하지 않은 값입니다.");
            model.addAttribute("title", title);
            model.addAttribute("price", price);
            return "write";
        }

        itemService.saveItem(title, price, username);
        return "redirect:/list";
    }

    // 파라미터의 개수가 너무 많으면 Map으로 받을 수도 있다.
    @PostMapping("/add2")
    public String addPost2(@RequestParam Map formData) {

        itemService.saveItem(formData.get("title").toString(),
                Integer.parseInt(formData.get("price").toString()),
                formData.get("username").toString());
        return "redirect:/list";
    }

    // ModelAttribute를 사용하면 바로 객체로 받을 수 있다.
    @PostMapping("/add3")
    public String addPost(@ModelAttribute Item item) {
        System.out.println(item);
        return "redirect:/list";
    }

    @GetMapping("/detail/{id}")
    public String detail(@PathVariable Long id, Model model) {

        Optional<Item> result = itemService.findOne(id);

        if (result.isPresent()) {
            model.addAttribute("data", result.get());
            return "detail";
        } else {
            return "redirect:/list";
        }
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {

        Optional<Item> result = itemService.findOne(id);

        if (result.isPresent()) {
            model.addAttribute("data", result.get());
            return "edit";
        } else {
            return "redirect:/list";
        }
    }

    @PostMapping("/edit")
    public String edit(Long id, String title, Integer price, Model model) throws Exception {

        if (title.isEmpty() || title.length() > 255) {
            model.addAttribute("error", "제목이 유효하지 않은 값입니다.(1-254)");
            model.addAttribute("title", title);
            model.addAttribute("price", price);
            return "write";
        }

        if (price < 0) {
            model.addAttribute("error", "가격이 유효하지 않은 값입니다.(>0)");
            model.addAttribute("title", title);
            model.addAttribute("price", price);
            return "write";
        }

        itemService.updateItem(id, title, price);
        return "redirect:/list";
    }

    @DeleteMapping("/item")
    public ResponseEntity<String> deleteItem(@RequestParam Long id) {

        itemService.deleteItem(id);
        return ResponseEntity.status(200).body("삭제완료");
    }

    @GetMapping("/test2")
    public String test2() {
        String aaaaa = new BCryptPasswordEncoder().encode("aaaaa");
        System.out.println("aaaaa = " + aaaaa);
        return "redirect:/list";
    }
}
