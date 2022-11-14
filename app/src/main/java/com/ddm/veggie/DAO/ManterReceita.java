package com.ddm.veggie.DAO;

import com.ddm.veggie.modelo.Receita;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ManterReceita {

    public static List<Receita> listReceitas(String nome, Map<String, Object> filters) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Query query = db.collection("receitas");
        if (filters != null) {
            if (filters.get("filterOrder") != null) {
                switch ((String) filters.get("filterOrder")){
                    case "Mais Favoritadas":
                        query = query.orderBy("favorite_count", Query.Direction.DESCENDING);
                        break;
                    case "Ordem alfabética A-Z":
                        query = query.orderBy("nome", Query.Direction.ASCENDING);
                        break;
                    case "Ordem alfabética Z-A":
                        query = query.orderBy("nome", Query.Direction.DESCENDING);
                        break;
                }
            } else {
                query = query.orderBy("favorite_count", Query.Direction.DESCENDING);
            }
        }
        Task<QuerySnapshot> result = query.get();
        while (!result.isComplete());
        List<Receita> receitas = new ArrayList<>();
        for (QueryDocumentSnapshot document : result.getResult()) {
            String nomeDocument = document.getString("nome");
            if (!nome.isEmpty() && nomeDocument != null) {
                String[] words = nomeDocument.toLowerCase().split(" ");
                for (String word : words) {
                    if (word.length() >= nome.length() && word.substring(0, nome.length()).equals(nome.toLowerCase())) {
                        receitas.add(new Receita(document.getId(), nomeDocument, document.getString("descricao"), Integer.parseInt(document.getString("favorite_count"))));
                        break;
                    }
                }
            } else {
                receitas.add(new Receita(document.getId(), nomeDocument, document.getString("descricao"), document.get("favorite_count", Integer.class)));
            }

        }
        return receitas;
    }

    public static Receita getReceita(String receitaId) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference documentReference = db.collection("receitas").document(receitaId);
        Task<DocumentSnapshot> result = documentReference.get();
        while (!result.isComplete());
        DocumentSnapshot documentSnapshot = result.getResult();
        Receita receita = null;
        if (documentSnapshot != null) {
            receita = new Receita(documentSnapshot.getId(), documentSnapshot.getString("nome"), documentSnapshot.getString("descricao"), documentSnapshot.get("favorite_count", Integer.class));
        }
        return receita;
    }

    public static void updateReceitaIncreaseFavoriteCount(String receitaId, OnCompleteListener onCompleteListener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("receitas").document(receitaId).update("favorite_count", FieldValue.increment(1)).addOnCompleteListener(onCompleteListener);
    }

    public static void updateReceitaDecreaseFavoriteCount(String receitaId, OnCompleteListener onCompleteListener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("receitas").document(receitaId).update("favorite_count", FieldValue.increment(-1)).addOnCompleteListener(onCompleteListener);
    }
}
