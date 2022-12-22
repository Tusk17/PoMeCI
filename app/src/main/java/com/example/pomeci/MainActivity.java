package com.example.pomeci;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;


public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }


    public void toAccess(View view) {
        Intent intent = new Intent(this, access.class);
        startActivity(intent);
    }
    public void toRegistrar(View view) {
        Intent intent = new Intent(this, registrar.class);
        startActivity(intent);
    }




}