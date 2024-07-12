package com.fpt.jpos;

//import com.fpt.jpos.config.RollbarConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JewelryProductionOrderSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(JewelryProductionOrderSystemApplication.class, args);
        //new RollbarConfig().rollbar().debug("Here is some debug message");
    }

}
