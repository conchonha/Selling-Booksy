package com.example.mobileappbook.ui.start_screen;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mobileappbook.R;
import com.example.mobileappbook.ui.login_page.LoginActivity;
import com.example.mobileappbook.ui.register_page.RegisterActivity;

public class StartScreen extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startscreen);
        listenerOnclick();
    }

    private void listenerOnclick() {
        findViewById(R.id.mCardRegister).setOnClickListener(this::onClick);
        findViewById(R.id.mCardLogin).setOnClickListener(this::onClick);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.mCardRegister :
                startActivity(new Intent(StartScreen.this, RegisterActivity.class));
                break;
            case R.id.mCardLogin:
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                break;
        }
    }
}