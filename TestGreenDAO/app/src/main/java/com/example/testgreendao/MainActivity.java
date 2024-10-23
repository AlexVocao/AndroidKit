package com.example.testgreendao;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

public class MainActivity extends AppCompatActivity {

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

        DaoSession daoSession = ((MyApp)getApplication()).getDaoSession();
        UserDao userDao = daoSession.getUserDao();

        // Insert a new User
        User user1 = new User(null, "Alice", 25);
        User user2 = new User(null, "Bobpie", 27);
        userDao.insert(user1);
        userDao.insert(user2);

        // Query all users
        List<User> users = userDao.loadAll();

        // Display the results
        for (User user: users) {
            Log.d("ManActivity", "User: " + user.getName() + ", Age: " + user.getAge());
        }
    }
}