package com.example.daniel.eksperiment1.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.daniel.eksperiment1.R;

public class FronterLoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fronter_login);
    }

    public void loginFronter(View view) {
        EditText brugernavnTextView = (EditText) findViewById(R.id.brugernavnFronter);
        EditText passwordTextView = (EditText) findViewById(R.id.passwordFronter);

        Intent intent = new Intent(this, FronterActivity.class);
        intent.putExtra("brugernavn", brugernavnTextView.getText().toString());
        intent.putExtra("password", passwordTextView.getText().toString());
        startActivity(intent);
    }
}
