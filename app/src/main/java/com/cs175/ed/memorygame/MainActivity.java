package com.cs175.ed.memorygame;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void onPlayClick(View v){
        SharedPreferences settings = getSharedPreferences("pref", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("positions", "");
        editor.commit();
        Intent intent = new Intent(MainActivity.this, com.cs175.ed.memorygame.GameActivity.class);
        startActivity(intent);
    }

    public void onResumeClick(View v){
        Intent intent = new Intent(MainActivity.this, com.cs175.ed.memorygame.GameActivity.class);
        startActivity(intent);
    }

    public void onRulesClick(View v){
        Intent intent = new Intent(MainActivity.this, com.cs175.ed.memorygame.RulesActivity.class);
        startActivity(intent);
    }
}
