package org.example.testclass;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class DriverThread extends Thread{

    @Override
    public void run() {

        ChromeOptions option = new ChromeOptions();

        // if (proxy != null) option.addArguments("--proxy-server=http://" + proxy);

        //System.out.println(proxy);

    }
}
