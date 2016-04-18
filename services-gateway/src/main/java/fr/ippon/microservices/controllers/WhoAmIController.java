package fr.ippon.microservices.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/")
public class WhoAmIController {

    @Value("${spring.application.name:}")
    private String appName;

    @Value("${server.port:-1}")
    private int serverPort;

    @RequestMapping(method = RequestMethod.GET, value = "/whoami")
    public String whoAmI() {
        return "I am the " + appName.replace("-", " ").toUpperCase() + ", running on port " + serverPort;
    }
}
