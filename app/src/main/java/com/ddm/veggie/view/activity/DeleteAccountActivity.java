package com.ddm.veggie.view.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.ddm.veggie.R;
import com.ddm.veggie.apresentador.ChangeNameActivityApresentador;
import com.ddm.veggie.apresentador.DeleteAccountActivityApresentador;
import com.ddm.veggie.contrato.ContratoChangeName;
import com.ddm.veggie.contrato.ContratoDeleteAccount;
import com.ddm.veggie.view.components.LoadingDialog;
import com.ddm.veggie.view.components.VeggieButton;
import com.ddm.veggie.view.components.VeggieEditText;

public class DeleteAccountActivity extends AppCompatActivity implements ContratoDeleteAccount.ContratoDeleteAccountView {

    //Presenter
    private ContratoDeleteAccount.ContratoDeleteAccountPresenter PRESENTER;

    //Components
    private Toolbar toolbar;
    private VeggieButton vbDelete;
    private final LoadingDialog LOADING_DIALOG;

    public DeleteAccountActivity() {
        PRESENTER = new DeleteAccountActivityApresentador(this);
        LOADING_DIALOG = new LoadingDialog("Deletando...");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_account);
        //Configure statusbar
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                window.setStatusBarColor(getResources().getColor(R.color.white, getTheme()));
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }
        }

        //Initialize Components
        toolbar = findViewById(R.id.delete_account_toolbar);
        vbDelete = findViewById(R.id.delete_account_vb_delete);

        //Actions
        toolbar.setNavigationOnClickListener(v -> navToSettings());
        vbDelete.setOnClickListener(click -> {
            LOADING_DIALOG.show(getSupportFragmentManager(), LoadingDialog.TAG);
            PRESENTER.deleteAccount();
        });

    }

    public void navToSettings() {
        this.finish();
    }

    public void navToSignIn() {
        Intent intent = new Intent(this, SignInActivity.class);
        this.PRESENTER.destroyView();
        this.startActivity(intent);
        this.finish();
    }

    @Override
    public void deleteSucess() {
        LOADING_DIALOG.dismiss();
        navToSignIn();
    }

    @Override
    public void deleteFailed() {
        LOADING_DIALOG.dismiss();
    }
}