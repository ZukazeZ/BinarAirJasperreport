package com.binarair.binarairrestapi.controller;

import com.binarair.binarairrestapi.model.request.UserRegisterRequest;
import com.binarair.binarairrestapi.model.response.UserRegisterResponse;
import com.binarair.binarairrestapi.model.response.WebResponse;
import com.binarair.binarairrestapi.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final static Logger log = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    @ResponseBody
    public ResponseEntity<WebResponse<UserRegisterResponse>> signup(@RequestBody @Valid UserRegisterRequest userRegisterRequest) {
        log.info("Call signup controller");
        UserRegisterResponse userRegisterResponse = userService.save(userRegisterRequest);
        WebResponse webResponse = new WebResponse(
                HttpStatus.CREATED.value(),
                HttpStatus.CREATED.getReasonPhrase(),
                userRegisterResponse
        );
        log.info("Successful user registration");
        return new ResponseEntity<>(webResponse, HttpStatus.CREATED);
    }
}