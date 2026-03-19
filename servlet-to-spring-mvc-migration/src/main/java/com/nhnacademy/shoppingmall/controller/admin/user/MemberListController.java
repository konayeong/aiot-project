package com.nhnacademy.shoppingmall.controller.admin.user;

import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.user.domain.User;
import com.nhnacademy.shoppingmall.user.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MemberListController {
    private final UserService userService;

    public MemberListController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/admin/memberList.do")
    public String execute(@RequestParam(name = "searchUser", required = false) String userId,
                          @RequestParam(name = "page", defaultValue = "1") int page,
                          Model model) {

        int pageSize = 10;

        Page<User> users = userService.getUsers(page, pageSize, userId);
        long totalCount = users.getTotalCount();
        int totalPages = (int) Math.ceil((double) totalCount/pageSize);

        model.addAttribute("users", users);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("searchUser", userId);


        return "shop/admin/user_list";
    }
}
