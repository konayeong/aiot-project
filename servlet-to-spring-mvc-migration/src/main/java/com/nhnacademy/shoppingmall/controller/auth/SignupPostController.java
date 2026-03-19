package com.nhnacademy.shoppingmall.controller.auth;

import com.nhnacademy.shoppingmall.point.domain.PointHistory;
import com.nhnacademy.shoppingmall.point.service.PointHistoryService;
import com.nhnacademy.shoppingmall.user.domain.User;
import com.nhnacademy.shoppingmall.user.exception.UserAlreadyExistsException;
import com.nhnacademy.shoppingmall.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;

@Slf4j
@Controller
public class SignupPostController{
    private final UserService userService;
    private final PointHistoryService pointService;

    public SignupPostController(UserService userService, PointHistoryService pointService) {
        this.userService = userService;
        this.pointService = pointService;
    }

    @PostMapping("/signupAction.do")
    public String execute(@RequestParam("user_id") String userId,
                          @RequestParam("user_name") String userName,
                          @RequestParam("user_password") String userPwd,
                          @RequestParam("user_birth") String userBirth,
                          Model model) {
        LocalDateTime now = LocalDateTime.now();

        try {
            // TODO default point 값을 생성자에서 처리하는게 좋지 않을까??
            User newUser = new User(userId, userName, userPwd, userBirth, User.Auth.ROLE_USER,
                    1_000_000, now, null);
            userService.saveUser(newUser);

            PointHistory history = new PointHistory(userId, null, PointHistory.PointType.REGISTER,
                    1_000_000);
            pointService.savePointHistory(history);

            log.info("Signup Success: {}", userId);
            return "redirect:/login.do";
        } catch (UserAlreadyExistsException e) {
            log.error("Signup Failed (Duplicate ID): {}", userId);
            model.addAttribute("errorMessage", "이미 존재하는 아이디 입니다.");
            return "shop/login/signup_form";
        } catch (Exception e) {
            log.error("Signup Failed", e);
            return "shop/login/signup_form";
        }

    }
}
