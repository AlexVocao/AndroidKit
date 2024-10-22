package com.example.testrxjava;

import io.reactivex.rxjava3.core.Single;

public class ApiService {
    public Single<String> getMainProductName() {
        return Single.create(emitter -> {
            // Simulate get product names from the internet
            try {
                Thread.sleep(2000);
            } catch (Exception e) {
                emitter.onError(e);
            }

            // emit data
            emitter.onSuccess("BMW");
        });
    }
}
