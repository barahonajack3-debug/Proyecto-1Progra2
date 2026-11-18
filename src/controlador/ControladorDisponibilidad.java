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
import java.util.ArrayList;
import modelo.GestorHotel;
import vista.Hotel.PnDisponibilidad;

/**
 *
 * @author USER
 */
public class ControladorDisponibilidad implements ActionListener {

    private final PnDisponibilidad vista;
    private final GestorHotel gestor;
    private final DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/uuuu")
            .withResolverStyle(ResolverStyle.STRICT);

    public ControladorDisponibilidad(PnDisponibilidad vista) {
        this.vista = vista;
        this.gestor = GestorHotel.getInstancia();
        vista.getBtnConsultar().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent evento) {
        LocalDate fecha;

        try {
            fecha = LocalDate.parse(vista.getFechaConsulta().trim(), formato);
        } catch (DateTimeParseException e) {
            vista.mostrarErrorFecha();
            return;
        }

        ArrayList<String> numeros = new ArrayList<>();
        int cantidad = gestor.consultarDisponibilidad(fecha, numeros);

        vista.mostrarDisponibilidad(cantidad, numeros);
    }
}
