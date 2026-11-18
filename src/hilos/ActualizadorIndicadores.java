package hilos;

import javax.swing.SwingUtilities;
import modelo.GestorHotel;
import vista.Hotel.PnInicio;

public class ActualizadorIndicadores implements Runnable {

    private final PnInicio vista;
    private final GestorHotel gestor;

    public ActualizadorIndicadores(PnInicio vista) {
        this.vista = vista;
        this.gestor = GestorHotel.getInstancia();
    }

    @Override
    public void run() {
        String nombreHilo = Thread.currentThread().getName();

        System.out.println(nombreHilo + ": inicia la actualización.");

        for (int ciclo = 1; ciclo <= 5; ciclo++) {
            int total = gestor.getTotalHabitaciones();
            int ocupadas = gestor.getHabitacionesOcupadas();
            int noOcupadas = gestor.getHabitacionesNoOcupadas();
            int confirmadas = gestor.getReservasConfirmadas();
            int vencidas = gestor.getReservasVencidas();
            long ingresos = gestor.calcularIngresosEsperados();

            SwingUtilities.invokeLater(() -> {
                vista.mostrarIndicadores(
                        total,
                        ocupadas,
                        noOcupadas,
                        confirmadas,
                        vencidas,
                        ingresos
                );
            });

            System.out.println(
                    nombreHilo + ": indicadores actualizados, ciclo "
                    + ciclo + " de 5."
            );

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println(nombreHilo + ": ejecución interrumpida.");
                return;
            }
        }

        System.out.println(nombreHilo + ": finaliza la actualización.");
    }
}
