package Model;

public class Frase {
    private String testo;
    private Testo testoEffettivo;
    private Collegamento collegamento;

    public Frase(String testo, Testo testoEffettivo)
    {
        this.testo = testo;
        this.testoEffettivo = testoEffettivo;
    }

    public String getTesto() {
        return testo;
    }

    public Collegamento getCollegamento() {
        return collegamento;
    }

    public Testo getTestoEffettivo() {
        return testoEffettivo;
    }

    public void setTesto(String testo) {
        this.testo = testo;
    }

    public void setCollegamento(Collegamento collegamento) {
        this.collegamento = collegamento;
    }

    public void setTestEffettivo(Testo testoEffettivo) {
        this.testoEffettivo = testoEffettivo;
    }
}
