package DAO;

import java.sql.Time;
import java.sql.Date;
import java.util.List;

public interface ListinoDAO {
    void leggiListinoAutore(List<String> nomeAutore, List<String> cognomeAutore, List<String> login, List<String> password);

    void leggiListinoUtenti(List<String> nomeUtente, List<String> cognomeUtente);

    void leggiPagineAutori(List<String> TitoloPagina, List<Date> DataPagina, List<Time> oraPagina, List<String> loginAutore);

    void leggiFrasiPagina(List<String> TestoFrase, List<String> TitoloPagina, List<Integer> ordine);
}
