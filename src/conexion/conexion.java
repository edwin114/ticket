/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.*;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author Edwin
 */
public class conexion {

    Connection conectar = null;
    static Statement s;
    static ResultSet rs;

    public Connection conexn() {
        try {
            Class.forName("org.postgresql.Driver");
            String cadena = "jdbc:postgresql://localhost:5432/ticket";
            String user = "postgres";
            String pass = "POSTGRES";
            conectar = (Connection) DriverManager.getConnection(cadena, user, pass);
            s = conectar.createStatement();
            JOptionPane.showMessageDialog(null, "Base de datos conectada");
        } catch (Exception exc) {

            JOptionPane.showMessageDialog(null, "Erro Base de datos NO conectada" + exc.getMessage());
        }
        return conectar;

    }

    public static ArrayList<String> listaCombo() {
        ArrayList<String> lista = new ArrayList<>();

        String SQL = "SELECT *FROM ticket";

        try {

            rs = s.executeQuery(SQL);
            while (rs.next()) {
                lista.add(rs.getString("estado"));
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error de datos conectada" + e.getMessage());
        }

        return lista;
    }
}
