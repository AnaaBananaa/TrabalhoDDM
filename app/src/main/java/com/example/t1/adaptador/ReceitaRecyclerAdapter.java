package com.example.t1.adaptador;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.t1.R;
import com.example.t1.modelo.Receita;

import java.io.File;
import java.io.InputStream;
import java.util.List;

public class ReceitaRecyclerAdapter extends RecyclerView.Adapter<ReceitaRecyclerAdapter.ViewHolder> {
    private List<Receita> receitas;
    private Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout mainLayout;
        TextView txtTitle;
        ImageView image;
        ImageButton btnOpen;

        public ViewHolder(View itemView) {
            super(itemView);
            mainLayout = itemView.findViewById(R.id.recipe_rv_main_layout);
            txtTitle = itemView.findViewById(R.id.recipe_rv_txt_title);
            image  = itemView.findViewById(R.id.recipe_rv_image);
            btnOpen = itemView.findViewById(R.id.recipe_rv_btn_open);
            btnOpen.setOnClickListener(click -> {
                Toast.makeText(itemView.getContext(), "Função de abrir a receita", Toast.LENGTH_SHORT).show();
            });
        }
    }

    public ReceitaRecyclerAdapter(List<Receita> receitas, Context context) {
        this.receitas = receitas;
        this.context = context;
    }

    @NonNull
    @Override
    public ReceitaRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_recipe_recycler_view, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.txtTitle.setText(receitas.get(position).getTitulo());
//        int resId = holder.itemView.getResources().getIdentifier(receitas.get(position).getCaminhoImagem(), "drawable", context.getPackageName());
        holder.image.setImageURI(Uri.parse(receitas.get(position).getCaminhoImagem()));
    }

    @Override
    public int getItemCount() {
        return receitas.size();
    }
}
