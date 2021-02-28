package com.andrew.thread;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.andrew.util.HttpUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class HTTPDemo {

    public static void main(String[] args) throws Exception {

        List<String> urls = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            urls.add("http://localhost:8993/rec-api/property/user/age/" + 1290148 + i);
        }
        long time = System.currentTimeMillis();
        ThreadPoolExecutor executor = new ThreadPoolExecutor(10, 80, 1L,
                TimeUnit.SECONDS, new ArrayBlockingQueue<>(100));
        List<FutureTask<JSON>> futureTasks = new ArrayList<>();
//        for (String url : urls) {
//            DoRequest request = new DoRequest(url);
//            FutureTask<JSON> futureTask = new FutureTask<>(request);
//            executor.submit(futureTask);
//            futureTasks.add(futureTask);
//        }

        List<CompletableFuture<JSONObject>> completableFutures = new ArrayList<>();
        for (String url : urls) {
            completableFutures.add(CompletableFuture.supplyAsync(() -> JSON.parseObject(HttpUtil.doGet(url)), executor));
        }
//        CompletableFuture<List<JSONObject>> sequence = sequence(completableFutures);
//        List<JSONObject> join = sequence.join();
//        System.out.println("cost: " + (System.currentTimeMillis() - time) + "\nresult: " + join.toString());

        List<JSONObject> collect = completableFutures.stream().map(CompletableFuture::join).collect(Collectors.toList());
        System.out.println("cost: " + (System.currentTimeMillis() - time) + "\nresult: " + collect.toString());
        executor.shutdown();
    }

    public static <T> CompletableFuture<List<T>> sequence(Collection<CompletableFuture<T>> futureCollection) {
        return CompletableFuture.allOf(futureCollection.toArray(new CompletableFuture[0]))
                .thenApply(v -> futureCollection.stream().map(CompletableFuture::join).collect(Collectors.toList()));
    }

}
