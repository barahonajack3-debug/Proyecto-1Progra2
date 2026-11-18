/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import hilos.SupervisorReservas;
import java.util.concurrent.atomic.AtomicBoolean;
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
    private final AtomicBoolean supervisionActiva = new AtomicBoolean(false);

    public ControladorHotel(FrmHotel vista) {
        this.vista = vista;
        this.gestor = GestorHotel.getInstancia();

        vista.getBtnCambiarUsuario().addActionListener(e -> cambiarUsuario());
    }

    public boolean ejecutarSupervision() {
        if (!supervisionActiva.compareAndSet(false, true)) {
            return false;
        }

        Thread supervisor = new Thread(new SupervisorReservas(), "SupervisorReservas");
        supervisor.setDaemon(true);
        supervisor.start();

        Thread vigilante = new Thread(() -> {
            try {
                supervisor.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                supervisionActiva.set(false);
            }
        }, "VigilanteSupervision");
        vigilante.setDaemon(true);
        vigilante.start();
        return true;
    }

    public boolean isSupervisionActiva() {
        return supervisionActiva.get();
    }

    private void cambiarUsuario() {
        int respuesta = JOptionPane.showConfirmDialog(vista,"Debe cerrar la sesión actual para ingresar con otro usuario.\n"+ 
            "Los datos registrados se conservarán.",
            "Cambiar usuario",JOptionPane.YES_NO_OPTION);
        if (respuesta != JOptionPane.YES_OPTION) {
            return;
        }
        gestor.cerrarSesion();
        vista.dispose();
        FrmLogin login = new FrmLogin();
        login.setVisible(true);
    }
}