package com.ddm.veggie.view.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.ddm.veggie.R;
import com.ddm.veggie.apresentador.SignInActivityApresentador;
import com.ddm.veggie.contrato.ContratoSignIn;
import com.ddm.veggie.view.components.LoadingDialog;
import com.ddm.veggie.view.components.VeggieButton;
import com.ddm.veggie.view.components.VeggieEditText;

public class SignInActivity extends AppCompatActivity implements ContratoSignIn.ContratoSignInView {

    //Presenter
    private final ContratoSignIn.ContratoSignInPresenter PRESENTER;

    //Components
    private TextView tvEmailAlert;
    private TextView tvPasswordAlert;
    private VeggieButton vbEnter;
    private VeggieEditText vetEmail;
    private VeggieEditText vetPassword;
    private final LoadingDialog LOADING_DIALOG;

    public SignInActivity() {
        PRESENTER = new SignInActivityApresentador(this);
        LOADING_DIALOG = new LoadingDialog("Conectando...");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        //Configure statusbar
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                window.setStatusBarColor(getResources().getColor(R.color.theme_green_100, getTheme()));
            }
        }

        //Initiaize Components
        tvEmailAlert = findViewById(R.id.sign_in_tv_email_alert);
        tvPasswordAlert = findViewById(R.id.sign_in_tv_password_alert);
        TextView tv = findViewById(R.id.sign_in_tv_sign_up);
        vbEnter = findViewById(R.id.sign_in_vb_enter);
        vetEmail = findViewById(R.id.sign_in_vet_email);
        vetPassword = findViewById(R.id.sign_in_vet_password);

        //Actions
        tv.setOnClickListener(view -> navToSignUp());

        vbEnter.setOnClickListener(view -> {
            LOADING_DIALOG.show(getSupportFragmentManager(), LoadingDialog.TAG);
            PRESENTER.signIn(vetEmail.getText().toString(), vetPassword.getText().toString());
        });

        vetEmail.setOnClickListener(action -> {
            if (vetEmail.isWrong()) {
                tvEmailAlert.setText("");
                vetEmail.disableWrong();
                vetEmail.refreshDrawableState();
            }
        });

        vetEmail.setOnFocusChangeListener((view, b) -> {
            if(b) {
                if (vetEmail.isWrong()) {
                    tvEmailAlert.setText("");
                    vetEmail.disableWrong();
                    vetEmail.refreshDrawableState();
                }
            }
        });

        vetPassword.setOnClickListener(action -> {
            if (vetPassword.isWrong()) {
                tvPasswordAlert.setText("");
                vetPassword.disableWrong();
                vetPassword.refreshDrawableState();
            }
        });

        vetPassword.setOnFocusChangeListener((view, b) -> {
            if(b) {
                if (vetPassword.isWrong()) {
                    tvPasswordAlert.setText("");
                    vetPassword.disableWrong();
                    vetPassword.refreshDrawableState();
                }
            }
        });
    }

    public void navToSignUp() {
        Intent intent = new Intent(this, SignUpActivity.class);
        this.startActivity(intent);
    }

    @Override
    public void closeLoadingDialog() {
        LOADING_DIALOG.dismiss();
    }

    @Override
    public void emptyEmail() {
        tvEmailAlert.setText(getResources().getText(R.string.alert_empty_email));
        vetEmail.enableWrong();
        vetEmail.refreshDrawableState();
    }

    @Override
    public void emptyPassword() {
        tvPasswordAlert.setText(getResources().getText(R.string.alert_empty_password));
        vetPassword.enableWrong();
        vetPassword.refreshDrawableState();
    }

    @Override
    public void signInFailed() {
        tvEmailAlert.setText(getResources().getText(R.string.alert_wrong_email_and_or_password));
        vetEmail.enableWrong();
        vetEmail.refreshDrawableState();
    }

    @Override
    public void signInSucess() {
        Intent intent = new Intent(this, AppActivity.class);
        this.startActivity(intent);
        this.finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PRESENTER.destroyView();
    }
}