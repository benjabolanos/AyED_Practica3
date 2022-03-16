package uabc.ic.benjaminbolanos.practica3;

import java.util.Scanner;

/**
 * Clase main de un carwash con un ciclo para correr cuantos días se requieran.
 * @author benjabolanos
 */
public class CarWash {
    public static void main(String[] args) {
        Control control = new Control();
        String respuesta = new String();
        Scanner scan = new Scanner(System.in);
        do{
            control.iniciarNuevoDia();
            control.correrDia();
            control.mostrarRegistrosDelDia();
            
            System.out.println("¿Iniciar un nuevo dia?(Si/No)");
            respuesta = scan.nextLine();
            
        }while(respuesta.toLowerCase().equals("si"));

    }
    
}
