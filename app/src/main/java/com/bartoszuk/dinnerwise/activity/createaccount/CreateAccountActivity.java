package com.bartoszuk.dinnerwise.activity.createaccount;

/**
 * Created by Maria Bartoszuk on 18/02/2017.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.bartoszuk.dinnerwise.R;
import com.bartoszuk.dinnerwise.activity.login.LoginActivity;

public class CreateAccountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
    }

    public void goToLogin(View view) {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }
}