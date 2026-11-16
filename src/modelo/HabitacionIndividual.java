/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author USER
 */
public class HabitacionIndividual extends Habitacion{

    //Contructor
    public HabitacionIndividual(String numero) {
        super(numero);
    }

    @Override
    public int getCapacidad() {
        return 1;
    }

    @Override
    public long getTarifa() {
        return 25000;
    }

    @Override
    public String getTipo() {
        return "Individual";
    }
    
}
