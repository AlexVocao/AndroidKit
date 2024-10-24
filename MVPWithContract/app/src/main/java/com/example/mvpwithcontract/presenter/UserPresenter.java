package com.example.mvpwithcontract.presenter;

import com.example.mvpwithcontract.contract.UserContract;
import com.example.mvpwithcontract.model.User;
import com.example.mvpwithcontract.view.MainActivity;

public class UserPresenter extends UserContract.Presenter<MainActivity> {
    private MainActivity view;
    private User user;
    public UserPresenter() {
    }
    
    @Override
    public void loadParam(MainActivity view, User user) {
        this.view = view;
        this.user = user;
    }

    @Override
    public void confirm() {
        if (user.getId() != 0 && !user.getName().isEmpty()) {
            view.showUser(user);
        } else {
            view.showError("Invalid user");
        }
    }
}
