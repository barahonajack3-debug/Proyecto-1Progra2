/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package modelo;

/**
 *
 * @author USER
 */
public enum EstadoReserva {
    CONFIRMADA("Confirmada"),
    HOSPEDADO("Hospedado"),
    FINALIZADA("Finalizada"),
    VENCIDA("Vencida");
    
    private final String texto;
    
    EstadoReserva(String texto){
        this.texto=texto;
    }
    
    public String toString(){
        return texto;
    }
}
