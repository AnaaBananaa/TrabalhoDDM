package com.ddm.veggie.view.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ddm.veggie.R;
import com.ddm.veggie.apresentador.SettingsFragmentApresentador;
import com.ddm.veggie.contrato.ContratoSettings;
import com.ddm.veggie.view.activity.ChangeNameActivity;
import com.ddm.veggie.view.activity.DeleteAccountActivity;
import com.ddm.veggie.view.activity.SignInActivity;
import com.ddm.veggie.view.components.VeggieConfigButton;

public class SettingsFragment extends Fragment implements ContratoSettings.ContratoSettingsView {

    //Presenter
    private final ContratoSettings.ContratoSettingsPresenter PRESENTER;

    //Components
    VeggieConfigButton vbChangeName;
    VeggieConfigButton vbDeleteAccount;
    VeggieConfigButton vbSignOut;

    public SettingsFragment() {
        PRESENTER = new SettingsFragmentApresentador(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        //Components
        vbChangeName = view.findViewById(R.id.settings_vb_change_name);
        vbDeleteAccount = view.findViewById(R.id.settings_vb_delete_account);
        vbSignOut = view.findViewById(R.id.settings_vb_sign_out);

        //Actions
        vbChangeName.setOnClickListener(click -> navToChangeName());
        vbDeleteAccount.setOnClickListener(click -> navToDeleteAccount());
        vbSignOut.setOnClickListener(click -> PRESENTER.signOut());
        return view;
    }

    public void navToChangeName() {
        Intent intent = new Intent(this.getActivity(), ChangeNameActivity.class);
        this.startActivity(intent);
    }

    public void navToDeleteAccount() {
        Intent intent = new Intent(this.getActivity(), DeleteAccountActivity.class);
        this.startActivity(intent);
    }

    @Override
    public void onNavToSignIn() {
        Intent intent = new Intent(this.getActivity(), SignInActivity.class);
        this.PRESENTER.destroyView();
        this.startActivity(intent);
        getActivity().finish();
    }
}