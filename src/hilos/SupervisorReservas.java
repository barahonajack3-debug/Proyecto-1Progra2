/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hilos;

import java.time.LocalDate;
import modelo.GestorHotel;

/**
 *
 * @author USER
 */
public class SupervisorReservas implements Runnable{
    //Atributos
    private final GestorHotel gestor;
    
    //Constructor
    public SupervisorReservas() {
        this.gestor = GestorHotel.getInstancia();
    }

    @Override
    public void run() {
        String nombre = Thread.currentThread().getName();
        System.out.println(nombre + ": inicia ejecución.");
        for (int ciclo = 1; ciclo <= 5; ciclo++) {
            int vencidas = gestor.marcarVencidas(LocalDate.now());
            System.out.println(nombre+ ": ciclo " + ciclo+ " - reservas vencidas actualizadas: "
                + vencidas);
            if (ciclo < 5) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    System.out.println(nombre + ": ejecución interrumpida.");
                    Thread.currentThread().interrupt();
                    return;
                }
            }
        }
        System.out.println(nombre + ": finaliza ejecución.");
    }
}