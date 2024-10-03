package ro.ase.com.onlineshop;

import java.util.ArrayList;

public class Haina {
    private String nume;
    private String brand;
    private String descriere;
    private String categorie;
    private String culoare;
    private double pret;
    private String imagineUrl;
    private String marime;
    private int stoc;

    public Haina() {
        // Firestore requires a no-argument constructor
    }

    public Haina(String nume, String brand, String descriere, String categorie, String culoare, double pret, String imagineUrl, String marime, int stoc) {
        this.nume = nume;
        this.brand = brand;
        this.descriere = descriere;
        this.categorie = categorie;
        this.culoare = culoare;
        this.pret = pret;
        this.imagineUrl = imagineUrl;
        this.marime = marime;
        this.stoc = stoc;
    }

    // Getters and setters
    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getDescriere() {
        return descriere;
    }

    public void setDescriere(String descriere) {
        this.descriere = descriere;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public String getCuloare() {
        return culoare;
    }

    public void setCuloare(String culoare) {
        this.culoare = culoare;
    }

    public double getPret() {
        return pret;
    }

    public void setPret(double pret) {
        this.pret = pret;
    }

    public String getImagineUrl() {
        return imagineUrl;
    }

    public void setImagineUrl(String imagineUrl) {
        this.imagineUrl = imagineUrl;
    }

    public String getMarime() {
        return marime;
    }

    public void setMarime(String marime) {
        this.marime = marime;
    }

    public int getStoc() {
        return stoc;
    }

    public void setStoc(int stoc) {
        this.stoc = stoc;
    }

    // New method to get available sizes based on stock
    public static ArrayList<String> getAvailableSizes(Haina haina) {
        ArrayList<String> availableSizes = new ArrayList<>();
        // Assuming we have predefined sizes, you can modify as per your requirement
        if (haina.getStoc() > 0) {
            availableSizes.add(haina.getMarime());
        }
        return availableSizes;
    }
}
