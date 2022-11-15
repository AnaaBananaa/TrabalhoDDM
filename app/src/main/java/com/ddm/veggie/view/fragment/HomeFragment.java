package com.ddm.veggie.view.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ddm.veggie.DAO.ManterUsuario;
import com.ddm.veggie.R;
import com.ddm.veggie.adaptador.ReceitaRecyclerAdapter;
import com.ddm.veggie.apresentador.HomeFragmentApresentador;
import com.ddm.veggie.contrato.ContratoHome;
import com.ddm.veggie.modelo.Receita;
import com.ddm.veggie.modelo.ReceitaRecycler;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements ContratoHome.ContratoHomeView {
    private final ContratoHome.ContratoHomePresenter PRESENTER;

    //Components
    private View view;
    private TextView txtUsername;
    private LinearLayout withoutContentLayout;
    private ConstraintLayout withContentLayout;
    private RecyclerView withContentRecyclerView;
    private ReceitaRecyclerAdapter withContentRecyclerViewAdapter;

    public HomeFragment() {
        PRESENTER = new HomeFragmentApresentador(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PRESENTER.updateFavoriteRecipesContent();
        List<Receita> recipes = PRESENTER.getFavoriteRecipes();
        List<ReceitaRecycler> recipesRecycler = new ArrayList<>();
        for (Receita recipe: recipes) {
            recipesRecycler.add(new ReceitaRecycler(recipe.getFirebaseId(), recipe.getNome(), recipe.getDescricao(), recipe.getFavoriteCount(), true));
        }
        withContentRecyclerViewAdapter = new ReceitaRecyclerAdapter(recipesRecycler);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Variable
        PRESENTER.setView(this);

        //Initialize Components
        view = inflater.inflate(R.layout.fragment_home, container, false);
        txtUsername = view.findViewById(R.id.home_txt_username);
        withoutContentLayout = view.findViewById(R.id.home_without_content_layout);
        withContentLayout = view.findViewById(R.id.home_with_content_layout);
        withContentRecyclerView = view.findViewById(R.id.home_with_content_recycler_view);

        //Configure Components
        txtUsername.setText(PRESENTER.getUsername());
        withoutContentLayout.setVisibility(View.INVISIBLE);
        withContentRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        withContentRecyclerView.setAdapter(withContentRecyclerViewAdapter);
        PRESENTER.updateFrameContent();
        return view;
    }

    @Override
    public void showWithContent() {
        withContentLayout.setVisibility(View.VISIBLE);
        withoutContentLayout.setVisibility(View.GONE);
    }

    @Override
    public void showWithoutContent() {
        withContentLayout.setVisibility(View.GONE);
        withoutContentLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        PRESENTER.destroyView();
    }
}