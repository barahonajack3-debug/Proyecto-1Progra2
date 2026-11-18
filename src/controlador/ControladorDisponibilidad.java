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
import vista.Hotel.PnDisponibilidad;

/**
 *
 * @author USER
 */
public class ControladorDisponibilidad implements ActionListener {

    private final PnDisponibilidad vista;
    private final DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/uuuu")
            .withResolverStyle(ResolverStyle.STRICT);

    public ControladorDisponibilidad(PnDisponibilidad vista) {
        this.vista = vista;
        vista.getBtnConsultar().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent evento) {
        try {
            LocalDate fecha = LocalDate.parse(vista.getFechaConsulta().trim(), formato);
            vista.mostrarAviso("Consulta pendiente para " + fecha.format(formato));
        } catch (DateTimeParseException e) {
            vista.mostrarErrorFecha();
        }
    }
}
