/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.util.ArrayList;

/**
 *
 * @author USER
 */
public class GestorHotel {
    
    //Única instancia
    private static final GestorHotel instancia = new GestorHotel();
    
    //Lista
    private final ArrayList<Recepcionista> recepcionistas;
    private final ArrayList<Huesped> huespedes = new ArrayList<>();
    
    //Usuaio con secion Activa
    private Recepcionista sesionActiva;
    
    //Constructor privado
    private GestorHotel(){
        recepcionistas=new ArrayList<>();
        
        recepcionistas.add(
                new Recepcionista("recepcion1","Hotel123")
        );
        recepcionistas.add(
                new Recepcionista("recepcion2","Hotel456")
        );
        sesionActiva=null;
    }
    
    //Devuelve unica instancia
    public static GestorHotel getInstancia(){
        return instancia;
    }
    
    //Funciones
    public synchronized boolean iniciarSesion(String usuario,String clave){
        //Validar inicio de sesion
        if(sesionActiva!=null){
            return false;
        }
        
        //Buscar usuario
        for (Recepcionista recepcionista: recepcionistas) {
            if(recepcionista.getUsuario().equals(usuario) 
                    && recepcionista.validarClave(clave)){
                sesionActiva=recepcionista;
                return true;
            }
        }
        return false;
    }
    
    public synchronized void cerrarSesion(){
        sesionActiva=null;
    }
    
    public synchronized Recepcionista getSesionActiva(){
            return sesionActiva;
    }

    public synchronized String registrarHuesped(String identificacion, String nombreCompleto,
                                               String telefono, String correo) {
        if (identificacion == null || nombreCompleto == null || telefono == null || correo == null) {
            return "Todos los campos son obligatorios.";
        }

        identificacion = identificacion.trim();
        nombreCompleto = nombreCompleto.trim();
        telefono = telefono.trim();
        correo = correo.trim();

        if (identificacion.isEmpty() || nombreCompleto.isEmpty()
                || telefono.isEmpty() || correo.isEmpty()) {
            return "Todos los campos son obligatorios.";
        }

        int arroba = correo.indexOf('@');
        int punto = correo.lastIndexOf('.');

        if (arroba <= 0 || arroba != correo.lastIndexOf('@')
                || punto <= arroba + 1 || punto == correo.length() - 1
                || correo.contains(" ")) {
            return "El correo debe tener un formato como ana@hotel.com.";
        }

        for (Huesped huesped : huespedes) {
            if (huesped.getIdentificacion().equals(identificacion)) {
                return "Ya existe un huésped con esa identificación.";
            }
        }

        huespedes.add(new Huesped(identificacion, nombreCompleto, telefono, correo));
        return "";
    }

    public synchronized ArrayList<Huesped> getHuespedes() {
        return new ArrayList<>(huespedes);
    }
}
