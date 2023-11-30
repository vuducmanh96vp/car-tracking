package com.nastech.cartracking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages={"com.nastech.cartracking"})
public class CarLocationApplication {

    public static void main(String[] args) {
        SpringApplication.run(CarLocationApplication.class, args);
    }

}
