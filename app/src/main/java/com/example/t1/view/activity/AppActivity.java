package com.example.t1.view.activity;

import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.t1.DAO.ManterReceita;
import com.example.t1.R;
import com.example.t1.databinding.ActivityAppBinding;
import com.example.t1.modelo.Receita;
import com.example.t1.view.fragment.HomeFragment;
import com.example.t1.view.fragment.SettingsFragment;

import java.io.File;

public class AppActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityAppBinding binding = ActivityAppBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new HomeFragment());

        //POPULANDO BANCO DE DADOS PROVISORIO
        ManterReceita.getInstance().insertReceita(new Receita("Risoto de Cogumelos", Uri.parse("android.resource://"+getPackageName()+"/" + R.drawable.risoto_de_cogumelos).toString(), "Aprenda a preparar esse delicioso risoto, um prato perfeito para seu almoço e/ou jantar, muito bem acompanhado de uma salada"));
        ManterReceita.getInstance().insertReceita(new Receita("Tabule de Quinoa", Uri.parse("android.resource://"+getPackageName()+"/" + R.drawable.tabule_de_quinoa).toString(), "Aprenda a fazer essa deliciosa salada"));
        ManterReceita.getInstance().insertReceita(new Receita("Ratatouille", Uri.parse("android.resource://"+getPackageName()+"/" + R.drawable.ratatouille).toString(), "Aprenda a preparar esse prato delicioso, tão delicioso que mereceu uma animação em sua homenagem"));

        binding.appBottomNav.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.home:
                    replaceFragment(new HomeFragment());
                    return true;
                case R.id.settings:
                    replaceFragment(new SettingsFragment());
                    return true;
                default:
                    return false;
            }
        });
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.app_frame_layout, fragment);
        transaction.commit();
    }
}