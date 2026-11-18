/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 *
 * @author USER
 */
public class Reserva {
    //Atributos
    private final String codigo;
    private final Huesped huesped;
    private final Habitacion habitacion;
    private final int personas;
    private final LocalDate fechaEntrada;
    private final LocalDate fechaSalida;
    private final int noches;
    private final long importe;
    private final Recepcionista recepcionista;
    
    private LocalDate fechaSalidaReal;
    private EstadoReserva estado;
    
    //Constructor
    public Reserva(String codigo, Huesped huesped, Habitacion habitacion, 
            int personas, LocalDate fechaEntrada, LocalDate fechaSalida,
            Recepcionista recepcionista) {
        this.codigo = codigo;
        this.huesped = huesped;
        this.habitacion = habitacion;
        this.personas = personas;
        this.fechaEntrada = fechaEntrada;
        this.fechaSalida = fechaSalida;
        this.noches = (int) ChronoUnit.DAYS.between(fechaEntrada, fechaSalida);
        this.importe = this.noches * habitacion.getTarifa();
        this.recepcionista = recepcionista;
        this.fechaSalidaReal = null;
        this.estado = EstadoReserva.CONFIRMADA;
    }

    //Getters
    public String getCodigo() {
        return codigo;
    }

    public Huesped getHuesped() {
        return huesped;
    }

    public Habitacion getHabitacion() {
        return habitacion;
    }

    public int getPersonas() {
        return personas;
    }

    public LocalDate getFechaEntrada() {
        return fechaEntrada;
    }

    public LocalDate getFechaSalida() {
        return fechaSalida;
    }

    public int getNoches() {
        return noches;
    }

    public long getImporte() {
        return importe;
    }

    public Recepcionista getRecepcionista() {
        return recepcionista;
    }

    public LocalDate getFechaSalidaReal() {
        return fechaSalidaReal;
    }

    public EstadoReserva getEstado() {
        return estado;
    } 
    
    //Funciones
    public void cambiarEstado(EstadoReserva nuevoEstado){
        this.estado=nuevoEstado;
    }
    
    public void registrarSalidaReal(LocalDate fecha){
        this.fechaSalidaReal=fecha;
    }

    @Override
    public String toString() {
        return codigo + " - " + huesped.getNombreCompleto();
    } 
}
