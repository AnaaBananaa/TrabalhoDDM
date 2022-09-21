package com.example.t1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.t1.DAO.ManterReceita;
import com.example.t1.adaptador.AdaptadorRecyclerView;
import com.example.t1.apresentador.HomeActivityApresentador;
import com.example.t1.modelo.Receita;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private HomeActivityApresentador apresentador = new HomeActivityApresentador(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        ImageView imagemLupa = findViewById(R.id.buscar);
        EditText editTextBusca = findViewById(R.id.search);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        ManterReceita.getInstance().insertReceita(new Receita("Salada Nutritiva", new File("sakura.jpg"), "Salada sem ingredientes de origem animal, mas com todos os nutrientes que precismaos no dia"));
        ManterReceita.getInstance().insertReceita(new Receita("Sorvete vegano", new File("sakura.jpg"), "Aprenda a fazer um sorvete delicioso usando leite vegetal"));
        recyclerView.setAdapter(new AdaptadorRecyclerView(ManterReceita.getInstance().getReceitas(), this));

        imagemLupa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerView.setAdapter((new AdaptadorRecyclerView(apresentador.buscaReceitas(editTextBusca.getText().toString()), view.getContext())));
            }
        });
    }
}