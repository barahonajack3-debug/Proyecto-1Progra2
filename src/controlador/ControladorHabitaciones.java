/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import modelo.GestorHotel;
import modelo.Habitacion;
import modelo.HabitacionFactory;
import vista.Hotel.Habitaciones;

/**
 *
 * @author USER
 */
public class ControladorHabitaciones implements ActionListener {
    //Atributos
    private final Habitaciones vista;
    private final GestorHotel gestor;
    
    //Constructor
    public ControladorHabitaciones(Habitaciones vista) {
        this.vista = vista;
        this.gestor = GestorHotel.getInstancia();
        vista.getBtnRegistrar().addActionListener(this);
        vista.getCmbTipo().addActionListener(e -> actualizarDatos());
        actualizarDatos();
        actualizarTabla();
    }

    private void actualizarDatos() {
        String tipo = (String) vista.getCmbTipo().getSelectedItem();
        Habitacion ejemplo = HabitacionFactory.crearHabitacion(tipo, "0");
        vista.setCapacidad(String.valueOf(ejemplo.getCapacidad()));
        vista.setTarifa(String.valueOf(ejemplo.getTarifa()));
    }

    @Override
    public void actionPerformed(ActionEvent evento) {
        String numero = vista.getNumero().trim();
        String tipo = (String) vista.getCmbTipo().getSelectedItem();
        if (numero.isEmpty()) {
            JOptionPane.showMessageDialog(vista,"El número de habitación es obligatorio.");
            return;
        }
        try {
            Habitacion habitacion = HabitacionFactory.crearHabitacion(tipo, numero);
            String error = gestor.registrarHabitacion(habitacion);
            if (!error.isEmpty()) {
                JOptionPane.showMessageDialog(vista, error);
                return;
            }
            actualizarTabla();
            vista.limpiar();
            JOptionPane.showMessageDialog(vista,"Habitación registrada correctamente.");
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(vista, e.getMessage());
        }
    }

    private void actualizarTabla() {
        vista.mostrarHabitaciones(gestor.getHabitaciones());
    }
}
