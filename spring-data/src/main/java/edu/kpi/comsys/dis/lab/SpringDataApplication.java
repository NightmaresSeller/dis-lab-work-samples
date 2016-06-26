package edu.kpi.comsys.dis.lab;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringDataApplication {

    private static final Logger LOG = LoggerFactory.getLogger("Application");

    public static void main(String[] args) {
        SpringApplication.run(SpringDataApplication.class);
    }

}
