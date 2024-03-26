//package com.flinter.shop;
//
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//
//@ControllerAdvice
//public class MyExceptionHandler {
//
//    //api의 모든 controller 에서의 에러 캐치
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<String> handler() {
//        return ResponseEntity.status(400).body("Global Error!");
//    }
//}
