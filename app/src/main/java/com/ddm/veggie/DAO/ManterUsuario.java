package com.ddm.veggie.DAO;

import com.ddm.veggie.modelo.Receita;
import com.ddm.veggie.modelo.Usuario;
import com.ddm.veggie.singleton.RepositorioUsuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ManterUsuario {

    public static void createUsuario(String firebaseUserId, String email, String nome) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, Object> usuarioData = new HashMap<>();
        usuarioData.put("firebase_user_id", firebaseUserId);
        usuarioData.put("email", email);
        usuarioData.put("nome", nome);
        usuarioData.put("favorite_recipes", new ArrayList<>());
        Task<Void> result = db.collection("usuarios").document(firebaseUserId).set(usuarioData);
        while (!result.isComplete());
    }

    public static Usuario getUsuario(String firebaseUserId) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference documentReference = db.collection("usuarios").document(firebaseUserId);
        Task<DocumentSnapshot> result = documentReference.get();
        while (!result.isComplete());
        DocumentSnapshot documentSnapshot = result.getResult();
        Usuario usuario = null;
        if (documentSnapshot != null) {
            List<Receita> favoriteRecipes = new ArrayList<>();
            for (String recipeId : (ArrayList<String>) documentSnapshot.get("favorite_recipes")) {
                favoriteRecipes.add(ManterReceita.getReceita(recipeId));
            }
            usuario = new Usuario((String) documentSnapshot.getData().get("firebase_user_id"), (String) documentSnapshot.getData().get("email"), (String) documentSnapshot.getData().get("nome"), favoriteRecipes);
        }
        return usuario;
    }

    public static boolean updateUsuarioName(String firebaseUserId, String name) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, Object> values = new HashMap<>();
        values.put("nome", name);
        Task<Void> result = db.collection("usuarios").document(firebaseUserId).update(values);
        while (!result.isComplete());
        Usuario usuario = getUsuario(firebaseUserId);
        if (usuario.getNome().equals(name)) {
            RepositorioUsuario.getInstance().getUsuario().setNome(name);
            return true;
        }
        return false;
    }

    public static void updateUsuarioAddFavoriteRecipe(String firebaseUserId, String receitaId, OnCompleteListener onCompleteListener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("usuarios").document(firebaseUserId).update("favorite_recipes", FieldValue.arrayUnion(receitaId)).addOnCompleteListener(onCompleteListener);
    }

    public static void updateUsuarioRemoveFavoriteRecipe(String firebaseUserId, String receitaId, OnCompleteListener onCompleteListener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("usuarios").document(firebaseUserId).update("favorite_recipes", FieldValue.arrayRemove(receitaId)).addOnCompleteListener(onCompleteListener);
    }

    public static boolean usuarioContainsRecipe(String firebaseUserId, String recipeId) throws InterruptedException {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference documentReference = db.collection("usuarios").document(firebaseUserId);
        Task<DocumentSnapshot> result = documentReference.get();
        while (!result.isComplete());
        boolean contains = false;
        DocumentSnapshot documentSnapshot = result.getResult();
        if (documentSnapshot != null) {
            for (String recipe : (ArrayList<String>) documentSnapshot.get("favorite_recipes")) {
                if (recipe.equals(recipeId)) {
                    contains = true;
                    break;
                }
            }
        }
        return contains;
    }

    public static void deleteUsuario(String firebaseUserId, OnCompleteListener<Void> onCompleteListener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("usuarios").document(firebaseUserId).delete().addOnCompleteListener(onCompleteListener);
    }
}
