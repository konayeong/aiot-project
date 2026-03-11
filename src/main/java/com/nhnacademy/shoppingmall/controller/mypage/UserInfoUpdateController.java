package com.nhnacademy.shoppingmall.controller.mypage;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.user.domain.User;
import com.nhnacademy.shoppingmall.user.repository.impl.UserRepositoryImpl;
import com.nhnacademy.shoppingmall.user.service.UserService;
import com.nhnacademy.shoppingmall.user.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping(method = RequestMapping.Method.POST, value = "/mypage/infoAction.do")
public class UserInfoUpdateController implements BaseController {
    private final UserService userService = new UserServiceImpl(new UserRepositoryImpl());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession(false);
        User user = (User) session.getAttribute("loginUser");

        String userName = req.getParameter("user_name");
        String userPassword = req.getParameter("user_password");

        try {
            user.setUserName(userName);
            user.setUserPassword(userPassword);

            userService.updateUser(user);
            session.setAttribute("loginUser", user);

            return "redirect:/mypage/index.do";
        } catch (Exception e) {
            log.error("User info update failed", e);
            req.setAttribute("errorMessage", "정보 수정 중 오류가 발생했습니다.");
            return "shop/mypage/info";
        }
    }
}
