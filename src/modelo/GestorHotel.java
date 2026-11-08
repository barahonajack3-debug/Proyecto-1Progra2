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
    private Recepcionista seccionActiva;
    
    //Constructor privado
    private GestorHotel(){
        recepcionistas=new ArrayList<>();
        
        recepcionistas.add(
                new Recepcionista("recepcion1","Hotel123")
        );
        recepcionistas.add(
                new Recepcionista("recepcion2","Hotel456")
        );
        seccionActiva=null;
    }
    
    //Devuelve unica instancia
    public static GestorHotel getInstancia(){
        return instancia;
    }
}