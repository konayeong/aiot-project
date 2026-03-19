package com.nhnacademy.shoppingmall.common.listener;

import com.nhnacademy.shoppingmall.user.domain.User;
import com.nhnacademy.shoppingmall.user.repository.impl.UserRepositoryImpl;
import com.nhnacademy.shoppingmall.user.service.UserService;
import com.nhnacademy.shoppingmall.user.service.impl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Objects;

@Slf4j
@Component
public class ApplicationListener implements ServletContextListener {
    private final UserService userService = new UserServiceImpl(new UserRepositoryImpl());
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        //todo#12 application 시작시 테스트 계정인 admin,user 등록합니다. 만약 존재하면 등록하지 않습니다.

        try{
            User admin = new User(
                    "admin", "관리자", "12345", "20030627", User.Auth.ROLE_ADMIN, 1_000_000, LocalDateTime.now(), null
            );
            saveUser(admin);

            User user = new User(
                    "user", "사용자", "12345", "20030627", User.Auth.ROLE_USER, 1_000_000, LocalDateTime.now(), null
            );
            saveUser(user);

        }catch (Exception e) {
        }
    }

    private void saveUser(User user) {
        User getUser = userService.getUser(user.getUserId());

        if(Objects.isNull(getUser)) {
            log.debug("[ApplicationListener] User 등록 ID : {}", user.getUserId());
            userService.saveUser(user);
        }
    }
}
