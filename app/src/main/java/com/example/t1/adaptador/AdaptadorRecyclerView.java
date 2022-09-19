package com.example.t1.adaptador;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.t1.R;
import com.example.t1.modelo.Receita;

import org.w3c.dom.Text;

import java.util.List;

public class AdaptadorRecyclerView extends RecyclerView.Adapter<AdaptadorRecyclerView.ViewHolder> {

    private List<Receita> receitas;
    private Context context;

    public AdaptadorRecyclerView(List<Receita> receitas, Context context){
        this.receitas = receitas;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_list,parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.imageView.setImageResource(R.drawable.sakura);
        holder.texto.setText(receitas.get(position).getTitulo());
    }

    @Override
    public int getItemCount() {
        return receitas.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView imageView;
        public TextView texto;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageReceita);
            texto = itemView.findViewById(R.id.tituloReclycerView);
            imageView.setOnClickListener(e -> Toast.makeText(itemView.getContext(), "Wild", Toast.LENGTH_SHORT).show());
        }
    }
}
