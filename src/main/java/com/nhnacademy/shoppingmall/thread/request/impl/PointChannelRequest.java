//package com.nhnacademy.shoppingmall.thread.request.impl;
//
//import com.nhnacademy.shoppingmall.point.domain.PointHistory;
//import com.nhnacademy.shoppingmall.point.repository.impl.PointHistoryRepositoryImpl;
//import com.nhnacademy.shoppingmall.point.service.PointHistoryService;
//import com.nhnacademy.shoppingmall.point.service.impl.PointHistoryServiceImpl;
//import com.nhnacademy.shoppingmall.thread.request.ChannelRequest;
//import com.nhnacademy.shoppingmall.user.domain.User;
//import com.nhnacademy.shoppingmall.user.exception.UserNotFoundException;
//import com.nhnacademy.shoppingmall.user.repository.impl.UserRepositoryImpl;
//import com.nhnacademy.shoppingmall.user.service.UserService;
//import com.nhnacademy.shoppingmall.user.service.impl.UserServiceImpl;
//import lombok.extern.slf4j.Slf4j;
//
//import java.sql.Connection;
//
//@Slf4j
//public class PointChannelRequest extends ChannelRequest {
//    private final int orderId;
//    private final String userId;
//    private final int earnedPoint;
//
//    public PointChannelRequest(int orderId, String userId, int earnedPoint) {
//        this.orderId = orderId;
//        this.userId = userId;
//        this.earnedPoint = earnedPoint;
//    }
//
//    @Override
//    public void execute() {
//        DbConnectionThreadLocal.initialize();
//        //todo#14-5 포인트 적립구현, connection은 point적립이 완료되면 반납합니다.
//        try {
//            log.debug("포인트 적립: userId={}, 적립예정 포인트:{}", userId, earnedPoint);
//
//            UserService userService = new UserServiceImpl(new UserRepositoryImpl());
//            PointHistoryService pointHistoryService = new PointHistoryServiceImpl(new PointHistoryRepositoryImpl());
//
//            User user = userService.getUser(userId);
//            if(user!=null) {
//                user.setUserPoint(user.getUserPoint() + earnedPoint);
//                userService.updateUser(user);
//            } else {
//                throw new UserNotFoundException(userId);
//            }
//
//            PointHistory pointHistory = new PointHistory(userId, orderId, PointHistory.PointType.ORDER_REWARD, earnedPoint);
//            pointHistoryService.savePointHistory(pointHistory);
//
//            Connection connection = DbConnectionThreadLocal.getConnection();
//            connection.commit();
//            log.debug("포인트 적립 완료");
//        } catch (Exception e) {
//            log.error("포인트 적립 실패: userId={}", userId, e);
//            DbConnectionThreadLocal.setSqlError(true);
//        } finally {
//            DbConnectionThreadLocal.reset();
//        }
//
//        log.debug("pointChannel execute");
//        DbConnectionThreadLocal.reset();
//    }
//}
