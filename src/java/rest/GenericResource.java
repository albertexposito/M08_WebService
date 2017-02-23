/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import bd.Coordenadas;
import bd.Conexion;
import com.google.gson.Gson;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import static javax.ws.rs.HttpMethod.POST;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * @author Lluis_2
 */
@Path("generic")
public class GenericResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of GenericResource
     */
    public GenericResource() {
    }

    ////////////////
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    //public String listarClientes(@PathParam("id") String mat) {
    public String obtenerRutasAutobus(@PathParam("id") String mat) {
        Conexion conexion = new Conexion();
        List<Coordenadas> lista = null;
        try {
            lista = conexion.ObtenerRutaAutobus(mat);
        } catch (SQLException ex) {
            Logger.getLogger(GenericResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        Gson gson = new Gson();

        return gson.toJson(lista);
    }

    /////
    @GET
    @Path("/allBuses")
    @Produces(MediaType.APPLICATION_JSON)
    //public String listarClientes(@PathParam("id") String mat) {
    public String obtenerUltimasPosiciones() {
        Conexion conexion = new Conexion();
        List<Coordenadas> lista = null;
        try {
            lista = conexion.ObtenerUltimaPosicionBuses();
        } catch (SQLException ex) {
            Logger.getLogger(GenericResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        Gson gson = new Gson();

        return gson.toJson(lista);
    }

    ////////////////
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public boolean insertarCoordenadas(String loc) throws ParseException {
        Conexion conexion = new Conexion();
        Gson gson = new Gson();
        Coordenadas coords;
        coords = gson.fromJson(loc, Coordenadas.class);
        boolean result = true;
        try {
            conexion.insertarRutas(coords);
        } catch (SQLException ex) {
            Logger.getLogger(GenericResource.class.getName()).log(Level.SEVERE, null, ex);
            result = false;
        }
        return result;
    }

}
