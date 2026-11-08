/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author USER
 */
public class Recepcionista {
    //Atributos
    private String usuario; 
    private String clave;

    //Constructor
    public Recepcionista(String usuario, String clave) {
        this.usuario = usuario;
        this.clave = clave;
    }
    
    //Get
    public String getUsuario() {
        return usuario;
    }
    
    //Funcion validar clave
    public boolean validarClave(String claveIngresada){
        return clave.equals(claveIngresada);
    }
}