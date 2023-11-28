package org.example;

import org.example.proxy6.ProxyMarket;
import org.example.services.ProxyService;

public class ProxyMain {
    public static void main(String[] args) {
        ProxyService proxyService = new ProxyService();
        System.out.println(proxyService.getAll());
    }
}
