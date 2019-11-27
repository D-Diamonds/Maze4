package com.example.maze4;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {

    private MazeView mazeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.mazeView = findViewById(R.id.mazeView);
    }
}
