package com.ddm.veggie.view.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.ddm.veggie.R;
import com.ddm.veggie.apresentador.SplashActivityApresentador;
import com.ddm.veggie.contrato.ContratoSplash;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity implements ContratoSplash.ContratoSplashView {

    private final ContratoSplash.ContratoSplashPresenter presenter = new SplashActivityApresentador(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                window.setStatusBarColor(getResources().getColor(R.color.theme_green_100, getTheme()));
            }
        }

        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                presenter.verifyCurrentUser();
            }
        };
        new Timer().schedule(timerTask, 2000);
    }

    @Override
    public void onNavToApp() {
        Intent intent = new Intent(this, AppActivity.class);
        this.startActivity(intent);
        this.finish();
    }

    @Override
    public void onNavToSignIn() {
        Intent intent = new Intent(this, SignInActivity.class);
        this.startActivity(intent);
        this.finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.destroyView();
    }
}