package ro.ase.com.onlineshop;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;

public class CosActivity extends AppCompatActivity {

    private ListView cosListView;
    private ArrayList<Haina> cosArticoleList; // Lista de articole
    private CosAdapter cosAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cos);

        cosListView = findViewById(R.id.cosListView);
        cosArticoleList = new ArrayList<>();

        // Obține coșul din intenție
        Cos cos = (Cos) getIntent().getSerializableExtra("cos");
        if (cos != null) {
            // Extrage articolele din coș
            HashMap<Haina, Integer> articole = cos.getArticole();
            cosArticoleList.addAll(articole.keySet()); // Adaugă doar articolele în lista de articole

            // Creează un adapter pentru coș, inclusiv cantitățile
            cosAdapter = new CosAdapter(this, cosArticoleList, articole); // Transmite articolele și cantitățile
            cosListView.setAdapter(cosAdapter);
        }
    }
}
