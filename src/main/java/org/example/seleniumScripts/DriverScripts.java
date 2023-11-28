package org.example.seleniumScripts;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

public abstract class DriverScripts {

    final WebDriver driver;
    final JavascriptExecutor javascriptExecutor;

    String accessToken;
    DriverScripts(WebDriver driver){
        this.driver = driver;
        javascriptExecutor = (JavascriptExecutor) this.driver;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
