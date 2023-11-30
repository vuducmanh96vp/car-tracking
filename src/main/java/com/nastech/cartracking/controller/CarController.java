package com.nastech.cartracking.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * <H3>
 * CarController
 * </H3>
 *
 * @author manhvud
 * @since 2023/11/16
 */
@Controller
public class CarController {

    @RequestMapping("/")
    public String index() {

        return "index";
    }


}