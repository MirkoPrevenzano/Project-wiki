package Model;


import java.util.ArrayList;
import java.util.List;

public class Testo {

    private List<Frase> listFrasi;

    public Testo()
    {
        listFrasi = new ArrayList<>();
    }

    public List<Frase> getFrasi() {return listFrasi;}
    public void addFrase(Frase f)
    {
        listFrasi.add(f);
    }
    public void removeFrase(Frase f)
    {
        listFrasi.remove(f);
    }
}
