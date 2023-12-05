package Model;

import Model.Frase;

import java.util.ArrayList;

public class Testo {

    private ArrayList<Frase> frasi;

    public Testo()
    {
        frasi = new ArrayList<Frase>();
    }

    public ArrayList<Frase> getFrasi() {
        return frasi;
    }
    public void addFrase(Frase f)
    {
        frasi.add(f);
    }
    public void removeFrase(Frase f)
    {
        frasi.remove(f);
    }
}
