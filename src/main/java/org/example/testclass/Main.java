package org.example.testclass;

import com.twocaptcha.TwoCaptcha;
import org.example.entities.User;
import org.example.enums.Gender;
import org.example.enums.Nationality;
import org.example.enums.VisaCategory;
import org.example.enums.VisaCenter;
import org.example.format.ClassicDate;
import org.example.jsonMethods.JsonHandler;
import org.example.jsonMethods.JsonHandlerFactory;
import org.example.seleniumScripts.DriverScriptRequest;
import org.example.seleniumScripts.SolverCaptchaScript;
import org.example.seleniumScripts.TwoCaptchaSolverScript;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;


import java.time.LocalDate;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {

        TwoCaptcha solver = new TwoCaptcha("2277c94a92955f560246de5a9ce9e16e");


        ChromeDriver driver = new ChromeDriver();



        JavascriptExecutor js = (JavascriptExecutor)driver;



        System.setProperty("webdriver.chrome.driver", "C:\\chromedriver");
        driver.get("https://visa.vfsglobal.com/rus/en/fra/login");
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        System.out.println(driver.findElement
                (By.xpath("/html/body/app-root/div/div/app-login/section/div/div/mat-card/p")).getText());

        driver.findElement(By.xpath("//*[@id=\"mat-input-0\"]")).sendKeys("tolik.baldin.79@mail.ru");
        driver.findElement(By.xpath("//*[@id=\"mat-input-1\"]")).sendKeys("Nikita2002link@");

        SolverCaptchaScript solverCaptchaScript = new TwoCaptchaSolverScript(driver);

        solverCaptchaScript.solveCaptcha();

        /*js.executeScript(
                "var xhr = new XMLHttpRequest();\n" +
                "\n" +
                "xhr.open('GET', 'https://lift-api.vfsglobal.com/master/center/hun/rus/ru-RU', true);\n" +
                "\n" +
                "xhr.onload = function() {\n" +
                "  if (xhr.status === 200) {\n" +
                "    var response = JSON.parse(xhr.responseText);\n" +
                "    console.log(response);\n" +
                "  }\n" +
                "};\n" +
                "\n" +

                "xhr.send();");*/

        //js.executeScript("console.log('hello');");

        //
        // /html/body/app-root/div/div/app-eligibility-criteria/section/form/mat-card[1]/p[2]




        //WebElement text = driver.findElement(By.xpath("/html/body/app-root/div/div/app-eligibility-criteria/section/form/mat-card[1]/p[2]"));
        /*js.executeScript(
                "var objects = document.getElementsByClassName('c-brand-grey-para');\n" +
                        "var xhr = new XMLHttpRequest();\n" +
                        "xhr.open('GET', 'https://lift-api.vfsglobal.com/appointment/slots?countryCode=rus&missionCode=hun&centerCode=Kazan&loginUser=tolik.baldin.79%40mail.ru&visaCategoryCode=MED&languageCode=ru-RU&applicantsCount=1&days=180&fromDate=18%2F09%2F2023&slotType=2&toDate=30%2F09%2F2023&urn=HUN23951680733&payCode=', true);\n" +
                        "xhr.onload = function() {\n" +
                        "  if (xhr.status === 200) {\n" +
                        "    objects[0].textContent = xhr.responseText;\n" +
                        "  }\n" +
                        "};\n" +
                        "\n" +
                        "xhr.send();");*/


        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        DriverScriptRequest driverScriptRequest = new DriverScriptRequest(driver);

        driverScriptRequest.setResponseTextField("//*[@id=\"mat-tab-content-0-0\"]/div/div");

        WebElement element = driver.findElement(By.xpath("//*[@id=\"mat-tab-label-0-0\"]/div"));




        User user = new User("129@MAIL.RU",
                "IVAN", "PEGRLLOV", Gender.Male,
                "1264743894", "8", "81224",
                LocalDate.now(), LocalDate.now(),
                Nationality.ALB, VisaCenter.FKAZ, VisaCategory.ShSt_Vaccinated);


        //String email = user.getLoginUser();


       ClassicDate date;
       int i = 0;
       int k = 0;
        long time = System.currentTimeMillis();
        while (true) {


            element.click();


           // JsonHandler jsonHandler = JsonHandlerFactory.createJsonHandler(driverScriptRequest.checkIsSlotAvailable(user, email));


            if((k & 1) == 1) {
                i++;
                JsonHandler jsonHandler = JsonHandlerFactory.createJsonHandler(driverScriptRequest.checkIsSlotAvailable(user));
                System.out.println("Step " + i + "for " + (System.currentTimeMillis() - time) / 1000 + " seconds");
                try {
                    Thread.sleep(110000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

            }
            k++;

            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }






            /*JsonHandler jsonHandler = JsonHandlerFactory.createJsonHandler(driverScriptRequest.checkIsSlotAvailable(user));

            if(jsonHandler.getElementByKey("earliestDate") != null){

                String jsonApplicants = driverScriptRequest.sendApplicant(user);

                String urn = JsonParser.findUrn(jsonApplicants);

                String jsonSlots = driverScriptRequest.getAvailableSlots(ClassicDate.formatDate(jsonHandler.getElementByKey("earliestDate").toString()), user, urn);

                System.out.println(jsonSlots);


                String jsonSchedule = driverScriptRequest
                        .scheduleBooking(JsonParser.findAllocationId(jsonSlots),
                                urn, user);

                System.out.println(jsonSchedule);

                if(JsonParser.IsPaymentRequired(jsonSchedule)){
                    Payment payment = JsonParser.parsePayment(jsonSchedule);
                }



            }
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            i++;

            if(i > 20){

                    System.out.println("During "+ ( System.currentTimeMillis() - time)/1000 + "seconds to "+ i+ "requests");
                    i = 0;


            }*/

        }


    }
}