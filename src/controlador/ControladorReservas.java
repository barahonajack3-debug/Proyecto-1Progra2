/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import javax.swing.JOptionPane;
import modelo.GestorHotel;
import modelo.Habitacion;
import modelo.Huesped;
import modelo.Reserva;
import vista.Hotel.Reservas;

/**
 *
 * @author USER
 */
public class ControladorReservas implements ActionListener{
    //Atributos
    private final Reservas vista;
    private final GestorHotel gestor;

    private final DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/uuuu")
        .withResolverStyle(ResolverStyle.STRICT);

    //Constructor
    public ControladorReservas(Reservas vista) {
        this.vista = vista;
        this.gestor = GestorHotel.getInstancia();
        vista.getBtnRegistrar().addActionListener(this);
        cargarHuespedes();
        cargarHabitaciones();
        actualizarTabla();
    }

    private void cargarHuespedes() {
        vista.cargarHuespedes(gestor.getHuespedes());
    }

    private void cargarHabitaciones() {
        vista.cargarHabitaciones(gestor.getHabitaciones());
    }

    @Override
    public void actionPerformed(ActionEvent evento) {
        try {
            Huesped huesped = vista.getHuespedSeleccionado();
            Habitacion habitacion = vista.getHabitacionSeleccionada();
            int personas = Integer.parseInt(vista.getPersonas().trim());
            LocalDate entrada = LocalDate.parse(vista.getEntrada().trim(), formato);
            LocalDate salida = LocalDate.parse(vista.getSalida().trim(), formato);
            Reserva reserva = gestor.registrarReserva(huesped,habitacion,personas,
                entrada,salida);
            actualizarTabla();
            vista.limpiar();
            JOptionPane.showMessageDialog(vista,"Reserva " + reserva.getCodigo()
                + " registrada correctamente.");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(vista,"La cantidad de personas debe ser un número entero.");
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(vista,"Las fechas deben tener el formato dd/MM/aaaa.");
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(vista,e.getMessage());
        }
    }
    private void actualizarTabla() {
        vista.mostrarReservas(gestor.getReservas());
    }
}