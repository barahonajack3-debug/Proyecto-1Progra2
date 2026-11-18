/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.time.LocalDate;
import javax.swing.JOptionPane;
import modelo.GestorHotel;
import vista.Hotel.FrmHotel;
import vista.Login.FrmLogin;

/**
 *
 * @author USER
 */
public class ControladorLogin {
    //Atributos
    private final FrmLogin vista;
    private final GestorHotel gestor;

    //Constructor
    public ControladorLogin(FrmLogin vista) {
        this.vista = vista;
        this.gestor = GestorHotel.getInstancia();
        vista.getBtnIniciarSesion().addActionListener(e -> iniciarSesion());
    }

    //Funciones
    private void iniciarSesion() {
        String usuario = vista.getUsuario();
        String clave = vista.getClave();
        boolean correcto = gestor.iniciarSesion(usuario, clave);
        if (!correcto) {
            vista.mostrarError(
                    "Usuario o contraseña incorrectos, o ya existe una sesión activa.");
            vista.limpiarClave();
            return;
        }
        FrmHotel hotel = new FrmHotel();
        hotel.setRecepcionista(gestor.getSesionActiva().getUsuario());
        hotel.setVisible(true);
        int vencidas = gestor.marcarVencidas(LocalDate.now());
        JOptionPane.showMessageDialog(
                hotel,
                "Revisión de vencimientos realizada correctamente.\n"
                + "Reservas marcadas como vencidas: " + vencidas);
        vista.dispose();
    }
}