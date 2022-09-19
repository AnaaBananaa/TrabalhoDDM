package com.example.t1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.t1.apresentador.CadastroActivityApresentador;
import com.example.t1.viewInterface.VisaoCadastro;

public class CadastroActivity extends AppCompatActivity implements VisaoCadastro {

    private CadastroActivityApresentador apresentador = new CadastroActivityApresentador(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        Button btnCadastrar = findViewById(R.id.btnCadastrar);
        EditText editTextNome = findViewById(R.id.etNome);
        EditText editTextEmail = findViewById(R.id.etEmail);
        EditText editTextSenha = findViewById(R.id.etSenhaCadastro);
        TextView textViewLogin = findViewById(R.id.txtLogin);

        btnCadastrar.setOnClickListener(view -> apresentador.onInsertUser(editTextEmail.getText().toString(), editTextSenha.getText().toString(), editTextNome.getText().toString()));
        textViewLogin.setOnClickListener(view -> apresentador.onNavToLogin());
    }

    @Override
    public void onNavToActivity(Class classe) {
        Intent intent = new Intent(this, classe);
        this.startActivity(intent);
    }

    @Override
    public void onShowToast(String mesage) {
        Toast.makeText(this, mesage, Toast.LENGTH_SHORT).show();
    }
}