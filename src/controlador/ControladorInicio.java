package controlador;

import hilos.ActualizadorIndicadores;
import hilos.SupervisorReservas;
import javax.swing.SwingUtilities;
import modelo.GestorHotel;
import vista.Hotel.PnInicio;

public class ControladorInicio {

    private final PnInicio vista;
    private final GestorHotel gestor;
    private boolean procesosEnEjecucion;

    public ControladorInicio(PnInicio vista) {
        this.vista = vista;
        this.gestor = GestorHotel.getInstancia();
        this.procesosEnEjecucion = false;

        vista.getBtnEjecutarProcesos().addActionListener(
                evento -> ejecutarProcesos()
        );
    }

    private void ejecutarProcesos() {
        if (procesosEnEjecucion) {
            return;
        }

        procesosEnEjecucion = true;
        vista.mostrarProcesosEnEjecucion(true);

        Thread hiloSupervisor = new Thread(
                new SupervisorReservas(), "SupervisorReservas");
        Thread hiloIndicadores = new Thread(
                new ActualizadorIndicadores(vista), "ActualizadorIndicadores");

        hiloSupervisor.start();
        hiloIndicadores.start();

        Thread coordinador = new Thread(() -> {
            try {
                hiloSupervisor.join();
                hiloIndicadores.join();

                SwingUtilities.invokeLater(() -> {
                    actualizarIndicadoresFinales();
                    procesosEnEjecucion = false;
                    vista.mostrarProcesosEnEjecucion(false);
                });
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "CoordinadorProcesos");

        coordinador.start();
    }

    private void actualizarIndicadoresFinales() {
        vista.mostrarIndicadores(
                gestor.getTotalHabitaciones(),
                gestor.getHabitacionesOcupadas(),
                gestor.getHabitacionesNoOcupadas(),
                gestor.getReservasConfirmadas(),
                gestor.getReservasVencidas(),
                gestor.calcularIngresosEsperados()
        );
    }
}
