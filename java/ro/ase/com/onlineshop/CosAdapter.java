package ro.ase.com.onlineshop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class CosAdapter extends ArrayAdapter<Haina> {
    private final ArrayList<Haina> articole; // Lista articolelor
    private final HashMap<Haina, Integer> cantitati; // HashMap pentru cantități

    public CosAdapter(Context context, ArrayList<Haina> articole, HashMap<Haina, Integer> cantitati) {
        super(context, 0, articole);
        this.articole = articole;
        this.cantitati = cantitati;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Haina haina = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.cos_item, parent, false);
        }

        TextView textViewHainaNume = convertView.findViewById(R.id.textViewHainaNume);
        TextView textViewHainaCantitate = convertView.findViewById(R.id.textViewHainaCantitate); // TextView pentru cantitate

        textViewHainaNume.setText(haina.getNume());
        textViewHainaCantitate.setText("Cantitate: " + cantitati.get(haina));

        return convertView;
    }
}
