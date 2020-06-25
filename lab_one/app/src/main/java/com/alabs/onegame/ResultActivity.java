package com.alabs.onegame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Intent screenIntent = getIntent();
        String resultText = screenIntent.getStringExtra("type");
        ImageView imageViewResult = findViewById(R.id.imageViewResult);
        switch (resultText) {
            case "win":
                imageViewResult.setImageResource(R.drawable.cube3d);
                resultText = "Победа";
                break;
            case "loose":
                imageViewResult.setImageResource(R.drawable.loose);
                resultText = "Проигрыш";
                break;
            case "draw":
                imageViewResult.setImageResource(R.drawable.draw);
                resultText = "Ничья";
                break;
        }
        TextView textViewResult = findViewById(R.id.textViewResult);
        textViewResult.setText("" + resultText);
    }

    public void toMain(View view) {
        Intent mainScreen = new Intent(ResultActivity.this, MainActivity.class);
        startActivity(mainScreen);
    }
}