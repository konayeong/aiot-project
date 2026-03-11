package com.nhnacademy.shoppingmall.controller.auth;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.point.domain.PointHistory;
import com.nhnacademy.shoppingmall.point.repository.PointHistoryRepository;
import com.nhnacademy.shoppingmall.point.repository.impl.PointHistoryRepositoryImpl;
import com.nhnacademy.shoppingmall.point.service.PointHistoryService;
import com.nhnacademy.shoppingmall.point.service.impl.PointHistoryServiceImpl;
import com.nhnacademy.shoppingmall.user.domain.User;
import com.nhnacademy.shoppingmall.user.exception.UserAlreadyExistsException;
import com.nhnacademy.shoppingmall.user.repository.impl.UserRepositoryImpl;
import com.nhnacademy.shoppingmall.user.service.UserService;
import com.nhnacademy.shoppingmall.user.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

@Slf4j
@RequestMapping(method = RequestMapping.Method.POST, value = "/signupAction.do")
public class SignupPostController implements BaseController {
    private final UserService userService = new UserServiceImpl(new UserRepositoryImpl());
    private final PointHistoryService pointService = new PointHistoryServiceImpl(new PointHistoryRepositoryImpl());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String userId = req.getParameter("user_id");
        String userName = req.getParameter("user_name");
        String userPwd = req.getParameter("user_password");
        String userBirth = req.getParameter("user_birth");
        LocalDateTime now = LocalDateTime.now();

        try {
            User newUser = new User(userId, userName, userPwd, userBirth, User.Auth.ROLE_USER,
                    1_000_000, now, null);
            userService.saveUser(newUser);

            PointHistory history = new PointHistory(userId, null, PointHistory.PointType.REGISTER,
                    1_000_000, now);
            pointService.savePointHistory(history);

            log.info("Signup Success: {}", userId);
            return "redirect:/login.do";
        } catch (UserAlreadyExistsException e) {
            log.error("Signup Failed (Duplicate ID): {}", userId);
            req.setAttribute("errorMessage", "이미 존재하는 아이디 입니다.");
            return "shop/login/signup_form";
        } catch (Exception e) {
            log.error("Signup Failed", e);
            return "shop/login/signup_form";
        }

    }
}
