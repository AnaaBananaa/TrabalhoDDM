package com.ddm.veggie.adaptador;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ddm.veggie.DAO.ManterReceita;
import com.ddm.veggie.DAO.ManterUsuario;
import com.ddm.veggie.R;
import com.ddm.veggie.modelo.Receita;
import com.ddm.veggie.modelo.Usuario;
import com.ddm.veggie.view.activity.RecipeActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

public class ReceitaRecyclerAdapter extends RecyclerView.Adapter<ReceitaRecyclerAdapter.ViewHolder> {
    private List<Receita> receitas;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout mainLayout;
        private TextView txtTitle;
        private TextView rvTxtFavoriteCount;
        private ImageButton btnFavorite;
        private ImageView image;
        private ImageView rvIvStar;
        private ProgressBar progressBar;

        public ViewHolder(View itemView) {
            super(itemView);
            mainLayout = itemView.findViewById(R.id.recipe_rv_main_layout);
            txtTitle = itemView.findViewById(R.id.recipe_rv_txt_title);
            rvTxtFavoriteCount = itemView.findViewById(R.id.rv_txt_favorite_count);
            progressBar = itemView.findViewById(R.id.recipe_rv_progress_image);
            btnFavorite = itemView.findViewById(R.id.rv_bt_favorite);
            image = itemView.findViewById(R.id.recipe_rv_image);
            rvIvStar = itemView.findViewById(R.id.rv_iv_star);
        }
    }

    public ReceitaRecyclerAdapter(List<Receita> receitas) {
        this.receitas = receitas;
    }

    public void setReceitas(List<Receita> receitas) {
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
        Usuario usuario = ManterUsuario.getUsuario(FirebaseAuth.getInstance().getCurrentUser().getUid());
        for (Receita recipe: usuario.getFavoriteRecipes()) {
            if (recipe.getFirebaseId().equals(receitas.get(position).getFirebaseId())) {
                holder.rvIvStar.setImageResource(R.drawable.ic_baseline_white_star_24);
                break;
            }
        }
        holder.txtTitle.setText(receitas.get(position).getNome());
        holder.rvTxtFavoriteCount.setText("Favoritados: " + receitas.get(position).getFavoriteCount());
        holder.progressBar.setVisibility(View.VISIBLE);
        StorageReference storageReference = FirebaseStorage.getInstance().getReference("recipes_images/"+receitas.get(position).getFirebaseId()+".jpg");
        storageReference.getBytes(1024 * 1024).addOnSuccessListener((OnSuccessListener<byte[]>) bytes -> {
            Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            holder.image.setImageBitmap(bmp);
            holder.progressBar.setVisibility(View.GONE);
        });
        holder.mainLayout.setOnClickListener(click -> {
            Intent intent = new Intent(holder.itemView.getContext(), RecipeActivity.class);
            intent.putExtra("recipeId", receitas.get(position).getFirebaseId());
            holder.itemView.getContext().startActivity(intent);
        });
        holder.btnFavorite.setOnClickListener(click -> {
            boolean userContaisRecipe = false;
            Usuario user = ManterUsuario.getUsuario(FirebaseAuth.getInstance().getCurrentUser().getUid());
            for (Receita recipe: user.getFavoriteRecipes()) {
                if (recipe.getFirebaseId().equals(receitas.get(position).getFirebaseId())) {
                    userContaisRecipe = true;
                    break;
                }
            }
            if (userContaisRecipe) {
                ManterUsuario.updateUsuarioRemoveFavoriteRecipe(user.getFirebaseUserId(), receitas.get(position).getFirebaseId(), onCompleteListenerUser -> {
                    if(onCompleteListenerUser.isSuccessful()) {
                        ManterReceita.updateReceitaDecreaseFavoriteCount(receitas.get(position).getFirebaseId(), onCompleteListenerRecipe -> {
                            if (onCompleteListenerRecipe.isSuccessful()) {
                                holder.rvIvStar.setImageResource(R.drawable.ic_baseline_white_star_outline_24);
                                holder.rvTxtFavoriteCount.setText("Favoritados: " + ManterReceita.getReceita(receitas.get(position).getFirebaseId()).getFavoriteCount());
                            }
                        });
                    }
                });
            } else {
                ManterUsuario.updateUsuarioAddFavoriteRecipe(user.getFirebaseUserId(), receitas.get(position).getFirebaseId(), onCompleteListenerUser -> {
                    if(onCompleteListenerUser.isSuccessful()) {
                        ManterReceita.updateReceitaIncreaseFavoriteCount(receitas.get(position).getFirebaseId(), onCompleteListenerRecipe -> {
                            if (onCompleteListenerRecipe.isSuccessful()) {
                                holder.rvIvStar.setImageResource(R.drawable.ic_baseline_white_star_24);
                                holder.rvTxtFavoriteCount.setText("Favoritados: " + ManterReceita.getReceita(receitas.get(position).getFirebaseId()).getFavoriteCount());
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
