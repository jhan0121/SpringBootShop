package com.flinter.shop.member;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/register")
    public String register(Authentication auth) {
        if (auth == null) {
            return "register";
        }
        if (auth.isAuthenticated()) return "redirect:/list";
        return "register";
    }

    @PreAuthorize("isAnonymous()")
    @PostMapping("/register")
    public String register(String username, String password, String displayName, Model model) {

        try {
            memberService.register(username, password, displayName);
            return "redirect:/login";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("displayName", displayName);
            model.addAttribute("username", username);
            return "register";
        }
    }

    @PreAuthorize("isAnonymous()")
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/my-page")
    public String myPage(Authentication auth) {
//        System.out.println(auth);
//        System.out.println(auth.getName());
//        System.out.println(auth.isAuthenticated());
//        System.out.println(auth.getAuthorities().contains(
//                new SimpleGrantedAuthority("normal")
//        ));

        CustomUser user = (CustomUser) auth.getPrincipal();
        System.out.println(user.getDisplayName());

        return "mypage";
    }
}
