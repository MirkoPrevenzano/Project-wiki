package ImplementazionePostgresDao;

import DAO.ListinoDAO;
import Database.ConnessioneDB;

import java.sql.*;
import java.sql.Date;
import java.util.List;

public class ImplementazionePostgresDao implements ListinoDAO {
    private Connection connection;

    public ImplementazionePostgresDao() {
        try {
            connection = ConnessioneDB.getInstance().connection;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void leggiListinoAutore(List<String> nomeAutore, List<String> cognomeAutore, List<String> login, List<String> password) {
        try {
            PreparedStatement leggiListinoPS = connection.prepareStatement(
                    "SELECT * FROM autore");
            ResultSet rs = leggiListinoPS.executeQuery();

            while (rs.next()) {
                nomeAutore.add(rs.getString("nome_a"));
                cognomeAutore.add(rs.getString("cognome"));
                login.add(rs.getString("username"));
                password.add(rs.getString("password"));
            }
            rs.close();
            leggiListinoPS.close();

        } catch (Exception e) {
            System.out.println("Errore: " + e.getMessage());
        }
    }

    public void leggiListinoUtenti(List<String> nomeUtente, List<String> cognomeUtente)
    {
        try {

            PreparedStatement leggiListinoPS = connection.prepareStatement("SELECT * FROM utente");
            ResultSet rs = leggiListinoPS.executeQuery();

            while(rs.next())
            {
                nomeUtente.add(rs.getString("nome_u"));
                cognomeUtente.add(rs.getString("cognome"));

            }
            rs.close();
            leggiListinoPS.close();

        }catch(Exception e)
        {
            System.out.println("Errore: "+e.getMessage());
        }
    }

    public void leggiPagineAutori(List<String> TitoloPagina, List<Date> dataPagina, List<Time> oraPagina, List<String> loginAutore) {
        try
        {
            PreparedStatement leggiListinoPS = connection.prepareStatement("SELECT * FROM pagina");
            ResultSet rs = leggiListinoPS.executeQuery();

            while(rs.next())
            {
                TitoloPagina.add((rs.getString("titolo")));
                loginAutore.add(rs.getString("username"));
                dataPagina.add(rs.getDate("data"));
                oraPagina.add(rs.getTime("ora"));
                System.out.println("nome: "+rs.getString("username"));
                System.out.println("titolo: "+rs.getString("titolo"));
            }
            rs.close();
            leggiListinoPS.close();

        }catch(Exception e)
        {
            System.out.println("Errore: "+e.getMessage());
        }

    }

    public void leggiFrasiPagina(List<String> TestoFrase, List<String> TitoloPagina, List<Integer> ordine)
    {
        try
        {
            PreparedStatement leggiListinoPS = connection.prepareStatement("SELECT * FROM (frase NATURAL INNER JOIN testo) NATURAL INNER JOIN pagina ORDER BY username, titolo, ordine ");
            ResultSet rs = leggiListinoPS.executeQuery();

            while(rs.next())
            {
                TestoFrase.add(rs.getString("testo"));
                TitoloPagina.add(rs.getString("titolo"));
                ordine.add(rs.getInt("ordine"));
            }
            connection.close();
            rs.close();
            leggiListinoPS.close();

        }catch(Exception e)
        {
            System.out.println("Errore: "+e.getMessage());
        }
    }


}
