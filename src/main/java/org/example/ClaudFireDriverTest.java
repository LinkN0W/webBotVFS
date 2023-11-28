package org.example;

import org.example.seleniumScripts.DriverScripts;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;

public class ClaudFireDriverTest {
    public static void main(String[] args) {

        String proxy = "";

        ChromeOptions option = new ChromeOptions();

        if (!proxy.equals("")) option.addArguments("--proxy-server=http://" + proxy);


        option.addArguments("--disable-blink-features=AutomationControlled");
        option.addArguments("--disable-extensions");
        option.setExperimentalOption("useAutomationExtension", false);
        option.setExperimentalOption("excludeSwitches", List.of("enable-automation"));

        ChromeDriver driver = new ChromeDriver(option);
        driver.get("https://rucaptcha.com/42");

        JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;

        /*
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        javascriptExecutor.executeScript(
                "let p;\n" +
                        "const i = setInterval(()=>{\n" +
                        "    if (window.turnstile) {\n" +
                        "        clearInterval(i)\n" +
                        "        window.turnstile.render = (a,b) => {\n" +
                        "            p = {\n" +
                        "                type: \"TurnstileTaskProxyless\",\n" +
                        "                websiteKey: b.sitekey,\n" +
                        "                websiteURL: window.location.href,\n" +
                        "                data: b.cData,\n" +
                        "                pagedata: b.chlPageData,\n" +
                        "                action: b.action,\n" +
                        "                userAgent: navigator.userAgent\n" +
                        "            }\n" +
                        "            console.log(JSON.stringify(p));\n" +
                        "            var element = document.querySelector(\"body > app-root > div > div > app-login > section > div > div > mat-card > p\");\n" +
                        "            element.textContent = JSON.stringify(p);\n" +
                        "            window.tsCallback = b.callback;\n" +
                        "            return 'foo';\n" +
                        "        }\n" +
                        "    }\n" +
                        "},10);");

         */

    }
}
