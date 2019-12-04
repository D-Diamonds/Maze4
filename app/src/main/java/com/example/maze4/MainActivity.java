package com.example.maze4;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity implements View.OnClickListener{

    private MazeView mazeView;

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.reset) {
            this.mazeView.reset();
            System.out.println("Resetting maze...");
        }
        if (v.getId() == R.id.randomize) {
            this.mazeView.randomize();
            System.out.println("Randomizing maze...");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.mazeView = findViewById(R.id.mazeView);
        findViewById(R.id.randomize).setOnClickListener(this);
        findViewById(R.id.reset).setOnClickListener(this);
    }
}
