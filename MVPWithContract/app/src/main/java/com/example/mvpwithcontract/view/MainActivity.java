package com.example.mvpwithcontract.view;

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

import com.example.mvpwithcontract.R;
import com.example.mvpwithcontract.contract.UserContract;
import com.example.mvpwithcontract.model.User;
import com.example.mvpwithcontract.presenter.UserPresenter;

public class MainActivity extends AppCompatActivity implements UserContract.View {
    private EditText edtId, edtName;
    private TextView txtResult;
    private Button btnConfirm;
    private UserContract.Presenter presenter;

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

        edtId = findViewById(R.id.edtId);
        edtName = findViewById(R.id.edtName);
        btnConfirm = findViewById(R.id.btnConfirm);
        txtResult = findViewById(R.id.txtResult);

        presenter = new UserPresenter();
        initView();
    }

    @Override
    public void showUser(User user) {
        txtResult.setText(String.format("Id: %d, Name: %s", user.getId(), user.getName()));
    }

    @Override
    public void showError(String error) {
        txtResult.setText(error);
    }

    void initView() {
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Long id = Long.parseLong(String.valueOf(edtId.getText()));
                String name = String.valueOf(edtName.getText());
                presenter.loadParam(MainActivity.this, new User(id, name));
                presenter.confirm();
            }
        });
    }
}