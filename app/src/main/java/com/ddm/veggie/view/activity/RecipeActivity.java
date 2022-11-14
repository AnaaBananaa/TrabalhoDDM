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
import com.ddm.veggie.DAO.ManterUsuario;
import com.ddm.veggie.R;
import com.ddm.veggie.apresentador.RecipeActivityApresentador;
import com.ddm.veggie.contrato.ContratoRecipe;
import com.ddm.veggie.modelo.Receita;
import com.ddm.veggie.modelo.Usuario;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class RecipeActivity extends AppCompatActivity implements ContratoRecipe.ContratoRecipeView {

    //Presenter
    private final ContratoRecipe.ContratoRecipePresenter PRESENTER;

    //Components
    private ImageButton btFavorite;
    private ImageView ivImage;
    private ProgressBar pImageProgress;
    private TextView tvFavoriteCount;
    private TextView tvDescriptionContent;
    private Toolbar toolbar;

    //Variables
    private Receita receita;

    public RecipeActivity() {
        PRESENTER = new RecipeActivityApresentador(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        //Variables from bundle
        String recipeId = getIntent().getExtras().getString("recipeId");
        receita = ManterReceita.getReceita(recipeId);

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
        btFavorite = findViewById(R.id.recipe_bt_favorite);
        ivImage = findViewById(R.id.recipe_iv_image);
        pImageProgress = findViewById(R.id.recipe_p_image_progress);
        tvFavoriteCount = findViewById(R.id.recipe_tv_favorite_count);
        tvDescriptionContent = findViewById(R.id.recipe_tv_description_content);
        toolbar = findViewById(R.id.recipe_toolbar);

        //Configure Components
        toolbar.setTitle(receita.getNome());
        tvFavoriteCount.setText("Favoritados: " + receita.getFavoriteCount());
        tvDescriptionContent.setText(receita.getDescricao());
        pImageProgress.setVisibility(View.VISIBLE);
        StorageReference storageReference = FirebaseStorage.getInstance().getReference("recipes_images/"+recipeId+".jpg");
        storageReference.getBytes(1024 * 1024).addOnSuccessListener((OnSuccessListener<byte[]>) bytes -> {
            Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            ivImage.setImageBitmap(bmp);
            ivImage.setClipToOutline(true);
            pImageProgress.setVisibility(View.GONE);
        });
        Usuario usuario = ManterUsuario.getUsuario(FirebaseAuth.getInstance().getCurrentUser().getUid());
        for (Receita recipe: usuario.getFavoriteRecipes()) {
            if (recipe.getFirebaseId().equals(recipeId)) {
                btFavorite.setImageResource(R.drawable.ic_baseline_white_star_24);
                break;
            }
        }

        //Actions
        btFavorite.setOnClickListener(click -> PRESENTER.changeFavorite(recipeId));
        toolbar.setNavigationOnClickListener(v -> this.finish());
    }

    @Override
    public void enableFavorite() {
        btFavorite.setImageResource(R.drawable.ic_baseline_white_star_24);
        tvFavoriteCount.setText("Favoritados: " + ManterReceita.getReceita(receita.getFirebaseId()).getFavoriteCount());
    }

    @Override
    public void disableFavorite() {
        btFavorite.setImageResource(R.drawable.ic_baseline_white_star_outline_24);
        tvFavoriteCount.setText("Favoritados: " + ManterReceita.getReceita(receita.getFirebaseId()).getFavoriteCount());
    }
}