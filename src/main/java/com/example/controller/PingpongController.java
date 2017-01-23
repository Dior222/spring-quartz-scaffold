package com.example.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * PingpongController
 *
 * @author liufenglin
 * @email fenglin_liu@hansight.com
 * @date 17/1/23
 */
@RestController
public class PingpongController {

    private static final Logger logger = LoggerFactory.getLogger(PingpongController.class);

    @GetMapping("/ping")
    public void ping() {
//        System.out.println("pong");
        logger.info("ping-pong.");
    }

}
