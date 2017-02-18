package com.bartoszuk.dinnerwise.activity.login;

/**
 * Created by Maria Bartoszuk on 18/02/2017.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.bartoszuk.dinnerwise.R;
import com.bartoszuk.dinnerwise.activity.createaccount.CreateAccountActivity;
import com.google.android.gms.common.SignInButton;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        SignInButton signInWithGoogleButton = (SignInButton) findViewById(R.id.sign_in_button);
        signInWithGoogleButton.setSize(SignInButton.SIZE_WIDE);
    }

    public void goToCreateAccount(View view) {
        startActivity(new Intent(this, CreateAccountActivity.class));
        finish();
    }
}
