/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author USER
 */
public abstract class Habitacion {
    //Atributos
    private final String numero;
    
    //Constructor
    public Habitacion(String numero){
        this.numero=numero;
    }
    
    //Get
    public String getNumero() {
        return numero;
    }
    
    public abstract int getCapacidad();
    public abstract long getTarifa();
    public abstract String getTipo();

    @Override
    public String toString() {
        return "Habitacion{" + "numero=" + numero + '}';
    }    
}