package ro.ase.com.onlineshop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class HainaAdapter extends RecyclerView.Adapter<HainaAdapter.HainaViewHolder> {
    private Context context;
    private List<Haina> hainaList;
    private Cos cos; // Reference to the shopping cart

    public HainaAdapter(Context context, List<Haina> hainaList, Cos cos) {
        this.context = context;
        this.hainaList = hainaList;
        this.cos = cos; // Initialize the cart
    }

    @NonNull
    @Override
    public HainaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.haina_item, parent, false);
        return new HainaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HainaViewHolder holder, int position) {
        Haina haina = hainaList.get(position);
        holder.textViewNume.setText(haina.getNume());
        holder.textViewPret.setText(String.format("%.2f RON", haina.getPret()));

        // Load image using Glide
        Glide.with(context).load(haina.getImagineUrl()).into(holder.imageViewHaina);

        // Set up spinner for sizes with available stock
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, Haina.getAvailableSizes(haina));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        holder.spinnerCantitate.setAdapter(adapter);

        // Set up button click listener to add items to the cart
        holder.buttonAdaugaLaCos.setOnClickListener(v -> {
            String selectedSize = holder.spinnerCantitate.getSelectedItem().toString();
            int quantity = 1; // Default quantity

            // Assuming we add items to the cart based on the selected size
            if (!selectedSize.isEmpty()) {
                // Add the item to the cart
                cos.adaugaLaCos(haina, selectedSize, quantity);
                Toast.makeText(context, haina.getNume() + " adăugat în coș!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Selectați o mărime disponibilă", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return hainaList.size();
    }

    public static class HainaViewHolder extends RecyclerView.ViewHolder {
        TextView textViewNume, textViewPret;
        ImageView imageViewHaina;
        Spinner spinnerCantitate;
        Button buttonAdaugaLaCos;

        public HainaViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewNume = itemView.findViewById(R.id.textViewNume);
            textViewPret = itemView.findViewById(R.id.textViewPret);
            imageViewHaina = itemView.findViewById(R.id.imageViewHaina);
            spinnerCantitate = itemView.findViewById(R.id.spinnerCantitate);
            buttonAdaugaLaCos = itemView.findViewById(R.id.buttonAdaugaLaCos);
        }
    }
}
