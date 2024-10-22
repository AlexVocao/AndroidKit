package com.example.testrxjava;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

public class ApiService {
    public Single<String> getMainProductName() {
        return Single.create(emitter -> {
            // Simulate to get product names from the internet
            try {
                Thread.sleep(2000);
            } catch (Exception e) {
                emitter.onError(e);
            }

            // emit data
            emitter.onSuccess("BMW");
        });
    }
    public Observable<String> getMainProductName1() {
        return Observable.create(emitter -> {
            // Simulate get product names from the internet
            try {
                Thread.sleep(2000);
            } catch (Exception e) {
                emitter.onError(e);
            }

            // Emit data
            emitter.onNext("Audi");
            emitter.onComplete();
        });
    }
    public Observable<List<User>> getUsers() {
        return Observable.create(emitter -> {
            // Simulate to get users from the internet
            try {
                Thread.sleep(2000);
            } catch (Exception e) {
                emitter.onError(e);
            }

            // List of users
            List<User> users = new ArrayList<>();
            users.add(new User("Alex", "abc@hotmail.com"));
            users.add(new User("Alice", "def@hotmail.com"));
            users.add(new User("Bob", "klm@hotmail.com"));

            // Emit data
            emitter.onNext(users);
            emitter.onComplete();
        });
    }
}
