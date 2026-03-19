package com.nhnacademy.shoppingmall.controller.mypage;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.point.repository.impl.PointHistoryRepositoryImpl;
import com.nhnacademy.shoppingmall.point.service.PointHistoryService;
import com.nhnacademy.shoppingmall.point.service.impl.PointHistoryServiceImpl;
import com.nhnacademy.shoppingmall.user.domain.User;
import com.nhnacademy.shoppingmall.user.repository.impl.UserRepositoryImpl;
import com.nhnacademy.shoppingmall.user.service.UserService;
import com.nhnacademy.shoppingmall.user.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping(method = RequestMapping.Method.POST, value = "/mypage/withdrawalAction.do")
public class WithdrawalController implements BaseController {
    private final UserService userService = new UserServiceImpl(new UserRepositoryImpl());
    private final PointHistoryService pointService = new PointHistoryServiceImpl(new PointHistoryRepositoryImpl());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession(false);
        User user = (User) session.getAttribute("loginUser");

        try {
            pointService.deletePointHistory(user.getUserId());
            userService.deleteUser(user.getUserId());
            log.info("User withdrawal success: {}", user.getUserId());

            session.invalidate();

            return "redirect:/index.do";
        } catch (Exception e) {
            log.error("User withdrawal failed: {}", user.getUserId(), e);
            return "redirect:/mypage/index.do";
        }
    }
}
