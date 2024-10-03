package ro.ase.com.onlineshop;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class HaineActivity extends AppCompatActivity {

    private RecyclerView haineRecyclerView; // Inlocuiește ListView cu RecyclerView
    private ArrayList<Haina> haineList;
    private HainaAdapter hainaAdapter;
    private FloatingActionButton fabAddHaina;
    private Button buttonVizualizeazaCos;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private Cos cos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_haine);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        cos = new Cos(); // Inițializează coșul

        haineRecyclerView = findViewById(R.id.haineRecyclerView); // Schimbă ID-ul în RecyclerView
        fabAddHaina = findViewById(R.id.fabAddHaina);
        buttonVizualizeazaCos = findViewById(R.id.buttonVizualizeazaCos);

        // Inițializează lista de haine
        haineList = new ArrayList<>();

        // Ascunde fab-ul inițial
        fabAddHaina.setVisibility(View.GONE);

        // Verifică dacă utilizatorul este administrator
        checkIfUserIsAdmin();

        // Setează un listener pentru fabAddHaina
        fabAddHaina.setOnClickListener(v -> {
            Intent intent = new Intent(HaineActivity.this, AddHainaActivity.class);
            startActivity(intent);
        });

        // Creează un HainaAdapter pentru a lega lista de haine la RecyclerView
        hainaAdapter = new HainaAdapter(this, haineList, cos);
        haineRecyclerView.setLayoutManager(new LinearLayoutManager(this)); // Setează layout-ul pentru RecyclerView
        haineRecyclerView.setAdapter(hainaAdapter); // Setează adapter-ul

        // Încarcă hainele din Firestore
        loadHaineFromFirestore();

        // Listener pentru vizualizarea coșului
        buttonVizualizeazaCos.setOnClickListener(v -> {
            Intent intent = new Intent(HaineActivity.this, CosActivity.class);
            intent.putExtra("cos", cos); // Trimite coșul la CosActivity
            startActivity(intent);
        });
    }

    private void checkIfUserIsAdmin() {
        String userId = mAuth.getCurrentUser().getUid();
        db.collection("users").document(userId).get().addOnSuccessListener(documentSnapshot -> {
            if (documentSnapshot.exists()) {
                String role = documentSnapshot.getString("role");
                if ("Administrator".equals(role)) {
                    // Afișează FAB doar dacă utilizatorul este administrator
                    fabAddHaina.setVisibility(View.VISIBLE);
                }
            }
        }).addOnFailureListener(e -> Toast.makeText(HaineActivity.this, "Eroare la obținerea informațiilor utilizatorului", Toast.LENGTH_SHORT).show());
    }

    // Metoda care încarcă hainele din Firestore
    private void loadHaineFromFirestore() {
        db.collection("haine").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                QuerySnapshot snapshot = task.getResult();
                if (snapshot != null && !snapshot.isEmpty()) {
                    haineList.clear();  // Curăță lista înainte de a adăuga noi date
                    for (DocumentSnapshot document : snapshot.getDocuments()) {
                        Haina haina = document.toObject(Haina.class);  // Transformă documentul într-un obiect Haina
                        haineList.add(haina);  // Adaugă obiectul în lista de haine
                    }
                    hainaAdapter.notifyDataSetChanged();  // Notifică adapterul pentru a reîmprospăta lista
                } else {
                    Toast.makeText(HaineActivity.this, "Nu există haine disponibile", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(HaineActivity.this, "Eroare la încărcarea hainelor", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
