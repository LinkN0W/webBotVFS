package org.example.seleniumScripts;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class SolverCaptchaScript extends DriverScripts{


    private final String regex = "(?<=k=).+(?=&co=)";


    SolverCaptchaScript(WebDriver driver) {
        super(driver);

    }

     String getTokenCaptcha(String xpathFrameCaptcha){

        WebElement frameCaptcha = null;
        try{
            frameCaptcha = driver.findElement(By.xpath(xpathFrameCaptcha));
        } catch (NoSuchElementException elementException) {
            return null;
        }

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(frameCaptcha.getAttribute("src"));
        String tokenCaptcha = "";

        if (matcher.find()) {
            tokenCaptcha = matcher.group();
            System.out.println("Token: " + tokenCaptcha);
        } else {
            System.out.println("Token is not found");
        }

        return tokenCaptcha;
    }


    public abstract void solveCaptcha();



}
