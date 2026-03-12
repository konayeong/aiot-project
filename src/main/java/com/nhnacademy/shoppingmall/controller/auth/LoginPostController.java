package com.nhnacademy.shoppingmall.controller.auth;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.point.domain.PointHistory;
import com.nhnacademy.shoppingmall.point.repository.impl.PointHistoryRepositoryImpl;
import com.nhnacademy.shoppingmall.point.service.PointHistoryService;
import com.nhnacademy.shoppingmall.point.service.impl.PointHistoryServiceImpl;
import com.nhnacademy.shoppingmall.user.domain.User;
import com.nhnacademy.shoppingmall.user.exception.UserNotFoundException;
import com.nhnacademy.shoppingmall.user.repository.impl.UserRepositoryImpl;
import com.nhnacademy.shoppingmall.user.service.UserService;
import com.nhnacademy.shoppingmall.user.service.impl.UserServiceImpl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Slf4j
@RequestMapping(method = RequestMapping.Method.POST,value = "/loginAction.do")
public class LoginPostController implements BaseController {

    private final UserService userService = new UserServiceImpl(new UserRepositoryImpl());
    private final PointHistoryService pointService = new PointHistoryServiceImpl(new PointHistoryRepositoryImpl());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        //todo#13-2 로그인 구현, session은 60분동안 유지됩니다.

        String userId = req.getParameter("user_id");
        String userPwd = req.getParameter("user_password");

        try{
            User user = userService.doLogin(userId, userPwd);

            LocalDateTime lastLoginAt = user.getLatestLoginAt();
            LocalDate today = LocalDate.now();

            if(lastLoginAt == null || lastLoginAt.toLocalDate().isBefore(today)) {
                user.setUserPoint(user.getUserPoint() + 10000);
                userService.updateUser(user);

                pointService.savePointHistory(new PointHistory(
                        user.getUserId(), null, PointHistory.PointType.LOGIN, 10000
                ));
                log.info("Daily first login point(10,000) rewarded to user: {}", userId);
            }
            HttpSession session = req.getSession(true);

            session.setMaxInactiveInterval(60 * 60);
            session.setAttribute("loginUser", user);

            log.debug("login user : {}", user.getUserId());
            return "redirect:/index.do";

        }catch (UserNotFoundException e) {
            req.setAttribute("errorMessage", "아이디와 비밀번호가 일치하지 않습니다.");
            return "shop/login/login_form";
        }

    }
}
