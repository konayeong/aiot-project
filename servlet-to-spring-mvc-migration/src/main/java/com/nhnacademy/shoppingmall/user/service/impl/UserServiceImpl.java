package com.nhnacademy.shoppingmall.user.service.impl;

import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.user.exception.UserAlreadyExistsException;
import com.nhnacademy.shoppingmall.user.exception.UserNotFoundException;
import com.nhnacademy.shoppingmall.user.service.UserService;
import com.nhnacademy.shoppingmall.user.domain.User;
import com.nhnacademy.shoppingmall.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public User getUser(String userId){
        //todo#4-1 회원조회
        Optional<User> opUser = userRepository.findById(userId);

//        if(opUser.isPresent()) {
//            return opUser.get();
//        }
//        return null;
        return opUser.orElse(null);
    }

    @Override
    public void saveUser(User user) {
        //todo#4-2 회원등록
        int userCnt = userRepository.countByUserId(user.getUserId());
        if(userCnt > 0) {
            throw new UserAlreadyExistsException(user.getUserId());
        }

        userRepository.save(user);
    }

    @Override
    public void updateUser(User user) {
        //todo#4-3 회원수정
        int userCnt = userRepository.countByUserId(user.getUserId());

        if(userCnt == 0) {
            throw new UserNotFoundException(user.getUserId());
        }

        userRepository.update(user);
    }

    @Override
    public void deleteUser(String userId) {
        //todo#4-4 회원삭제
        int userCnt = userRepository.countByUserId(userId);

        if(userCnt == 0) {
            throw new UserNotFoundException(userId);
        }

        userRepository.deleteByUserId(userId);
    }

    @Override
    public User doLogin(String userId, String userPassword) {
        //todo#4-5 로그인 구현, userId, userPassword로 일치하는 회원 조회
        Optional<User> opUser = userRepository.findByUserIdAndUserPassword(userId, userPassword);

        if(opUser.isEmpty()) {
            throw new UserNotFoundException(userId);
        }

        User user = opUser.get();

        LocalDateTime now = LocalDateTime.now();
        userRepository.updateLatestLoginAtByUserId(userId, now);
        user.setLatestLoginAt(now);

        return user;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<User> getUsers(int page, int pageSize, String userId) {
        return userRepository.findAll(page, pageSize, userId);
    }
}
