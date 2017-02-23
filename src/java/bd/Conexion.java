/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
 */
package bd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Lluis_2
 */
public class Conexion {

    Connection connection;

    public Conexion() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            connection = DriverManager.getConnection("jdbc:oracle:thin:@ieslaferreria.xtec.cat:8081:INSLAFERRERI", "AlbertER_96", "romanica");

        } catch (SQLException ex) {
            try {
                connection = DriverManager.getConnection("jdbc:oracle:thin:@192.168.180.10:1521:INSLAFERRERI", "AlbertER_96", "romanica");
            } catch (SQLException ex2) {
                Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public Connection getConnection() {
        return connection;
    }

    public void finalizarConexion() throws SQLException {
        connection.close();
    }

////////////////////////////INSERTAR RUTAS AUTOBUS /////////////////////////////
    public boolean insertarRutas(Coordenadas coords) throws SQLException, ParseException {
        String sql = "INSERT INTO m08_rutas VALUES (?, ?, ?, ?)";
        PreparedStatement stmt = connection.prepareStatement(sql);
        String date;
        stmt.setString(1, coords.getMatricula()); //stmt.setString(1, cli.getNombre);
        stmt.setDouble(2, coords.getLatitud());
        stmt.setDouble(3, coords.getLongitud());
        date = coords.getHora();
        Date data = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date);
        java.sql.Date sDate = convertUtilToSql(data);
        stmt.setDate(4, sDate);
        int res = stmt.executeUpdate();
        finalizarConexion();

        return (res == 1);
    }

/////////////////////////OBTENER RUTAS AUTOBUS////////////////////////////////////
    public List ObtenerRutaAutobus(String matricula) throws SQLException {
        List ruta = new ArrayList<>();
        ResultSet rset;

        String sql = "SELECT * FROM (SELECT matricula, latitud, longitud, hora FROM m08_rutas WHERE matricula LIKE ? ORDER BY hora DESC) WHERE ROWNUM <= 5";
        PreparedStatement stmt = getConnection().prepareStatement(sql);
        stmt.setString(1, matricula);
        rset = stmt.executeQuery();
        while (rset.next()) {
            ruta.add(new Coordenadas(rset.getString("matricula"), rset.getDouble("latitud"), rset.getDouble("longitud"), rset.getString("hora")));

        }
        finalizarConexion();
        return ruta;
    }

    public List ObtenerUltimaPosicionBuses() throws SQLException {
        List ultimasPosiciones = new ArrayList<>();
        ResultSet rset;

        String sql = "SELECT * FROM m08_rutas WHERE (matricula, hora) IN (SELECT matricula, MAX(hora) FROM m08_rutas GROUP BY matricula)";
        PreparedStatement stmt = getConnection().prepareStatement(sql);
        rset = stmt.executeQuery();
        while (rset.next()) {
            ultimasPosiciones.add(new Coordenadas(rset.getString("matricula"), rset.getDouble("latitud"), rset.getDouble("longitud"), rset.getString("hora")));
        }
        finalizarConexion();
        return ultimasPosiciones;
    }

    private java.sql.Date convertUtilToSql(java.util.Date uDate) {

        java.sql.Date sDate = new java.sql.Date(uDate.getTime());

        return sDate;
    }
}
