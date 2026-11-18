/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author USER
 */
public class GestorHotel {
    
    //Única instancia
    private static final GestorHotel instancia = new GestorHotel();
    
    //Lista
    private final ArrayList<Recepcionista> recepcionistas = new ArrayList<>();
    private final ArrayList<Huesped> huespedes = new ArrayList<>();
    private final ArrayList<Habitacion> habitaciones = new ArrayList<>();
    private final ArrayList<Reserva> reservas = new ArrayList<>();
    
    private Recepcionista sesionActiva;
    
    //Constructor privado
    private GestorHotel(){
        recepcionistas.add(new Recepcionista("recepcion1","Hotel123"));
        recepcionistas.add(new Recepcionista("recepcion2","Hotel456"));
    }
    
    //Devuelve unica instancia
    public static GestorHotel getInstancia(){
        return instancia;
    }
    
    //Funciones
    public synchronized boolean iniciarSesion(String usuario,String clave){
        //Validar inicio de sesion
        if(sesionActiva!=null){
            return false;
        }
        
        //Buscar usuario
        for (Recepcionista recepcionista: recepcionistas) {
            if(recepcionista.getUsuario().equals(usuario) 
                    && recepcionista.validarClave(clave)){
                sesionActiva=recepcionista;
                return true;
            }
        }
        return false;
    }
    
    public synchronized void cerrarSesion(){
        sesionActiva=null;
    }
    
    public synchronized Recepcionista getSesionActiva(){
        return sesionActiva;
    }

    //=====Huespedes=====
    public synchronized String registrarHuesped(String identificacion, String nombreCompleto,
            String telefono, String correo) {
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

        int arroba = correo.indexOf("@");
        int ultimoArroba = correo.lastIndexOf("@");
        int punto = correo.lastIndexOf(".");

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

        huespedes.add(new Huesped(identificacion, nombreCompleto, telefono, correo)
        );
        return "";
    }

    public synchronized ArrayList<Huesped> getHuespedes() {
        return new ArrayList<>(huespedes);
    }
    
    //======Habitaciones=====    
    public synchronized String registrarHabitacion(Habitacion habitacion){
        if(habitacion==null || habitacion.getNumero().trim().isEmpty()){
            return "El numero de habitacion es obligatorio.";
        }
        
        for(Habitacion existente : habitaciones){
            if(existente.getNumero().equalsIgnoreCase(habitacion.getNumero())){
                return "Ya existe una habitacion con ese numero.";
            }
        }  
        habitaciones.add(habitacion);
        return "";
    }
    
    public synchronized ArrayList<Habitacion> getHabitaciones(){
        return new ArrayList<>(habitaciones);
    }
    
    //=====Reservas=====    
    public synchronized Reserva registarReserva(Huesped huesped,Habitacion habitacion,
        int personas,LocalDate entrada,LocalDate salida){
        
        if(huesped==null){
            throw new IllegalArgumentException("Debe seleccionar un huesped."); 
        }
        if(habitacion==null){
            throw new IllegalArgumentException("Debe selecionar una habitacion");
        }
        if(entrada==null || salida==null){
            throw new IllegalArgumentException("Las fechas son obligatorias");
        }
        if(entrada.isBefore(LocalDate.now())){
            throw new IllegalArgumentException("La fecha de entrada no puede ser anterior a hoy");
        }
        if(!salida.isAfter(entrada)){
            throw new IllegalArgumentException("La fecha de salida debe ser posterior a hoy");
        }
        if(personas<=0){
            throw new IllegalArgumentException("La cantidad de personas deb ser mayor que cero");
        }
        if(personas > habitacion.getCapacidad()){
            throw new IllegalArgumentException("La cantidad de personas supera la capacidad");
        }
        if(hayConflicto(habitacion,entrada,salida)){
            throw new IllegalArgumentException("la habitacion ya esta ocupada durante ese periodo");
        }
        
        String codigo=String.format("R%04d",reservas.size()+1);
        
        Reserva reserva=new Reserva(codigo,huesped,habitacion,
            personas,entrada,salida,sesionActiva);
        reservas.add(reserva);
        return reserva;
    } 
    
    public synchronized boolean hayConflicto(Habitacion habitacion,
        LocalDate entrada,LocalDate salida){
        
        for(Reserva reserva : reservas){
            boolean bloquea = reserva.getEstado()==EstadoReserva.CONFIRMADA
                    || reserva.getEstado()==EstadoReserva.CONFIRMADA;
            if(!bloquea){
                continue;
            }
            if(!reserva.getHabitacion().getNumero()
                    .equals(habitacion.getNumero())){
                continue;
            }
            boolean seSuperpone=entrada.isBefore(reserva.getFechaSalida()) 
                    && salida.isAfter(reserva.getFechaEntrada());
            if(seSuperpone){
                return true;
            }
        }
        return false;
    } 
    
    public synchronized Reserva buscarReserva(String codigo){
        for(Reserva reserva : reservas){
            if(reserva.getCodigo().equalsIgnoreCase(codigo.trim())){
                return reserva;
            }
        }
        return null;
    }
    
    // ===== Check-in y check-out =====
    public synchronized String registrarEntrada(String codigo) {
        if (codigo == null || codigo.trim().isEmpty()) {
            return "Ingresa el código de la reserva.";
        }

        Reserva reserva = buscarReserva(codigo);

        if (reserva == null) {
            return "No existe una reserva con ese código.";
        }

        if (reserva.getEstado() != EstadoReserva.CONFIRMADA) {
            return "Solo se puede registrar la entrada de una reserva confirmada.";
        }

        LocalDate hoy = LocalDate.now();

        if (!hoy.equals(reserva.getFechaEntrada())) {
            return "La entrada debe registrarse el día de la fecha programada.";
        }

        reserva.cambiarEstado(EstadoReserva.HOSPEDADO);
        return "";
    }

    public synchronized String registrarSalida(String codigo) {
        if (codigo == null || codigo.trim().isEmpty()) {
            return "Ingresa el código de la reserva.";
        }

        Reserva reserva = buscarReserva(codigo);

        if (reserva == null) {
            return "No existe una reserva con ese código.";
        }

        if (reserva.getEstado() != EstadoReserva.HOSPEDADO) {
            return "Solo se puede registrar la salida de una reserva en estado Hospedado.";
        }

        reserva.registrarSalidaReal(LocalDate.now());
        reserva.cambiarEstado(EstadoReserva.FINALIZADA);
        return "";
    }

    // ===== Disponibilidad =====
    public synchronized int consultarDisponibilidad(
            LocalDate fecha, ArrayList<String> numeros) {

        numeros.clear();
        return contarDisponibles(fecha, numeros, 0);
    }

    private int contarDisponibles(
            LocalDate fecha, ArrayList<String> numeros, int indice) {

        // Caso base: ya revisamos todas las habitaciones.
        if (indice == habitaciones.size()) {
            return 0;
        }

        Habitacion habitacion = habitaciones.get(indice);
        int cantidad = 0;

        if (estaDisponible(habitacion, fecha)) {
            numeros.add(habitacion.getNumero());
            cantidad = 1;
        }

        // Sumamos esta habitación y revisamos la siguiente.
        return cantidad + contarDisponibles(fecha, numeros, indice + 1);
    }

    private boolean estaDisponible(Habitacion habitacion, LocalDate fecha) {

        for (Reserva reserva : reservas) {
            boolean mismaHabitacion = reserva.getHabitacion().getNumero()
                    .equalsIgnoreCase(habitacion.getNumero());

            boolean bloquea = reserva.getEstado() == EstadoReserva.CONFIRMADA
                    || reserva.getEstado() == EstadoReserva.HOSPEDADO;

            boolean dentroDelPeriodo = !fecha.isBefore(reserva.getFechaEntrada())
                    && fecha.isBefore(reserva.getFechaSalida());

            if (mismaHabitacion && bloquea && dentroDelPeriodo) {
                return false;
            }
        }

        return true;
    }

    // ===== Indicadores del hotel =====
    public synchronized int getTotalHabitaciones() {
        return habitaciones.size();
    }

    public synchronized int getHabitacionesOcupadas() {
        int cantidad = 0;

        for (Habitacion habitacion : habitaciones) {
            if (estaHospedada(habitacion)) {
                cantidad++;
            }
        }

        return cantidad;
    }

    private boolean estaHospedada(Habitacion habitacion) {
        for (Reserva reserva : reservas) {
            boolean mismaHabitacion = reserva.getHabitacion().getNumero()
                    .equalsIgnoreCase(habitacion.getNumero());

            if (mismaHabitacion
                    && reserva.getEstado() == EstadoReserva.HOSPEDADO) {
                return true;
            }
        }

        return false;
    }

    public synchronized int getHabitacionesNoOcupadas() {
        return habitaciones.size() - getHabitacionesOcupadas();
    }

    public synchronized int getReservasConfirmadas() {
        int cantidad = 0;

        for (Reserva reserva : reservas) {
            if (reserva.getEstado() == EstadoReserva.CONFIRMADA) {
                cantidad++;
            }
        }

        return cantidad;
    }

    public synchronized int getReservasVencidas() {
        int cantidad = 0;

        for (Reserva reserva : reservas) {
            if (reserva.getEstado() == EstadoReserva.VENCIDA) {
                cantidad++;
            }
        }

        return cantidad;
    }

    public synchronized long calcularIngresosEsperados() {
        return sumarIngresosConfirmados(reservas, 0);
    }

    private long sumarIngresosConfirmados(
            ArrayList<Reserva> listaReservas, int indice) {

        // Caso base: se revisaron todas las reservas.
        if (indice == listaReservas.size()) {
            return 0;
        }

        Reserva reserva = listaReservas.get(indice);
        long importe = 0;

        if (reserva.getEstado() == EstadoReserva.CONFIRMADA) {
            importe = reserva.getImporte();
        }

        // Sumamos el importe y revisamos la siguiente reserva.
        return importe
                + sumarIngresosConfirmados(listaReservas, indice + 1);
    }

    //======Vencimientos=====
    public synchronized int marcarVencidas(LocalDate fechaActual){
        int cantidad=0;
        for(Reserva reserva : reservas){
            if(reserva.getEstado()==EstadoReserva.CONFIRMADA 
                && reserva.getFechaEntrada().isBefore(fechaActual)){
                reserva.cambiarEstado(EstadoReserva.VENCIDA);
                cantidad++;
            }
        }
        return cantidad;
    }
    
    public synchronized ArrayList<Reserva> simularVencidad(LocalDate fechaReferencia) {
        ArrayList<Reserva> vencidas = new ArrayList<>();

        for (Reserva reserva : reservas) {
            if (reserva.getEstado() == EstadoReserva.CONFIRMADA
                    && reserva.getFechaEntrada().isBefore(fechaReferencia)) {
                vencidas.add(reserva);
            }
        }

        return vencidas;
    }
}
