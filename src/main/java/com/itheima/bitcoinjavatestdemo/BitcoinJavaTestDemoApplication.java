package com.itheima.bitcoinjavatestdemo;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import java.util.Scanner;

@SpringBootApplication
public class BitcoinJavaTestDemoApplication {
    public static String port;
    public static void main(String[] args) {
//        SpringApplication.run(BitcoinJavaTestDemoApplication.class, args);
        Scanner scanner = new Scanner(System.in);
         port = scanner.nextLine();
        new SpringApplicationBuilder(BitcoinJavaTestDemoApplication.class).properties("server.port="+port).run(args);
    }
}
