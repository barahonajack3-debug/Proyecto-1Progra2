package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import modelo.EstadoReserva;
import modelo.GestorHotel;
import modelo.Reserva;
import vista.Hotel.PnCheck;

public class ControladorCheck implements ActionListener {

    private final PnCheck vista;
    private final GestorHotel gestor;
    private Reserva reservaSeleccionada;

    public ControladorCheck(PnCheck vista) {
        this.vista = vista;
        this.gestor = GestorHotel.getInstancia();
        vista.getBtnBuscar().addActionListener(this);
        vista.getBtnRegistrarEntrada().addActionListener(this);
        vista.getBtnRegistrarSalida().addActionListener(this);
        vista.limpiarDetalle();
    }

    @Override
    public void actionPerformed(ActionEvent evento) {
        if (evento.getSource() == vista.getBtnBuscar()) {
            buscarReserva();
        } else if (evento.getSource() == vista.getBtnRegistrarEntrada()) {
            registrarEntrada();
        } else if (evento.getSource() == vista.getBtnRegistrarSalida()) {
            registrarSalida();
        }
    }

    private void buscarReserva() {
        reservaSeleccionada = null;
        vista.limpiarDetalle();
        String codigo = vista.getCodigoReserva();
        if (codigo.isEmpty()) {
            vista.mostrarError("Ingresa el código de la reserva.");
            return;
        }
        reservaSeleccionada = gestor.buscarReserva(codigo);
        if (reservaSeleccionada == null) {
            vista.mostrarError("No existe una reserva con ese código.");
            return;
        }
        actualizarDetalle();
    }

    private void registrarEntrada() {
        if (!validarSeleccion()) {
            return;
        }
        String error = gestor.registrarEntrada(
                reservaSeleccionada.getCodigo());
        actualizarDetalle();
        if (!error.isEmpty()) {
            vista.mostrarError(error);
            return;
        }
        vista.mostrarMensaje("Entrada registrada correctamente.");
    }

    private void registrarSalida() {
        if (!validarSeleccion()) {
            return;
        }
        String error = gestor.registrarSalida(
                reservaSeleccionada.getCodigo());
        actualizarDetalle();
        if (!error.isEmpty()) {
            vista.mostrarError(error);
            return;
        }
        vista.mostrarMensaje("Salida registrada correctamente.");
    }

    private boolean validarSeleccion() {
        if (reservaSeleccionada == null) {
            vista.mostrarError("Primero busca una reserva.");
            return false;
        }
        if (!reservaSeleccionada.getCodigo()
                .equalsIgnoreCase(vista.getCodigoReserva())) {
            reservaSeleccionada = null;
            vista.limpiarDetalle();
            vista.mostrarError("El código cambió. Busca la reserva nuevamente.");
            return false;
        }
        return true;
    }

    private void actualizarDetalle() {
        vista.mostrarReserva(reservaSeleccionada);
        boolean permiteEntrada =
                reservaSeleccionada.getEstado() == EstadoReserva.CONFIRMADA;
        boolean permiteSalida =
                reservaSeleccionada.getEstado() == EstadoReserva.HOSPEDADO;
        vista.habilitarBotones(permiteEntrada, permiteSalida);
    }
}
