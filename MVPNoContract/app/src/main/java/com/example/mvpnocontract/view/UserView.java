package com.example.mvpnocontract.view;

import com.example.mvpnocontract.model.UserModel;

public interface UserView {
    void showUser(UserModel userModel);

    void showError(String error);
}
