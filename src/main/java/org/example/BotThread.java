package org.example;

import org.example.entities.User;
import org.example.exceptions.ResponseException;
import org.example.format.ClassicDate;
import org.example.jsonMethods.JsonHandler;
import org.example.jsonMethods.JsonHandlerFactory;
import org.example.jsonMethods.JsonParser;
import org.example.seleniumScripts.DriverScriptRequest;
import org.example.seleniumScripts.SolverCaptchaScript;
import org.example.seleniumScripts.TwoCaptchaSolverScript;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

public class BotThread extends Thread {

    String proxy = null;
    User user;

    String email;

    int idThread;

    ConcurrentLinkedQueue<Integer> queueIdOfThread;

    ConcurrentLinkedQueue<Task> scheduleTask;

    ConcurrentMap<Integer, User> userConcurrentMap;

    boolean isRegistered = false;

    public BotThread(String proxy, ConcurrentMap<Integer, User> userConcurrentMap, String email, int idThread,
                     ConcurrentLinkedQueue<Integer> atomicQueue, ConcurrentLinkedQueue<Task> scheduleTask) {
        this.proxy = proxy;
        this.userConcurrentMap = userConcurrentMap;
        this.email = email;
        queueIdOfThread = atomicQueue;
        this.idThread = idThread;
        queueIdOfThread.offer(idThread);
        this.scheduleTask = scheduleTask;
    }

    public BotThread(User user, String email, int idThread,
                     ConcurrentLinkedQueue<Integer> atomicQueue, ConcurrentLinkedQueue<Task> scheduleTask) {
        this.user = user;
        this.email = email;
        queueIdOfThread = atomicQueue;
        this.idThread = idThread;
        queueIdOfThread.offer(idThread);
        this.scheduleTask = scheduleTask;
    }


    public void run() {

        System.out.printf("%s started... \n", Thread.currentThread().getName());
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            System.out.println("Thread has been interrupted");
        }
        user = userConcurrentMap.get(idThread);

        ChromeOptions option = new ChromeOptions();

        if (!proxy.equals("")) option.addArguments("--proxy-server=http://" + proxy);


        option.addArguments("--disable-blink-features=AutomationControlled");
        option.addArguments("--disable-extensions");
        option.setExperimentalOption("useAutomationExtension", false);
        option.setExperimentalOption("excludeSwitches", List.of("enable-automation"));

        System.out.println(proxy);

        ChromeDriver driver = new ChromeDriver(option);


        driver.get("https://visa.vfsglobal.com/rus/en/fra/login");
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        System.out.println(driver.findElement
                (By.xpath("/html/body/app-root/div/div/app-login/section/div/div/mat-card/p")).getText());

        driver.findElement(By.xpath("//*[@id=\"mat-input-0\"]")).sendKeys("" + email);
        driver.findElement(By.xpath("//*[@id=\"mat-input-1\"]")).sendKeys("Nikita2002link@");

        SolverCaptchaScript solverCaptchaScript = new TwoCaptchaSolverScript(driver);


        try {
            solverCaptchaScript.solveCaptcha();
        }
        catch (StaleElementReferenceException e){
            queueIdOfThread.poll();
            driver.close();
            driver.quit();
        }


        DriverScriptRequest driverScriptRequest = new DriverScriptRequest(driver);

        try {
            driverScriptRequest.setResponseTextField("//*[@id=\"mat-tab-content-0-0\"]/div/div");
        }
        catch (NoSuchElementException elementException){
            queueIdOfThread.poll();
            driver.close();
            driver.quit();
        }
        WebElement element = driver.findElement(By.xpath("//*[@id=\"mat-tab-label-0-0\"]/div"));



        JsonHandler jsonHandler = JsonHandlerFactory.createJsonHandler(driverScriptRequest.checkIsSlotAvailable(user));

        ClassicDate date;
        String jsonApplicants = driverScriptRequest.sendApplicant(user, email);

        String urn = JsonParser.findUrn(jsonApplicants);

        //String jsonSlots = driverScriptRequest.getAvailableSlots(ClassicDate.formatDate(jsonHandler.getElementByKey("earliestDate").toString()), user, urn);
        try {
            Thread.sleep(60000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        int count_term = 0;
        while (true) {

            System.out.println("queue id "+queueIdOfThread.peek() + " thread id "+idThread);

            if (queueIdOfThread.peek() == idThread) {
                System.out.println("I begin to work " + idThread);


                for (int i = 0; i < 20; i++) {
                    System.out.println("Step is " + i + " " + idThread);

                    jsonHandler = JsonHandlerFactory.createJsonHandler(driverScriptRequest.checkIsSlotAvailable(user));

                    if (jsonHandler.getElementByKey("earliestDate") != null) {



                        String jsonSlots = driverScriptRequest.getAvailableSlots(ClassicDate.formatDate(jsonHandler.getElementByKey("earliestDate").toString()), user, urn);

                        List<Integer> allocationList = JsonParser.findAllocationIdsList(jsonSlots);

                        System.out.println(allocationList);

                        allocationList.stream().forEach(e-> scheduleTask.offer(new Task(e, user.getVisaCategory())));


                        scheduleTask.poll();

                        try {
                            int amount = JsonParser.getAmount(driverScriptRequest.mapvasCounter(urn));

                            isRegistered = true;
                            break;
                            //userQueue.poll();
                        } catch (ResponseException e) {
                            break;
                        }

                    }

                    try {
                        Thread.sleep(30000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }


                }
                if(isRegistered ) {
                    userConcurrentMap.remove(idThread);
                }
                queueIdOfThread.poll();
                driver.close();
                driver.quit();
                break;

            }


            if(scheduleTask.size() != 0){
                boolean isPresent = false;
                for(Task task : scheduleTask){
                    if(task.getVisaCategory() == user.getVisaCategory()){
                        isPresent = true;
                        break;
                    }
                }


                if(isPresent) {
                    try {
                        int amount = JsonParser.getAmount(driverScriptRequest.mapvasCounter(urn));
                        userConcurrentMap.remove(idThread);
                    } catch (ResponseException e) {
                        System.out.println(e.getMessage());
                        queueIdOfThread.poll();
                        driver.close();
                        driver.quit();
                        break;
                    }
                /*amount = JsonParser.getAmount(driverScriptRequest.mapvasCounter(urn));

                String jsonSchedule = driverScriptRequest
                        .scheduleBooking(scheduleTask.poll(),
                                urn, user, amount);*/
                }
            }


            if(count_term++ > 10) {
                element.click();
                count_term = 0;
                System.out.println("I'm waiting " + idThread);
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }


        }

    }
}
