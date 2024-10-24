package com.example.mvpwithcontract.contract;

import com.example.mvpwithcontract.model.User;

public interface UserContract {
    interface View {
        void showUser(User user);
        void showError(String error);
    }
    public abstract class Presenter<View> {
        public abstract void loadParam(View view, User user);
        public abstract void confirm();
    }
}
