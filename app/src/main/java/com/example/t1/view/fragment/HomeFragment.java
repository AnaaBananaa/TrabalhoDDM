package com.example.t1.view.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.t1.DAO.ManterReceita;
import com.example.t1.R;
import com.example.t1.adaptador.ReceitaRecyclerAdapter;
import com.example.t1.apresentador.HomeFragmentApresentador;
import com.example.t1.contrato.ContratoHome;

public class HomeFragment extends Fragment implements ContratoHome.ContratoHomeView {

    private ContratoHome.ContratoHomePresenter apresentador = new HomeFragmentApresentador(this);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        //Components
        RecyclerView recyclerView = view.findViewById(R.id.home_recycler_view);
        EditText edSearch = view.findViewById(R.id.home_ed_search);
        ImageButton btnSearch = view.findViewById(R.id.home_btn_search);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new ReceitaRecyclerAdapter(ManterReceita.getInstance().getReceitas(), view.getContext()));

        //Actions
        btnSearch.setOnClickListener(click-> {
            recyclerView.setAdapter((new ReceitaRecyclerAdapter(apresentador.buscaReceitas(edSearch.getText().toString()), view.getContext())));
        });
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        apresentador.destroyView();
    }
}