package JDBC;

import java.beans.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class BancoDeDados implements InterfaceBancoDeDados {
    Connection c;

    @Override
    public void conectar(String db_url, String db_user, String db_password) {
        // TODO Auto-generated method stub
        try {
            Log meuLogger = new Log("Log.txt");
			meuLogger.logger.setLevel(Level.INFO);
			meuLogger.logger.info("Iniciando a conexão com o bando de dados!");

            c = DriverManager.getConnection("jdbc:mysql://localhost:3306/perifericos", "root", "");
            meuLogger.logger.setLevel(Level.INFO);
			meuLogger.logger.info("Conectado ao bando de dados!");
        } catch (Exception e) {
            System.out.println("Problemas: " + e);
        }
    }

    @Override
    public void desconectar() {
        try {
            if (c != null) {
                c.close();
                System.out.println("Desconectado!");
                Log meuLogger = new Log("Log.txt");
			meuLogger.logger.setLevel(Level.INFO);
			meuLogger.logger.info("Desconectado do banco de dados!");
            }
        } catch (Exception e) {
            System.out.println("Problemas na desconeção do Banco de dados " + e);
        }
    }

    @Override
    public void consultar(String db_query) {
        try {
            Log meuLogger = new Log("Log.txt");
			meuLogger.logger.setLevel(Level.INFO);
			meuLogger.logger.info("Iniciando consulta ao banco!");
            PreparedStatement ps = c.prepareStatement(db_query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("ID"));
                System.out.println("Tipo: " + rs.getString("Tipo"));
                System.out.println("Modelo: " + rs.getString("Modelo"));
                System.out.println("Preço: " + rs.getFloat("Preço"));
                meuLogger.logger.setLevel(Level.INFO);
			meuLogger.logger.info("Consulta ao banco finalizada!");
            }
        } catch (Exception e) {
            System.out.println("Erro na consulta com Banco de dados " + e);
        }

    }

    @Override
    public int inserirAlterarExcluir(String db_query) {
        int resultado = 0;
        int linhas = 0;
        try {
            PreparedStatement ps = c.prepareStatement(db_query);
            resultado = ps.executeUpdate();
            linhas = ps.executeUpdate();

        } catch (Exception e) {
            System.out.println("Erro na execução de alteração do Banco de dado " + e);
        }
        return resultado;

        
    }
    
    public void  deletar(int id) {
        String delete = "DELETE FROM perifericos WHERE id = ?";
        try {
            PreparedStatement ps = c.prepareStatement(delete);
            ps.setInt(1, id);

            ps.execute();
            ps.close();
            c.close();
        } catch (SQLException e) {
            System.out.println("erro ao deletar:");
        }
    }

    public PreparedStatement prepareStatement(String inserir) {
        return null;
    }
}
