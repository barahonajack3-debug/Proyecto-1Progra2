package controlador;

import hilos.ActualizadorIndicadores;
import javax.swing.SwingUtilities;
import vista.Hotel.PnInicio;

public class ControladorInicio {

    private final PnInicio vista;
    private boolean procesosEnEjecucion;

    public ControladorInicio(PnInicio vista) {
        this.vista = vista;
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

        ActualizadorIndicadores actualizador =
                new ActualizadorIndicadores(vista);

        Thread hiloIndicadores = new Thread(() -> {
            actualizador.run();

            SwingUtilities.invokeLater(() -> {
                procesosEnEjecucion = false;
                vista.mostrarProcesosEnEjecucion(false);
            });
        }, "ActualizadorIndicadores");

        hiloIndicadores.start();
    }
}
