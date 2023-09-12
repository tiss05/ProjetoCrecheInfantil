package com.example.projcrech;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Show loading when inicialize the app
 */
public class LoadAppActivity extends AppCompatActivity {

    private ProgressBar progressBarAnimation;
    private ObjectAnimator progressAnimator;
    public static final String PREFERENCES_APP = "PREFERENCES_APP";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_app);
        init();

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            //UserUtils user = new UserUtils(getApplicationContext());
            //if(user.getID().equals("")){
                LoginActivity.startMainActivity(getApplicationContext());

            //}
            //finish();
        }, 2000);

        progressAnimator.setDuration(2000);
        progressAnimator.start();

        progressAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                progressBarAnimation.setVisibility(View.VISIBLE);
            }
        });
    }

    private void init(){
        progressBarAnimation = findViewById(R.id.progressBar);
        progressAnimator = ObjectAnimator.ofInt(progressBarAnimation, "progress", 0,100);
    }
}