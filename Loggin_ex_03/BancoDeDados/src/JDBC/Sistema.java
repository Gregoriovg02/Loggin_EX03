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



public class Sistema {
    public static void main(String[] args) throws IOException {
        Scanner imprimir = new Scanner(System.in);
        BancoDeDados bancodedados = new BancoDeDados();

        bancodedados.conectar("jdbc:mysql://localhost:3306/perifericos", "root", "");
       
        String inserir = "INSERT INTO perifericos (id, tipo, modelo, preço) VALUES (?, ?, ?, ?)";
        
        System.out.println("Insira o ID: ");
        String id = imprimir.next();
        System.out.println("Insira o Tipo do periferio: ");
        String tipo = imprimir.next();
        System.out.println("Insira o Modelo do periferio: ");
        String modelo = imprimir.next();
        System.out.println("Insira o Valor: ");
        float preço = imprimir.nextFloat();
   
        try {
            Log meuLog = new Log("Log.txt");
            meuLog.logger.setLevel(Level.INFO);
            meuLog.logger.info("Iniciando cadastro no banco de dados!");


            
            PreparedStatement ps = bancodedados.c.prepareStatement(inserir);
            ps.setString(1, id);
            ps.setString(2, tipo);
            ps.setString(3, modelo);
            ps.setFloat(4, preço);
            ps.executeUpdate();
            ps.close();
            meuLog.logger.setLevel(Level.INFO);
            meuLog.logger.info("Cadastro realizado com sucesso!");

           } catch (SQLException e) {
            System.out.print("Erro ao realizar Cadastro! " + e.getMessage());
        }

        String consultarbancodedados = "select * from perifericos";
        bancodedados.consultar(consultarbancodedados);

        System.out.println("Você deseja Deletar algum Item? s/n ");
        String resposta = imprimir.next();
        
        if(resposta.equalsIgnoreCase("s")){
        System.out.println("Insira o ID para deletar: ");
        int deletarid = imprimir.nextInt();
        bancodedados.deletar(deletarid);
        bancodedados.desconectar();
    }else{
            bancodedados.desconectar();
        }


        
    }


    
}
