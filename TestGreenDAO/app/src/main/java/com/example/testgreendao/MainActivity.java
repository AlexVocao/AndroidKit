package com.example.testgreendao;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Button btnDeleteAll, btnListAll, btnDeleteOne, btnUpdateOne, btnCreateList;
    private DaoSession daoSession;
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

        btnDeleteAll = findViewById(R.id.btnDeleteAll);
        btnListAll = findViewById(R.id.btnListAll);
        btnCreateList = findViewById(R.id.btnCreateList);
        btnUpdateOne = findViewById(R.id.btnUpdateOne);
        btnDeleteOne = findViewById(R.id.btnDeleteOne);

        daoSession = ((MyApp)getApplication()).getDaoSession();

        btnCreateList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createList();
            }
        });
        btnListAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listAll();
            }
        });
        btnUpdateOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateOne(1);
            }
        });
        btnDeleteOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteOne(1);
            }
        });
        btnDeleteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteAll();
            }
        });

        // =========================================================================================
        // Car
/*        CarDao carDao = daoSession.getCarDao();
        // Insert a new Car
        Car car1 = new Car(null, "BMW", 3.0);
        Car car2 = new Car(null, "Rolls-royce", 5.0);
        carDao.insert(car1);
        carDao.insert(car2);

        // Query all users
        List<Car> cars = carDao.loadAll();

        // Display the results
        for (Car car: cars) {
            Log.d("ManActivity", "Car: " + car.getName() + ", Engine: " + car.getEngine());
        }*/
    }
    void createList() {
        UserDao userDao = daoSession.getUserDao();
        User user1 = new User(null, "Alice", 25);
        User user2 = new User(null, "David", 27);
        userDao.insert(user1);
        userDao.insert(user2);
    }
    void listAll() {
        // Query all users
        UserDao userDao = daoSession.getUserDao();
        List<User> users = userDao.loadAll();
        // Display the results
        for (User user: users) {
            Log.d("ManActivity", "User: " + "Id: " + user.getId()+", Name: " + user.getName() + ", Age: " + user.getAge());
        }
    }
    void deleteAll() {
        UserDao userDao = daoSession.getUserDao();
        userDao.deleteAll();
    }
    void updateOne(int index) {
        UserDao userDao = daoSession.getUserDao();
        User user = userDao.loadAll().get(index-1);
        user.setName("Salomon");
        userDao.update(user);
    }
    void deleteOne(int index) {
        UserDao userDao = daoSession.getUserDao();
        User user = userDao.loadAll().get(index-1);
        userDao.delete(user);
    }
}