package org.example.testclass;

import org.example.entities.Proxy;
import org.example.jsonMethods.JsonParser;
import org.example.proxy6.ProxyRepository;

import org.example.services.ProxyService;
import org.example.services.UserService;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;

public class testMainWebDriver {


    public static void main(String[] args) {

        ProxyRepository proxyRepository = new ProxyRepository();

        ProxyService proxyService = new ProxyService();

        proxyService.getAll().forEach(e -> System.out.println(e));

        UserService userService = new UserService();


        userService.getNotAppointedUsers(1).forEach(user -> System.out.println(user));

    }
}
