package com.alabs.onegame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;


public class MainActivity extends AppCompatActivity {

    int playerPoints = 0;
    int botPoints = 0;

    static class TwoCubes{
        int cube1;
        int cube2;
        public TwoCubes(int cube1, int cube2) {
            this.cube1 = cube1;
            this.cube2 = cube2;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView textPoints = findViewById(R.id.textViewPoints);
        textPoints.setText("Бот: " + botPoints + " / Игрок: " + playerPoints);
    }

    public void showCubes(int cube1, int cube2, int cube3, int cube4) {
        ImageView imageCube1 = findViewById(R.id.imageView);
        ImageView imageCube2 = findViewById(R.id.imageView2);
        ImageView imageCube3 = findViewById(R.id.imageView3);
        ImageView imageCube4 = findViewById(R.id.imageView4);
        int[] cubes =  {cube1, cube2, cube3, cube4};
        ImageView[] imageCubes = {imageCube1, imageCube2, imageCube3, imageCube4};
        for (int i = 0; i < cubes.length; i++) {
            int qurCube = cubes[i];
            ImageView imageCubesQ = imageCubes[i];
            switch (qurCube) {
                case 1:
                    imageCubesQ.setImageResource(R.drawable.cubew1);
                    break;
                case 2:
                    imageCubesQ.setImageResource(R.drawable.cubew2);
                    break;
                case 3:
                    imageCubesQ.setImageResource(R.drawable.cubew3);
                    break;
                case 4:
                    imageCubesQ.setImageResource(R.drawable.cubew4);
                    break;
                case 5:
                    imageCubesQ.setImageResource(R.drawable.cubew5);
                    break;
                case 6:
                    imageCubesQ.setImageResource(R.drawable.cubew6);
                    break;
            }
        }
    }

    public TwoCubes random2Cubes() {
        Random rand = new Random();
        int cube1 = rand.nextInt((6 - 1) + 1) + 1;
        int cube2 = rand.nextInt((6 - 1) + 1) + 1;
        return new TwoCubes(cube1, cube2);
    }

    public void cubePoints() {
        TwoCubes botCubes = random2Cubes();
        TwoCubes playerCubes = random2Cubes();
        int botCube1 = botCubes.cube1;
        int botCube2 = botCubes.cube2;
        int playerCube1 = playerCubes.cube1;
        int playerCube2 = playerCubes.cube2;

        showCubes(botCube1, botCube2, playerCube1, playerCube2);
        while (botCube1 == botCube2) {
            botCubes = random2Cubes();
            botCube1 = botCubes.cube1;
            botCube2 = botCubes.cube2;
            showCubes(botCube1, botCube2, playerCube1, playerCube2);
        }
        while (playerCube1 == playerCube2) {
            playerCubes = random2Cubes();
            playerCube1 = playerCubes.cube1;
            playerCube2 = playerCubes.cube2;
            showCubes(botCube1, botCube2, playerCube1, playerCube2);
        }

        botPoints += botCube1 + botCube2;
        playerPoints += playerCube1 + playerCube2;

        TextView textPoints = findViewById(R.id.textViewPoints);
        textPoints.setText("Бот: " + botPoints + " / Игрок: " + playerPoints);
    }

    public void throwCubes(View view) {
        cubePoints();
        if ((playerPoints >= 100) || (botPoints >= 100)) {
            Intent resultScreen = new Intent(MainActivity.this, ResultActivity.class);
            if (playerPoints >= 100) {
                if (botPoints >= 100) {
                    resultScreen.putExtra("type", "draw");
                }
                else {
                    resultScreen.putExtra("type", "win");
                }
            }
            else if (botPoints >= 100) {
                resultScreen.putExtra("type", "loose");
            }
            startActivity(resultScreen);
        }
    }
}