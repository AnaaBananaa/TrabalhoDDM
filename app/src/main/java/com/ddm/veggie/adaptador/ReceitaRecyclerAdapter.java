package com.ddm.veggie.adaptador;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.ddm.veggie.DAO.ManterReceita;
import com.ddm.veggie.DAO.ManterUsuario;
import com.ddm.veggie.R;
import com.ddm.veggie.modelo.Receita;
import com.ddm.veggie.modelo.ReceitaRecycler;
import com.ddm.veggie.modelo.Usuario;
import com.ddm.veggie.view.activity.RecipeActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

public class ReceitaRecyclerAdapter extends RecyclerView.Adapter<ReceitaRecyclerAdapter.ViewHolder> {
    private List<ReceitaRecycler> receitas;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private CardView card;
        private ImageButton ibFavorite;
        private ImageView ivImage;
        private ImageView ivStar;
        private ProgressBar pbImage;
        private TextView tvFavoriteCount;
        private TextView tvTitle;

        public ViewHolder(View itemView) {
            super(itemView);
            card = itemView.findViewById(R.id.recycler_view_card_layout);
            ibFavorite = itemView.findViewById(R.id.recycler_view_ib_favorite);
            ivImage = itemView.findViewById(R.id.recycler_view_iv_image);
            ivStar = itemView.findViewById(R.id.recycler_view_iv_star);
            tvFavoriteCount = itemView.findViewById(R.id.recycler_view_tv_favorite_count);
            tvTitle = itemView.findViewById(R.id.recycler_view_tv_title);
            pbImage = itemView.findViewById(R.id.recycler_view_pb_image);
        }
    }

    public ReceitaRecyclerAdapter(List<ReceitaRecycler> receitas) {
        this.receitas = receitas;
    }

    public void setReceitas(List<ReceitaRecycler> receitas) {
        this.receitas = receitas;
    }

    @NonNull
    @Override
    public ReceitaRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view_recipe, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //Configure Components
        ReceitaRecycler currentRecipe = receitas.get(position);
        holder.tvTitle.setText(currentRecipe.getNome());
        holder.ivImage.setImageResource(R.color.transparent);
        holder.pbImage.setVisibility(View.VISIBLE);
        StorageReference storageReference = FirebaseStorage.getInstance().getReference("recipes_images/"+currentRecipe.getFirebaseId()+".jpg");
        storageReference.getBytes(1024 * 1024).addOnSuccessListener((OnSuccessListener<byte[]>) bytes -> {
            Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            holder.ivImage.setImageBitmap(bmp);
            holder.pbImage.setVisibility(View.GONE);
        });
        holder.tvFavoriteCount.setText("Favoritados: " + currentRecipe.getFavoriteCount());
        if (currentRecipe.isFavorited()) {
            holder.ivStar.setImageResource(R.drawable.ic_baseline_white_star_24);
        } else {
            holder.ivStar.setImageResource(R.drawable.ic_baseline_white_star_outline_24);
        }

        //Actions
        holder.card.setOnClickListener(click -> {
            Intent intent = new Intent(holder.itemView.getContext(), RecipeActivity.class);
            Receita recipe = ManterReceita.getReceita(currentRecipe.getFirebaseId());
            intent.putExtra("firebaseId", recipe.getFirebaseId());
            intent.putExtra("name", recipe.getNome());
            intent.putExtra("descricao", recipe.getDescricao());
            intent.putExtra("favoriteCount", recipe.getFavoriteCount());
            intent.putExtra("isFavorited", currentRecipe.isFavorited());
            holder.itemView.getContext().startActivity(intent);
        });

        holder.ibFavorite.setOnClickListener(click -> {
            if (currentRecipe.isFavorited()) {
                ManterUsuario.updateUsuarioRemoveFavoriteRecipe(FirebaseAuth.getInstance().getCurrentUser().getUid(), currentRecipe.getFirebaseId(), onCompleteListenerUser -> {
                    if(onCompleteListenerUser.isSuccessful()) {
                        ManterReceita.updateReceitaDecreaseFavoriteCount(currentRecipe.getFirebaseId(), onCompleteListenerRecipe -> {
                            if (onCompleteListenerRecipe.isSuccessful()) {
                                currentRecipe.setIsFavorited(!currentRecipe.isFavorited());
                                holder.ivStar.setImageResource(R.drawable.ic_baseline_white_star_outline_24);
                                holder.tvFavoriteCount.setText("Favoritados: " + ManterReceita.getReceita(currentRecipe.getFirebaseId()).getFavoriteCount());
                            }
                        });
                    }
                });
            } else {
                ManterUsuario.updateUsuarioAddFavoriteRecipe(FirebaseAuth.getInstance().getCurrentUser().getUid(), currentRecipe.getFirebaseId(), onCompleteListenerUser -> {
                    if(onCompleteListenerUser.isSuccessful()) {
                        ManterReceita.updateReceitaIncreaseFavoriteCount(currentRecipe.getFirebaseId(), onCompleteListenerRecipe -> {
                            if (onCompleteListenerRecipe.isSuccessful()) {
                                currentRecipe.setIsFavorited(!currentRecipe.isFavorited());
                                holder.ivStar.setImageResource(R.drawable.ic_baseline_white_star_24);
                                holder.tvFavoriteCount.setText("Favoritados: " + ManterReceita.getReceita(currentRecipe.getFirebaseId()).getFavoriteCount());
                            }
                        });
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return receitas.size();
    }
}
