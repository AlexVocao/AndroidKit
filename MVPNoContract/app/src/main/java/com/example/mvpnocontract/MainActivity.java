package com.example.mvpnocontract;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.mvpnocontract.model.UserModel;
import com.example.mvpnocontract.presenter.UserPresenter;
import com.example.mvpnocontract.view.UserView;

public class MainActivity extends AppCompatActivity implements UserView {
    private final static String TAG = "MainActivity";
    private TextView txtResult;
    private UserPresenter presenter;

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

        txtResult  = findViewById(R.id.txtResult);

        UserModel userModel = new UserModel(9L, "David");
        presenter = new UserPresenter(userModel, this);
        //presenter = new UserPresenter(null, this);
        presenter.loadUser();
    }

    @Override
    public void showUser(UserModel userModel) {
        txtResult.setText(String.format("id: %d, name: %s", userModel.getId(), userModel.getName()));
    }

    @Override
    public void showError(String error) {
        txtResult.setText("Error: " + error);
    }
}