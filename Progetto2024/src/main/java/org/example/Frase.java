package org.example;

public class Frase {
    private String testo;
    private Testo testo_effettivo;
    private Collegamento collegamento;

    public Frase(String testo, Testo testo_effettivo)
    {
        this.testo = testo;
        this.testo_effettivo = testo_effettivo;
    }

    public String getTesto() {
        return testo;
    }

    public Collegamento getCollegamento() {
        return collegamento;
    }

    public Testo getTesto_effettivo() {
        return testo_effettivo;
    }

    public void setTesto(String testo) {
        this.testo = testo;
    }

    public void setCollegamento(Collegamento collegamento) {
        this.collegamento = collegamento;
    }

    public void setTesto_effettivo(Testo testo_effettivo) {
        this.testo_effettivo = testo_effettivo;
    }
}
