package com.example.t1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.media.Image;
import android.os.Bundle;

import com.example.t1.adaptador.AdaptadorRecyclerView;
import com.example.t1.modelo.Receita;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        List<Receita> receitas = new ArrayList<>();
        receitas.add(new Receita("Salada Nutritiva", new File("sakura.jpg"), "Salada sem ingredientes de origem animal, mas com todos os nutrientes que precismaos no dia"));
        receitas.add(new Receita("Sorvete vegano", new File("sakura.jpg"), "Aprenda a fazer um sorvete delicioso usando leite vegetal"));
        recyclerView.setAdapter(new AdaptadorRecyclerView(receitas, this));
    }
}