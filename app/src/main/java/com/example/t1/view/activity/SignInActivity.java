package com.example.t1.view.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.t1.R;
import com.example.t1.apresentador.SignInActivityApresentador;
import com.example.t1.contrato.ContratoSignIn;

public class SignInActivity extends AppCompatActivity implements ContratoSignIn.ContratoSignInView {

    private final ContratoSignIn.ContratoSignInPresenter presenter = new SignInActivityApresentador(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        //Components
        EditText edEmail = findViewById(R.id.sign_in_ed_email);
        EditText edPassword = findViewById(R.id.sign_in_et_password);
        Button btnEnter = findViewById(R.id.sign_in_btn_enter);
        TextView txtSignUp = findViewById(R.id.sign_in_txt_sign_up);

        //Actions
        btnEnter.setOnClickListener(view -> presenter.onVerifyUser(edEmail.getText().toString(), edPassword.getText().toString()));
        txtSignUp.setOnClickListener(view -> onNavToSignUp());
    }

    @Override
    public void onShowToast(String mesage) {
        Toast.makeText(this, mesage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNavToSignUp() {
        Intent intent = new Intent(this, SignUpActivity.class);
        this.startActivity(intent);
    }

    @Override
    public void onNavToApp() {
        Intent intent = new Intent(this, AppActivity.class);
        this.startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.destroyView();
    }
}