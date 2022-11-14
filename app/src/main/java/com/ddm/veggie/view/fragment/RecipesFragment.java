package com.ddm.veggie.view.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.ddm.veggie.R;
import com.ddm.veggie.adaptador.ReceitaRecyclerAdapter;
import com.ddm.veggie.apresentador.RecipesFragmentApresentador;
import com.ddm.veggie.contrato.ContratoRecipes;
import com.ddm.veggie.view.components.FilterDialog;

import java.util.HashMap;
import java.util.Map;

public class RecipesFragment extends Fragment implements ContratoRecipes.ContratoRecipesView, FilterDialog.FilterDialogFragmentListener {
    private final ContratoRecipes.ContratoRecipesPresenter PRESENTER;

    //Variables
    private Map<String, Object> filters;

    //Components
    private View view;
    private EditText edSearch;
    private TextView txtFilter;
    private ImageButton btnSearch;
    private RecyclerView recyclerView;
    private FilterDialog filterDialog;


    public RecipesFragment() {
        filters = new HashMap<>();
        filters.put("filterOrder", null);
        PRESENTER = new RecipesFragmentApresentador(this);
        filterDialog = new FilterDialog(this, filters);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PRESENTER.buscaReceitas("", filters);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Initialize Components
        view = inflater.inflate(R.layout.fragment_recipes, container, false);
        edSearch = view.findViewById(R.id.recipes_ed_search);
        btnSearch = view.findViewById(R.id.recipes_btn_search);
        txtFilter = view.findViewById(R.id.recipes_txt_filter);
        recyclerView = view.findViewById(R.id.recipes_recycler_view);

        //Configure Components
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(new ReceitaRecyclerAdapter(PRESENTER.getReceitas()));

        //Actions
        btnSearch.setOnClickListener(click-> {
            PRESENTER.buscaReceitas(edSearch.getText().toString(), filters);
            recyclerView.setAdapter(new ReceitaRecyclerAdapter(PRESENTER.getReceitas()));
            edSearch.clearFocus();
            InputMethodManager in = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            in.hideSoftInputFromWindow(edSearch.getWindowToken(), 0);
        });
        edSearch.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                PRESENTER.buscaReceitas(edSearch.getText().toString(), filters);
                recyclerView.setAdapter(new ReceitaRecyclerAdapter(PRESENTER.getReceitas()));
                edSearch.clearFocus();
                InputMethodManager in = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                in.hideSoftInputFromWindow(edSearch.getWindowToken(), 0);
                return true;
            }
            return false;
        });
        txtFilter.setOnClickListener(click -> openDialog());
        return view;
    }

    private void openDialog() {
        filterDialog.show(getChildFragmentManager(), filterDialog.TAG);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        PRESENTER.destroyView();
    }

    @Override
    public void onSaveClicked() {
        PRESENTER.buscaReceitas(edSearch.getText().toString(), filters);
        recyclerView.setAdapter(new ReceitaRecyclerAdapter(PRESENTER.getReceitas()));
    }
}