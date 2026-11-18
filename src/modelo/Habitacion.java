/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 * Clase base de todas las habitaciones del hotel.
 *
 * @author USER
 */
public abstract class Habitacion {

    private final String numero;

    public Habitacion(String numero) {
        this.numero = numero;
    }

    public String getNumero() {
        return numero;
    }

    public abstract int getCapacidad();

    public abstract long getTarifa();

    public abstract String getTipo();

    @Override
    public String toString() {
        return numero + " - " + getTipo();
    }
}
