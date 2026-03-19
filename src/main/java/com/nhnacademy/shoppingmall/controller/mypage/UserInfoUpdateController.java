package com.nhnacademy.shoppingmall.controller.mypage;

import com.nhnacademy.shoppingmall.user.domain.User;
import com.nhnacademy.shoppingmall.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
public class UserInfoUpdateController {
    private final UserService userService;

    public UserInfoUpdateController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/mypage/infoAction.do")
    public String execute(HttpServletRequest req,
                          @RequestParam(name = "user_name") String userName,
                          @RequestParam(name = "user_password") String userPassword,
                          Model model) {
        HttpSession session = req.getSession(false);
        User user = (User) session.getAttribute("loginUser");

        try {
            user.setUserName(userName);
            user.setUserPassword(userPassword);

            userService.updateUser(user);
            session.setAttribute("loginUser", user);

            return "redirect:/mypage/info.do";
        } catch (Exception e) {
            log.error("User info update failed", e);
            model.addAttribute("errorMessage", "정보 수정 중 오류가 발생했습니다.");
            return "shop/mypage/info";
        }
    }
}
