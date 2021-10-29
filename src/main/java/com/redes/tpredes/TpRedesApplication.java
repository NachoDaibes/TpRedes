package com.redes.tpredes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TpRedesApplication {

    public static void main(String[] args) {
        SpringApplication.run(TpRedesApplication.class, args);

        Protocolo e1 = new Protocolo();

        e1.send();
    }
}
