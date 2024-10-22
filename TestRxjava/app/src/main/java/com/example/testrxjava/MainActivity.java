package com.example.testrxjava;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ShareCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.w3c.dom.Text;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private final static String TAG = "MainActivity";
    private TextView txtResult, txtUsersResult;
    private CompositeDisposable compositeDisposablel;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        txtResult = findViewById(R.id.txtResult);
        txtUsersResult = findViewById(R.id.txtUsersResult);
        compositeDisposablel = new CompositeDisposable();
        // Using Single
        //fetchMainProductName();
        // Using Observable
        //fetchMainProductName1();
        //fetchUsers();
        fetchAllData();
    }

    @SuppressLint("CheckResult")
    private void fetchMainProductName() {
        new ApiService().getMainProductName()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        mainProductName -> {
                            Log.d(TAG, "main product name = " + mainProductName);
                            updateUi(mainProductName);
                        },
                        throwable -> {
                            Log.d(TAG, "error = " + throwable.getMessage());
                        }
                );
    }

    @SuppressLint("CheckResult")
    private void fetchMainProductName1() {
        new ApiService().getMainProductName1()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        mainProductName -> {
                            Log.d(TAG, "main product name 1 = " + mainProductName);
                            updateUi(mainProductName);
                        },
                        throwable -> {
                            Log.d(TAG, "error = " + throwable.getMessage());
                        },
                        () -> {
                            Log.d(TAG, "Task is successful");
                        }
                );
    }

    @SuppressLint("CheckResult")
    private void fetchUsers() {
        new ApiService().getUsers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        users -> {
                            updateUserUi(users);
                        },
                        throwable -> {
                            Log.d(TAG, throwable.getMessage());
                        },
                        () -> {
                            Log.d(TAG, "Task is completed");
                        }
                );
    }

    private void fetchAllData() {
        compositeDisposablel.add(new ApiService().getMainProductName()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        mainProductName -> {
                            Log.d(TAG, "main product name = " + mainProductName);
                        },
                        throwable -> {
                            Log.d(TAG, "error = " + throwable.getMessage());
                        }
                )
        );
        compositeDisposablel.add(new ApiService().getMainProductName1()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        mainProductName -> {
                            Log.d(TAG, "main product name 1 = " + mainProductName);
                            updateUi(mainProductName);
                        },
                        throwable -> {
                            Log.d(TAG, "error = " + throwable.getMessage());
                        },
                        () -> {
                            Log.d(TAG, "Task is successful");
                        }
                )
        );
        compositeDisposablel.add(new ApiService().getUsers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        users -> {
                            updateUserUi(users);
                        },
                        throwable -> {
                            Log.d(TAG, throwable.getMessage());
                        },
                        () -> {
                            Log.d(TAG, "Task is completed");
                        }
                )
        );
    }

    public void updateUi(String newText) {
        txtResult.setText(newText);
    }

    public void updateUserUi(List<User> users) {
        String msg = "";
        for (User user : users) {
            msg += user.getName() + " - " + user.getEmail() + "\n";
        }
        txtUsersResult.setText(msg);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposablel.clear();
    }
}