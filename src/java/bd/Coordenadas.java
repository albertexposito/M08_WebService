/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bd;


/**
 *
 * @author Lluis_2
 */
public class Coordenadas {

    private String matricula;
    private double latitud;
    private double longitud;
    private String hora;

    public Coordenadas(String matricula, double latitud, double longitud, String hora) {
        this.matricula = matricula;
        this.latitud = latitud;
        this.longitud = longitud;
        this.hora = hora;
    }

    public Coordenadas() {
    }

    public String getMatricula() {
        return matricula;
    }

    public double getLatitud() {
        return latitud;
    }

    public double getLongitud() {
        return longitud;
    }
    
    public String getHora() {
        return hora;
    }

}
