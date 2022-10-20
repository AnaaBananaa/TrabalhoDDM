package com.example.t1.view.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.t1.R;
import com.example.t1.apresentador.SignUpActivityApresentador;
import com.example.t1.contrato.ContratoSignUp;

public class SignUpActivity extends AppCompatActivity implements ContratoSignUp.ContratoSignUpView {

    private final ContratoSignUp.ContratoSignUpPresenter presenter = new SignUpActivityApresentador(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //Components
        Button btnCreateAccount = findViewById(R.id.sign_up_btn_create_account);
        EditText edName = findViewById(R.id.sign_up_ed_name);
        EditText edEmail = findViewById(R.id.sign_up_ed_email);
        EditText edPassword = findViewById(R.id.sign_up_ed_password);
        TextView txtSignIn = findViewById(R.id.sign_up_txt_sign_in);

        //Actions
        btnCreateAccount.setOnClickListener(view -> presenter.onInsertUser(edEmail.getText().toString(), edPassword.getText().toString(), edName.getText().toString()));
        txtSignIn.setOnClickListener(view -> onNavToSignIn());
    }

    @Override
    public void onShowToast(String mesage) {
        Toast.makeText(this, mesage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNavToSignIn() {
        Intent intent = new Intent(this, SignInActivity.class);
        this.startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.destroyView();
    }
}