package ro.ase.com.onlineshop;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class Cos implements Serializable {
    private HashMap<Haina, Integer> articole; // Folosim un HashMap pentru a asocia fiecare haină cu cantitatea sa

    // Constructor
    public Cos() {
        articole = new HashMap<>();
    }

    // Getter pentru articole
    public HashMap<Haina, Integer> getArticole() {
        return articole;
    }

    // Metodă pentru a adăuga un articol în coș
    public void addArticol(Haina haina, int cantitate) {
        articole.put(haina, articole.getOrDefault(haina, 0) + cantitate);
    }

    // Metodă pentru a elimina un articol din coș
    public boolean removeArticol(Haina haina) {
        if (articole.containsKey(haina)) {
            articole.remove(haina);
            return true;
        }
        return false;
    }

    // Metodă pentru a goli coșul
    public void clearCos() {
        articole.clear();
    }

    // Metodă pentru a calcula totalul coșului
    public double calculateTotal() {
        double total = 0.0;
        for (HashMap.Entry<Haina, Integer> entry : articole.entrySet()) {
            total += entry.getKey().getPret() * entry.getValue();
        }
        return total;
    }

    // Metodă pentru a verifica dacă coșul este gol
    public boolean isEmpty() {
        return articole.isEmpty();
    }

    // Metodă pentru a obține numărul total de articole din coș
    public int getTotalArticole() {
        return articole.size();
    }

    public void adaugaLaCos(Haina haina, String selectedSize, int quantity) {
    }
}
