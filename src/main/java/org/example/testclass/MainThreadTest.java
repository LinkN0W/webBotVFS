package org.example.testclass;

import org.example.jsonMethods.JsonParser;
import org.openqa.selenium.json.Json;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

public class MainThreadTest{

    public static void main(String[] args) {


        String jsonSlots = "[{\"mission\": \"France\", \"center\": \"France Visa Application Centre-Irkutsk\", \"visacategory\": \"LS_ASSISTANTDELANGUE\", \"date\": \"10/31/2023\", \"isWeekend\": false, \"counters\": [{\"allocationCategory\": \"Long Stay\", \"categoryCode\": \"CC & GU\", \"groups\": [{\"visaGroupName\": \"Long Stay for educational and professional purpose\", \"timeSlots\": [{\"allocationId\": 5666409, \"timeSlot\": \"9:20-9:40\", \"slotType\": \"Normal\", \"totalSeats\": 1, \"remainingSeats\": 1, \"startTimetick\": 0}]}]}], \"error\": null}]";


        int id = JsonParser.findAllocationId(jsonSlots,1, 0);

        List<Integer> ids = JsonParser.findAllocationIdsList(jsonSlots);
        System.out.println(ids);


    }
}
