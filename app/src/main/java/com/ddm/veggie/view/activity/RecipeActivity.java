package com.ddm.veggie.view.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.ddm.veggie.DAO.ManterReceita;
import com.ddm.veggie.R;
import com.ddm.veggie.apresentador.RecipeActivityApresentador;
import com.ddm.veggie.contrato.ContratoRecipe;
import com.ddm.veggie.modelo.ReceitaRecycler;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class RecipeActivity extends AppCompatActivity implements ContratoRecipe.ContratoRecipeView {

    //Presenter
    private final ContratoRecipe.ContratoRecipePresenter PRESENTER;

    //Components
    private ImageButton ibFavorite;
    private ImageView ivImage;
    private ProgressBar pbImage;
    private Toolbar tbToolbar;
    private TextView tvDescriptionContent;
    private TextView tvFavoriteCount;

    //Variables
    private ReceitaRecycler currentRecipe;

    public RecipeActivity() {
        PRESENTER = new RecipeActivityApresentador(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        //Variables from bundle
        currentRecipe = new ReceitaRecycler(
                getIntent().getExtras().getString("firebaseId"),
                getIntent().getExtras().getString("name"),
                getIntent().getExtras().getString("descricao"),
                getIntent().getExtras().getInt("favoriteCount"),
                getIntent().getExtras().getBoolean("isFavorited")
        );

        //Configure statusbar
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                window.setStatusBarColor(getResources().getColor(R.color.white, getTheme()));
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }
        }

        //Initialize Components
        ibFavorite = findViewById(R.id.recipe_ib_favorite);
        ivImage = findViewById(R.id.recipe_iv_image);
        pbImage = findViewById(R.id.recipe_pb_image);
        tbToolbar = findViewById(R.id.recipe_tb_toolbar);
        tvDescriptionContent = findViewById(R.id.recipe_tv_description_content);
        tvFavoriteCount = findViewById(R.id.recipe_tv_favorite_count);

        //Configure Components
        tbToolbar.setTitle(currentRecipe.getNome());
        pbImage.setVisibility(View.VISIBLE);
        StorageReference storageReference = FirebaseStorage.getInstance().getReference("recipes_images/"+currentRecipe.getFirebaseId()+".jpg");
        storageReference.getBytes(1024 * 1024).addOnSuccessListener((OnSuccessListener<byte[]>) bytes -> {
            Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            ivImage.setImageBitmap(bmp);
            ivImage.setClipToOutline(true);
            pbImage.setVisibility(View.GONE);
        });
        tvFavoriteCount.setText("Favoritados: " + currentRecipe.getFavoriteCount());
        if (currentRecipe.isFavorited()) {
            ibFavorite.setImageResource(R.drawable.ic_baseline_white_star_24);
        } else {
            ibFavorite.setImageResource(R.drawable.ic_baseline_white_star_outline_24);
        }
        tvDescriptionContent.setText(currentRecipe.getDescricao().replaceAll("<br/>", "\n"));

        //Actions
        ibFavorite.setOnClickListener(click -> PRESENTER.changeFavorite(currentRecipe.getFirebaseId()));
        tbToolbar.setNavigationOnClickListener(v -> this.finish());
    }

    @Override
    public void enableFavorite() {
        currentRecipe.setIsFavorited(!currentRecipe.isFavorited());
        ibFavorite.setImageResource(R.drawable.ic_baseline_white_star_24);
        tvFavoriteCount.setText("Favoritados: " + ManterReceita.getReceita(currentRecipe.getFirebaseId()).getFavoriteCount());
    }

    @Override
    public void disableFavorite() {
        currentRecipe.setIsFavorited(!currentRecipe.isFavorited());
        ibFavorite.setImageResource(R.drawable.ic_baseline_white_star_outline_24);
        tvFavoriteCount.setText("Favoritados: " + ManterReceita.getReceita(currentRecipe.getFirebaseId()).getFavoriteCount());
    }
}