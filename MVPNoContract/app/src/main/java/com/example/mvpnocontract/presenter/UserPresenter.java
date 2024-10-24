package com.example.mvpnocontract.presenter;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mvpnocontract.model.UserModel;
import com.example.mvpnocontract.view.UserView;

public class UserPresenter {
    private UserModel userModel;
    private UserView userView;

    public UserPresenter(UserModel userModel, UserView userView) {
        this.userModel = userModel;
        this.userView = userView;
    }

    public void loadUser() {
        if (userModel != null) {
            userView.showUser(userModel);
        } else {
            userView.showError("User not found");
        }
    }
}
