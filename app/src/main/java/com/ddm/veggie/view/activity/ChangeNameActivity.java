package com.ddm.veggie.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.ddm.veggie.R;
import com.ddm.veggie.apresentador.ChangeNameActivityApresentador;
import com.ddm.veggie.contrato.ContratoChangeName;
import com.ddm.veggie.view.components.VeggieButton;
import com.ddm.veggie.view.components.VeggieEditText;

public class ChangeNameActivity extends AppCompatActivity implements ContratoChangeName.ContratoChangeNameView {

    //Presenter
    private ContratoChangeName.ContratoChangeNamePresenter PRESENTER;

    //Components
    private TextView tvNameAlert;
    private VeggieButton vbSave;
    private VeggieEditText vetChangeName;
    private Toolbar toolbar;

    public ChangeNameActivity() {
        PRESENTER = new ChangeNameActivityApresentador(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_name);
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
        tvNameAlert = findViewById(R.id.change_name_tv_name_alert);
        vbSave = findViewById(R.id.change_name_vb_save);
        vetChangeName = findViewById(R.id.change_name_vet_name);
        toolbar = findViewById(R.id.change_name_toolbar);

        //Actions
        toolbar.setNavigationOnClickListener(v -> navToSettings());
        vbSave.setOnClickListener(click -> PRESENTER.changeName(vetChangeName.getText().toString()));

        vetChangeName.setOnClickListener(action -> {
            if (vetChangeName.isWrong()) {
                tvNameAlert.setText("");
                vetChangeName.disableWrong();
                vetChangeName.refreshDrawableState();
            }
        });

        vetChangeName.setOnFocusChangeListener((view, b) -> {
            if(b) {
                if (vetChangeName.isWrong()) {
                    tvNameAlert.setText("");
                    vetChangeName.disableWrong();
                    vetChangeName.refreshDrawableState();
                }
            }
        });
    }

    public void navToSettings() {
        this.finish();
    }

    @Override
    public void saveSucess() {
        navToSettings();
    }

    @Override
    public void saveFailed() {
        tvNameAlert.setText(getResources().getText(R.string.alert_empty_name));
        vetChangeName.enableWrong();
        vetChangeName.refreshDrawableState();
    }
}