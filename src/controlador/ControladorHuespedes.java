/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import modelo.GestorHotel;
import vista.Hotel.PnHuespedes;

/**
 *
 * @author USER
 */
public class ControladorHuespedes implements ActionListener {

    private final PnHuespedes vista;
    private final GestorHotel gestor;

    public ControladorHuespedes(PnHuespedes vista) {
        this.vista = vista;
        this.gestor = GestorHotel.getInstancia();

        vista.getBtnRegistrarHuesped().addActionListener(this);
        vista.mostrarHuespedes(gestor.getHuespedes());
    }

    @Override
    public void actionPerformed(ActionEvent evento) {
        String error = gestor.registrarHuesped(
                vista.getIdentificacion(),
                vista.getNombreCompleto(),
                vista.getTelefono(),
                vista.getCorreo()
        );

        if (!error.isEmpty()) {
            vista.mostrarError(error);
            return;
        }

        vista.mostrarHuespedes(gestor.getHuespedes());
        vista.limpiarCampos();
    }
}
