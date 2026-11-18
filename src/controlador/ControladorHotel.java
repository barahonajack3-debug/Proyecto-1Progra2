/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import javax.swing.JOptionPane;
import modelo.GestorHotel;
import vista.Hotel.FrmHotel;
import vista.Login.FrmLogin;

/**
 *
 * @author USER
 */
public class ControladorHotel {
    private final FrmHotel vista;
    private final GestorHotel gestor;

    public ControladorHotel(FrmHotel vista) {
        this.vista = vista;
        this.gestor = GestorHotel.getInstancia();

        vista.getBtnCambiarUsuario().addActionListener(e -> cambiarUsuario());
    }

    private void cambiarUsuario() {
        int respuesta = JOptionPane.showConfirmDialog(vista,"Debe cerrar la sesión actual para ingresar con otro usuario.\n"+ 
            "Los datos registrados se conservarán.",
            "Cambiar usuario",JOptionPane.YES_NO_OPTION
        );
        if (respuesta != JOptionPane.YES_OPTION) {
            return;
        }
        gestor.cerrarSesion();
        vista.dispose();
        FrmLogin login = new FrmLogin();
        login.setVisible(true);
    }
}
