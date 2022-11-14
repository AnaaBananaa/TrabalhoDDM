package com.ddm.veggie.view.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.ddm.veggie.R;
import com.ddm.veggie.apresentador.SignUpActivityApresentador;
import com.ddm.veggie.contrato.ContratoSignUp;
import com.ddm.veggie.view.components.LoadingDialog;
import com.ddm.veggie.view.components.VeggieButton;
import com.ddm.veggie.view.components.VeggieEditText;

public class SignUpActivity extends AppCompatActivity implements ContratoSignUp.ContratoSignUpView {

    private final ContratoSignUp.ContratoSignUpPresenter PRESENTER;

    //Components
    private TextView tvEmailAlert;
    private TextView tvNameAlert;
    private TextView tvPasswordAlert;
    private VeggieButton vbCreateAccount;
    private VeggieEditText vetEmail;
    private VeggieEditText vetName;
    private VeggieEditText vetPassword;
    private final LoadingDialog LOADING_DIALOG;

    public SignUpActivity() {
        PRESENTER = new SignUpActivityApresentador(this);
        LOADING_DIALOG = new LoadingDialog("Criando...");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //Configure statusbar
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                window.setStatusBarColor(getResources().getColor(R.color.theme_green_100, getTheme()));
            }
        }

        //Components
        tvEmailAlert = findViewById(R.id.sign_up_tv_email_alert);
        tvNameAlert = findViewById(R.id.sign_up_tv_name_alert);
        tvPasswordAlert = findViewById(R.id.sign_up_tv_password_alert);
        TextView tvSignIn = findViewById(R.id.sign_up_tv_sign_in);
        vbCreateAccount = findViewById(R.id.sign_up_btn_create_account);
        vetEmail = findViewById(R.id.sign_up_vet_email);
        vetName = findViewById(R.id.sign_up_vet_name);
        vetPassword = findViewById(R.id.sign_up_vet_password);

        //Actions
        tvSignIn.setOnClickListener(view -> navToSignIn());

        vbCreateAccount.setOnClickListener(view -> {
            LOADING_DIALOG.show(getSupportFragmentManager(), LoadingDialog.TAG);
            PRESENTER.signUp(vetEmail.getText().toString(), vetPassword.getText().toString(), vetName.getText().toString());
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

        vetName.setOnClickListener(action -> {
            if (vetName.isWrong()) {
                tvNameAlert.setText("");
                vetName.disableWrong();
                vetName.refreshDrawableState();
            }
        });

        vetName.setOnFocusChangeListener((view, b) -> {
            if(b) {
                if (vetName.isWrong()) {
                    tvNameAlert.setText("");
                    vetName.disableWrong();
                    vetName.refreshDrawableState();
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

    public void navToSignIn() {
        Intent intent = new Intent(this, SignInActivity.class);
        this.startActivity(intent);
        this.finish();
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
    public void emptyName() {
        tvNameAlert.setText(getResources().getText(R.string.alert_empty_email));
        vetName.enableWrong();
        vetName.refreshDrawableState();
    }

    @Override
    public void emptyPassword() {
        tvPasswordAlert.setText(getResources().getText(R.string.alert_empty_email));
        vetPassword.enableWrong();
        vetPassword.refreshDrawableState();
    }

    @Override
    public void signUpFailed() {
        tvEmailAlert.setText(getResources().getText(R.string.alert_email_already_in_use));
        vetEmail.enableWrong();
        vetEmail.refreshDrawableState();
    }

    @Override
    public void signUpSucess() {
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