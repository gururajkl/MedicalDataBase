package com.example.demoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    Animation zoom, rotateAnimation;
    ImageView img, pill;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        zoom = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom);
        img = findViewById(R.id.image);
        pill = findViewById(R.id.pill);
        img.startAnimation(zoom);

        rotateAnimation();

        Handler h = new Handler();
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(getApplicationContext(), MainActivity2.class);
                startActivity(i);
                finish();
            }
        }, 4000);



    }

    private void rotateAnimation() {
        rotateAnimation = AnimationUtils.loadAnimation(this, R.anim.rotate);
        pill.startAnimation(rotateAnimation);
    }
}