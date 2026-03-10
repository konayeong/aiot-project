package com.nhnacademy.shoppingmall.controller.auth;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.user.domain.User;
import com.nhnacademy.shoppingmall.user.exception.UserNotFoundException;
import com.nhnacademy.shoppingmall.user.repository.impl.UserRepositoryImpl;
import com.nhnacademy.shoppingmall.user.service.UserService;
import com.nhnacademy.shoppingmall.user.service.impl.UserServiceImpl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
@RequestMapping(method = RequestMapping.Method.POST,value = "/loginAction.do")
public class LoginPostController implements BaseController {

    private final UserService userService = new UserServiceImpl(new UserRepositoryImpl());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        //todo#13-2 로그인 구현, session은 60분동안 유지됩니다.

        String userId = req.getParameter("user_id");
        String userPwd = req.getParameter("user_password");

        User user = null;
        try{
            user = userService.doLogin(userId, userPwd);
            log.debug("login user : {}", user.getUserId());
        }catch (UserNotFoundException e) {
            return "shop/login/login_form";
        }

        if(Objects.nonNull(user)) {
            HttpSession session = req.getSession(true);

            session.setMaxInactiveInterval(60 * 60);
            session.setAttribute("loginUser", user);

            return "shop/main/index";
        }else{
            return "shop/login/login_form"; // 로그인 실패
        }

    }
}
