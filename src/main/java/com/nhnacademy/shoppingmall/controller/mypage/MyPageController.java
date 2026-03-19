package com.nhnacademy.shoppingmall.controller.mypage;

import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.point.domain.PointHistory;
import com.nhnacademy.shoppingmall.point.service.PointHistoryService;
import com.nhnacademy.shoppingmall.user.domain.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MyPageController {
    private final PointHistoryService pointHistoryService;

    public MyPageController(PointHistoryService pointHistoryService) {
        this.pointHistoryService = pointHistoryService;
    }

    @GetMapping("/mypage/index.do")
    public String execute(HttpServletRequest req,
                          @RequestParam(name = "page", defaultValue = "1") int page,
                          Model model) {
        HttpSession session = req.getSession(false);
        User user = (User) session.getAttribute("loginUser");

        int pageSize = 10;

        Page<PointHistory> pointHistoryPage = pointHistoryService.getPointHistoryPage(user.getUserId(), page, pageSize);

        model.addAttribute("pointHistoryPage", pointHistoryPage);
        model.addAttribute("currentPage", page);

        return "shop/mypage/index";
    }
}
