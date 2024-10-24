package com.example.mvpnocontract;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
    private EditText edtName, edtId;
    private Button btnConfirm;
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

        txtResult = findViewById(R.id.txtResult);
        edtId = findViewById(R.id.edtId);
        edtName = findViewById(R.id.edtName);
        btnConfirm = findViewById(R.id.btnConfirm);

        presenter = new UserPresenter();
        initView();
    }

    @Override
    public void showUser(UserModel userModel) {
        txtResult.setText(String.format("id: %d, name: %s", userModel.getId(), userModel.getName()));
    }

    @Override
    public void showError(String error) {
        txtResult.setText("Error: " + error);
    }

    @Override
    public void confirm() {
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Long id = Long.parseLong(String.valueOf(edtId.getText()));
                String name = String.valueOf(edtName.getText());
                UserModel userModel = new UserModel(id, name);
                presenter.loadParam(userModel, MainActivity.this);
                presenter.loadUser();
            }
        });
    }
    void initView() {
        confirm();
    }
}