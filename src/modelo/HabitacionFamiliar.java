/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author USER
 */
public class HabitacionFamiliar extends Habitacion{

    //Constructor;
    public HabitacionFamiliar(String numero) {
        super(numero);
    }

    @Override
    public int getCapacidad() {
        return 4;
    }

    @Override
    public long getTarifa() {
        return 65000;
    }

    @Override
    public String getTipo() {
        return "Familiar";
    } 
}