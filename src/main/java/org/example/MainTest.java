package org.example;

import org.example.entities.User;
import org.example.enums.Gender;
import org.example.enums.Nationality;
import org.example.enums.VisaCategory;
import org.example.enums.VisaCenter;
import org.example.services.ProxyService;
import org.example.services.UserService;

import java.time.LocalDate;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.*;

public class MainTest {
    public static void main(String[] args) throws InterruptedException {




        Queue<Integer> numberQueue = new ArrayDeque<>();
        numberQueue.offer(0);
        numberQueue.offer(1);
        numberQueue.offer(2);

        ProxyService proxyService = new ProxyService();

        List<String> listProxy = new ArrayList<>();
        proxyService.getAll().forEach(proxy -> listProxy.add(proxy.getIp()));


        List<String> listEmail = new ArrayList<>();
        listEmail.add("ivan.kuleba.03@mail.ru");
        listEmail.add("tolik.baldin.79@mail.ru");
        listEmail.add("flyboad@mail.ru");






        Queue<String> proxiesQueue = new ArrayDeque<>();
        Queue<String> accountsQueue = new ArrayDeque<>();


        UserService userService = new UserService();


        listEmail.stream().forEach(e-> accountsQueue.offer(e));

        listProxy.stream().forEach(e-> proxiesQueue.offer(e));


        ConcurrentMap<Integer, User> userConcurrentMap = new ConcurrentHashMap<>();


        System.out.println(userService.getFirstNotAppointedUser());
        int number = 0;
        for(User user : userService.getNotAppointedUsers(3)){
            numberQueue.offer(number);
            userConcurrentMap.put(number, user);
            number++;
            if(number > 2) break;
        }

        System.out.println(userConcurrentMap.size());

        Thread.sleep(10000);

        ConcurrentLinkedQueue<Integer> atomicQueue =  new ConcurrentLinkedQueue<>();

        ExecutorService executorService = Executors.newFixedThreadPool(3);

        ConcurrentLinkedQueue<Task> scheduleTask = new ConcurrentLinkedQueue<>();

        while (true){
            if(atomicQueue.size() < 3) {

                if(userConcurrentMap.size() < 3){
                    userConcurrentMap.put(numberQueue.element(), userService.getFirstNotAppointedUser());
                }
                executorService.execute(new BotThread(proxiesQueue.element(), userConcurrentMap, accountsQueue.element(), numberQueue.element(), atomicQueue, scheduleTask));
                numberQueue.offer(numberQueue.poll());
                proxiesQueue.offer(proxiesQueue.poll());
                accountsQueue.offer(accountsQueue.poll());
                Thread.sleep(1000);
            }

        }




    }
}
