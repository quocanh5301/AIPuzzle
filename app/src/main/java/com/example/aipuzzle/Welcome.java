package com.example.aipuzzle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Welcome extends AppCompatActivity implements View.OnClickListener {

    Button level3, level4, level5;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        level3 = findViewById(R.id.level3);
        level4 = findViewById(R.id.level4);
        level5 = findViewById(R.id.level5);
        level3.setOnClickListener(this);
        level4.setOnClickListener(this);
        level5.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.level3:
                intent = new Intent(Welcome.this, MainActivity.class);
                intent.putExtra("level", 3);
                startActivity(intent);
                break;
            case R.id.level4:
                intent = new Intent(Welcome.this, MainActivity.class);
                intent.putExtra("level", 4);
                startActivity(intent);
                break;
            case R.id.level5:
                intent = new Intent(Welcome.this, MainActivity.class);
                intent.putExtra("level", 5);
                startActivity(intent);
                break;
        }
    }
}