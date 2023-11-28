package org.example.testclass;

import org.example.BotThread;
import org.example.Task;
import org.example.ThreadTest;
import org.example.entities.User;
import org.example.services.ProxyService;
import org.example.services.UserService;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.*;

public class MainTestThread {
    public static void main(String[] args) throws InterruptedException {

        ProxyService proxyService = new ProxyService();

        List<String> listProxy = new ArrayList<>();
        proxyService.getAll().forEach(proxy -> listProxy.add(proxy.getIp()));


        Queue<String> proxiesQueue = new ArrayDeque<>();
        listProxy.stream().forEach(e-> proxiesQueue.offer(e));


        ConcurrentMap<Integer, User> userConcurrentMap = new ConcurrentHashMap<>();


        List<String> listEmail = new ArrayList<>();
        listEmail.add("ivan.kuleba.03@mail.ru");
        listEmail.add("tolik.baldin.79@mail.ru");
        listEmail.add("flyboad@mail.ru");



        Queue<String> accountsQueue = new ArrayDeque<>();

        listEmail.stream().forEach(e-> accountsQueue.offer(e));

        ConcurrentLinkedQueue<Task> scheduleTask = new ConcurrentLinkedQueue<>();

        UserService userService = new UserService();

        Queue<User> usersQueue = new ArrayDeque<>();

        int number = 0;
        for(User user : userService.getNotAppointedUsers(3)){
            usersQueue.add(user);
        }

        System.out.println(userConcurrentMap);

        Queue<Long> idThreadQueue = new ArrayDeque<>();


        ExecutorService executorService = Executors.newFixedThreadPool(3);

        while (true){
            if(idThreadQueue.size() < 3) {
                executorService.execute(new ThreadTest(idThreadQueue, proxiesQueue, usersQueue, accountsQueue, scheduleTask));
                Thread.sleep(1000);
            }

        }






    }
}
