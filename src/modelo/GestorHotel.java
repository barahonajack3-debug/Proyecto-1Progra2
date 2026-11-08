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
}