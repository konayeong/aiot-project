package com.nhnacademy.shoppingmall.controller.mypage;

import com.nhnacademy.shoppingmall.point.service.PointHistoryService;
import com.nhnacademy.shoppingmall.user.domain.User;
import com.nhnacademy.shoppingmall.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
public class WithdrawalController {
    private final UserService userService;
    private final PointHistoryService pointService;

    public WithdrawalController(UserService userService, PointHistoryService pointService) {
        this.userService = userService;
        this.pointService = pointService;
    }

    @PostMapping("/mypage/withdrawalAction.do")
    public String execute(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        User user = (User) session.getAttribute("loginUser");

        try {
            // TODO 탈퇴 처리 안됨 (cascade 설정이 없어서 자동 삭제가 안된다)
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
