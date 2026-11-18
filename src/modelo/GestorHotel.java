/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Único punto de acceso a los datos del hotel (patrón Singleton).
 *
 * @author USER
 */
public class GestorHotel {

    private static final GestorHotel instancia = new GestorHotel();

    private final ArrayList<Recepcionista> recepcionistas = new ArrayList<>();
    private final ArrayList<Huesped> huespedes = new ArrayList<>();
    private final ArrayList<Habitacion> habitaciones = new ArrayList<>();
    private final ArrayList<Reserva> reservas = new ArrayList<>();

    private Recepcionista sesionActiva;
    private int siguienteCodigo = 1;

    private GestorHotel() {
        recepcionistas.add(new Recepcionista("recepcion1", "Hotel123"));
        recepcionistas.add(new Recepcionista("recepcion2", "Hotel456"));
    }

    public static GestorHotel getInstancia() {
        return instancia;
    }

    // ---------------- Sesión ----------------

    public synchronized boolean iniciarSesion(String usuario, String clave) {

        if (sesionActiva != null) {
            return false;
        }

        for (Recepcionista recepcionista : recepcionistas) {
            if (recepcionista.getUsuario().equals(usuario)
                    && recepcionista.validarClave(clave)) {

                sesionActiva = recepcionista;
                return true;
            }
        }

        return false;
    }

    public synchronized void cerrarSesion() {
        sesionActiva = null;
    }

    public synchronized Recepcionista getSesionActiva() {
        return sesionActiva;
    }

    // ---------------- Huéspedes ----------------

    public synchronized String registrarHuesped(String identificacion,
            String nombreCompleto, String telefono, String correo) {

        if (identificacion == null || nombreCompleto == null
                || telefono == null || correo == null) {
            return "Todos los campos son obligatorios.";
        }

        identificacion = identificacion.trim();
        nombreCompleto = nombreCompleto.trim();
        telefono = telefono.trim();
        correo = correo.trim();

        if (identificacion.isEmpty() || nombreCompleto.isEmpty()
                || telefono.isEmpty() || correo.isEmpty()) {
            return "Todos los campos son obligatorios.";
        }

        int arroba = correo.indexOf('@');
        int ultimoArroba = correo.lastIndexOf('@');
        int punto = correo.lastIndexOf('.');

        if (arroba <= 0 || arroba != ultimoArroba
                || punto <= arroba + 1
                || punto == correo.length() - 1
                || correo.contains(" ")) {
            return "El correo debe tener un formato como ana@hotel.com.";
        }

        for (Huesped huesped : huespedes) {
            if (huesped.getIdentificacion().equals(identificacion)) {
                return "Ya existe un huésped con esa identificación.";
            }
        }

        huespedes.add(
                new Huesped(identificacion, nombreCompleto, telefono, correo)
        );

        return "";
    }

    public synchronized ArrayList<Huesped> getHuespedes() {
        return new ArrayList<>(huespedes);
    }

    // ---------------- Habitaciones ----------------

    public synchronized String registrarHabitacion(Habitacion habitacion) {

        if (habitacion == null || habitacion.getNumero().trim().isEmpty()) {
            return "El número de habitación es obligatorio.";
        }

        for (Habitacion existente : habitaciones) {
            if (existente.getNumero().equalsIgnoreCase(habitacion.getNumero())) {
                return "Ya existe una habitación con ese número.";
            }
        }

        habitaciones.add(habitacion);
        return "";
    }

    public synchronized ArrayList<Habitacion> getHabitaciones() {
        return new ArrayList<>(habitaciones);
    }

    // ---------------- Reservas ----------------

    public synchronized Reserva registrarReserva(
            Huesped huesped,
            Habitacion habitacion,
            int personas,
            LocalDate entrada,
            LocalDate salida) {

        if (huesped == null) {
            throw new IllegalArgumentException("Debe seleccionar un huésped.");
        }

        if (habitacion == null) {
            throw new IllegalArgumentException("Debe seleccionar una habitación.");
        }

        if (entrada == null || salida == null) {
            throw new IllegalArgumentException("Las fechas son obligatorias.");
        }

        if (entrada.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException(
                    "La fecha de entrada no puede ser anterior a hoy.");
        }

        if (!salida.isAfter(entrada)) {
            throw new IllegalArgumentException(
                    "La fecha de salida debe ser posterior a la entrada.");
        }

        if (personas <= 0) {
            throw new IllegalArgumentException(
                    "La cantidad de personas debe ser mayor que cero.");
        }

        if (personas > habitacion.getCapacidad()) {
            throw new IllegalArgumentException(
                    "La cantidad de personas supera la capacidad de la habitación.");
        }

        if (hayConflicto(habitacion, entrada, salida)) {
            throw new IllegalArgumentException(
                    "La habitación ya está ocupada durante ese período.");
        }

        String codigo = String.format("R%04d", siguienteCodigo);
        siguienteCodigo++;

        Reserva reserva = new Reserva(
                codigo,
                huesped,
                habitacion,
                personas,
                entrada,
                salida,
                sesionActiva
        );

        reservas.add(reserva);

        return reserva;
    }

    public synchronized boolean hayConflicto(
            Habitacion habitacion,
            LocalDate entrada,
            LocalDate salida) {

        for (Reserva reserva : reservas) {

            boolean bloquea = reserva.getEstado() == EstadoReserva.CONFIRMADA
                    || reserva.getEstado() == EstadoReserva.HOSPEDADO;

            if (!bloquea) {
                continue;
            }

            if (!reserva.getHabitacion().getNumero()
                    .equals(habitacion.getNumero())) {
                continue;
            }

            boolean seSuperpone
                    = entrada.isBefore(reserva.getFechaSalida())
                    && salida.isAfter(reserva.getFechaEntrada());

            if (seSuperpone) {
                return true;
            }
        }

        return false;
    }

    public synchronized ArrayList<Reserva> getReservas() {
        return new ArrayList<>(reservas);
    }

    public synchronized Reserva buscarReserva(String codigo) {

        if (codigo == null) {
            return null;
        }

        for (Reserva reserva : reservas) {
            if (reserva.getCodigo().equalsIgnoreCase(codigo.trim())) {
                return reserva;
            }
        }

        return null;
    }

    // ---------------- Vencimientos ----------------

    public synchronized int marcarVencidas(LocalDate fechaActual) {

        int cantidad = 0;

        for (Reserva reserva : reservas) {

            if (reserva.getEstado() == EstadoReserva.CONFIRMADA
                    && reserva.getFechaEntrada().isBefore(fechaActual)) {

                reserva.cambiarEstado(EstadoReserva.VENCIDA);
                cantidad++;
            }
        }

        return cantidad;
    }

    /**
     * Simulación informativa: no modifica el estado real de las reservas.
     */
    public synchronized ArrayList<Reserva> simularVencidas(
            LocalDate fechaReferencia) {

        ArrayList<Reserva> resultado = new ArrayList<>();

        for (Reserva reserva : reservas) {

            if (reserva.getEstado() == EstadoReserva.CONFIRMADA
                    && reserva.getFechaEntrada().isBefore(fechaReferencia)) {

                resultado.add(reserva);
            }
        }

        return resultado;
    }

    // ---------------- Consulta simple de disponibilidad ----------------

    /**
     * Método iterativo de apoyo para la consulta de disponibilidad. Daniel
     * puede reutilizarlo dentro de su método recursivo (RF-10).
     */
    public synchronized ArrayList<Habitacion> habitacionesDisponibles(
            LocalDate fecha) {

        ArrayList<Habitacion> disponibles = new ArrayList<>();

        for (Habitacion habitacion : habitaciones) {

            boolean ocupada = false;

            for (Reserva reserva : reservas) {

                boolean bloquea
                        = reserva.getEstado() == EstadoReserva.CONFIRMADA
                        || reserva.getEstado() == EstadoReserva.HOSPEDADO;

                if (!bloquea) {
                    continue;
                }

                boolean mismaHabitacion
                        = reserva.getHabitacion().getNumero()
                                .equals(habitacion.getNumero());

                boolean incluyeFecha
                        = !fecha.isBefore(reserva.getFechaEntrada())
                        && fecha.isBefore(reserva.getFechaSalida());

                if (mismaHabitacion && incluyeFecha) {
                    ocupada = true;
                    break;
                }
            }

            if (!ocupada) {
                disponibles.add(habitacion);
            }
        }

        return disponibles;
    }
}
