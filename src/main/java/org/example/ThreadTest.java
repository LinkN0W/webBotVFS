package org.example;

import org.example.entities.Proxy;
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
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;

public class ThreadTest extends Thread {


    Queue<Long> idThreadQueue;

    Queue<String> proxiesQueue;

    Queue<User> usersQueue;

    Queue<String> accountsQueue;

    User user;

    String proxy;

    boolean isRegistered = false;

    ConcurrentLinkedQueue<Task> scheduleTask;


    public ThreadTest(Queue<Long> idThreadQueue, Queue<String> proxiesQueue, Queue<User> usersQueue, Queue<String> accountsQueue, ConcurrentLinkedQueue<Task> scheduleTask) {
        this.idThreadQueue = idThreadQueue;
        this.proxiesQueue = proxiesQueue;
        this.usersQueue = usersQueue;
        this.accountsQueue = accountsQueue;
        this.scheduleTask = scheduleTask;

    }


    @Override
    public void run() {

        idThreadQueue.offer(Thread.currentThread().getId());

        proxy = proxiesQueue.element();
        proxiesQueue.offer(proxiesQueue.poll());

        user = usersQueue.element();
        usersQueue.offer(usersQueue.poll());

        String email = accountsQueue.element();
        accountsQueue.offer(accountsQueue.poll());


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


        driver.findElement(By.xpath("//*[@id=\"mat-input-0\"]")).sendKeys("" + email);
        driver.findElement(By.xpath("//*[@id=\"mat-input-1\"]")).sendKeys("Nikita2002link@");

        SolverCaptchaScript solverCaptchaScript = new TwoCaptchaSolverScript(driver);


        try {
            solverCaptchaScript.solveCaptcha();
        } catch (Exception e) {
            System.out.println("I cant solve captcha");
            idThreadQueue.poll();
            driver.close();
            driver.quit();
        }

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        DriverScriptRequest driverScriptRequest = new DriverScriptRequest(driver);

        try {
            driverScriptRequest.setResponseTextField("//*[@id=\"mat-tab-content-0-0\"]/div/div");
        } catch (NoSuchElementException elementException) {
            System.out.println("I cant enter");
            idThreadQueue.poll();
            driver.close();
            driver.quit();
        }


        WebElement element = driver.findElement(By.xpath("//*[@id=\"mat-tab-label-0-0\"]/div"));


        JsonHandler jsonHandler = JsonHandlerFactory.createJsonHandler(driverScriptRequest.checkIsSlotAvailable(user));

        ClassicDate date;
        String jsonApplicants = driverScriptRequest.sendApplicant(user, email);

        String urn = JsonParser.findUrn(jsonApplicants);


        System.out.println(Thread.currentThread().getId());
        System.out.println(idThreadQueue.element());

        while (true) {



            if (idThreadQueue.element() == Thread.currentThread().getId()) {

                //System.out.println("I begin to work " + Thread.currentThread().getId() + " with " + proxy + " for " + user.getFirstName());
                for (int i = 0; i < 10; i++) {

                    element.click();
                    jsonHandler = JsonHandlerFactory.createJsonHandler(driverScriptRequest.checkIsSlotAvailable(user));


                    if (jsonHandler == null){
                        try {
                            Thread.sleep(5000);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        continue;
                    }

                    if (jsonHandler.getElementByKey("earliestDate") != null) {

                        System.out.println("Slot is available");
                        String jsonSlots = driverScriptRequest.getAvailableSlots(ClassicDate.formatDate(jsonHandler.getElementByKey("earliestDate").toString()), user, urn);

                        if (jsonSlots == null){
                            try {
                                Thread.sleep(5000);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                            continue;
                        }


                        List<Integer> allocationList = JsonParser.findAllocationIdsList(jsonSlots);

                        if (allocationList == null){
                            try {
                                Thread.sleep(5000);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                            continue;

                        }

                        System.out.println(allocationList);

                        allocationList.stream().forEach(e -> scheduleTask.offer(new Task(e, user.getVisaCategory())));

                        System.out.println("Slot for "+user.getLastName()+" is ready");


                        scheduleTask.poll();

                        try {
                            int amount = JsonParser.getAmount(driverScriptRequest.mapvasCounter(urn));
                            String jsonSchedule = driverScriptRequest
                                    .scheduleBooking(scheduleTask.element().getAllocationId(),
                                            urn, user, amount);
                            System.out.println(jsonSchedule);
                            scheduleTask.poll();
                            isRegistered = true;
                            break;
                            //userQueue.poll();
                        } catch (ResponseException e) {
                            break;
                        }

                    }
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                }
                    //System.out.println("I have finished already " + Thread.currentThread().getId());

                    if (isRegistered) {
                        int queueSize = usersQueue.size();
                        for (int k = 0; k < queueSize; k++) {
                            User userForRemove = usersQueue.poll();
                            if (userForRemove.getId() != user.getId()) {
                                usersQueue.add(userForRemove);
                            }
                        }

                    }
                    idThreadQueue.poll();
                    driver.close();
                    driver.quit();
                    break;
                }



                if (scheduleTask.size() != 0) {
                    boolean isPresent = false;
                    for (Task task : scheduleTask) {
                        if (task.getVisaCategory() == user.getVisaCategory()) {
                            isPresent = true;
                            break;
                        }
                    }


                    if (isPresent) {
                        try {
                            int amount = JsonParser.getAmount(driverScriptRequest.mapvasCounter(urn));
                            String jsonSchedule = driverScriptRequest
                                    .scheduleBooking(scheduleTask.element().getAllocationId(),
                                            urn, user, amount);
                            System.out.println(jsonSchedule);
                            scheduleTask.poll();
                            int queueSize = usersQueue.size();
                            for (int k = 0; k < queueSize; k++) {
                                User userForRemove = usersQueue.poll();
                                if (userForRemove.getId() != user.getId()) {
                                    usersQueue.add(userForRemove);
                                }
                            }
                        } catch (ResponseException e) {
                            System.out.println(e.getMessage());
                            usersQueue.poll();
                            driver.close();
                            driver.quit();
                            break;
                        }
                    }

                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }



                }

                element.click();
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            }
        }


}