package org.example;

public class Collegamento {
    private Pagina pagina;
    private Frase frase;

    public Collegamento(Pagina pagina, Frase frase)
    {
        this.pagina = pagina;
        this.frase = frase;
    }

    public Frase getFrase() {
        return frase;
    }

    public void setFrase(Frase frase) {
        this.frase = frase;
    }

    public Pagina getPagina() {
        return pagina;
    }

    public void setPagina(Pagina pagina) {
        this.pagina = pagina;
    }
}
