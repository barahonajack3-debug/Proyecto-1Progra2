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
import modelo.Reserva;
import vista.Hotel.PnSimulacion;

/**
 *
 * @author USER
 */
public class ControladorSimulacion implements ActionListener{
    //Atributos
    private final PnSimulacion vista;
    private final GestorHotel gestor;

    private final DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/uuuu")
        .withResolverStyle(ResolverStyle.STRICT);

    //Constructor
    public ControladorSimulacion(PnSimulacion vista) {
        this.vista = vista;
        this.gestor = GestorHotel.getInstancia();
        vista.getBtnSimular().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent evento) {
        try {
            LocalDate fecha = LocalDate.parse(vista.getFechaReferencia().trim(),formato);
            java.util.ArrayList<Reserva> resultado =
            gestor.simularVencidas(fecha);
            vista.mostrarReservas(resultado, fecha);
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(vista,"La fecha debe tener el formato dd/MM/aaaa.");
        }
    }
}
