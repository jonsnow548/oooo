package ro.ase.com.onlineshop;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText searchBar;
    private Button buttonHaine, buttonIncaltaminte, buttonAccesorii;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);  // Leagă activitatea de layout-ul XML

        // Inițializare elemente UI
        searchBar = findViewById(R.id.searchBar);
        buttonHaine = findViewById(R.id.buttonHaine);
        buttonIncaltaminte = findViewById(R.id.buttonIncaltaminte);
        buttonAccesorii = findViewById(R.id.buttonAccesorii);

        // Setare acțiune pentru bara de căutare
        searchBar.setOnEditorActionListener((v, actionId, event) -> {
            String query = searchBar.getText().toString().trim();
            if (!query.isEmpty()) {
                searchProducts(query);  // Funcția care ar efectua căutarea produselor
            } else {
                Toast.makeText(MainActivity.this, "Introduceți un termen de căutare", Toast.LENGTH_SHORT).show();
            }
            return true;
        });

        // Setare acțiuni pentru butoanele de categorii
        buttonHaine.setOnClickListener(v -> openCategory("Haine"));
        buttonIncaltaminte.setOnClickListener(v -> openCategory("Încălțăminte"));
        buttonAccesorii.setOnClickListener(v -> openCategory("Accesorii"));
    }

    // Funcție care simulează căutarea produselor (în realitate, ar trebui să efectueze o interogare a bazei de date)
    private void searchProducts(String query) {
        // De exemplu, ar putea deschide o activitate nouă cu rezultatele căutării
        Toast.makeText(MainActivity.this, "Căutare pentru: " + query, Toast.LENGTH_SHORT).show();
    }

    // Funcție pentru deschiderea unei activități specifice categoriei selectate
    private void openCategory(String category) {
        Toast.makeText(MainActivity.this, "Deschide categoria: " + category, Toast.LENGTH_SHORT).show();
        // Aici poți adăuga codul pentru a deschide o activitate dedicată fiecărei categorii
         Intent intent = new Intent(MainActivity.this, HaineActivity.class);
         startActivity(intent);
    }
}
