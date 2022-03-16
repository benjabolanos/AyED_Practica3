package uabc.ic.benjaminbolanos.practica3;

import java.util.PriorityQueue;

/**
 * Clase que modela una cola de acceso de un carwash. La cola utilizada es
 * PriorityQueue para darle prioridad a los vehiculos que sean VIP. Tiene un 
 * limite de 10 vehiculos.
 * @author benjabolanos
 */
public class Acceso {
    private final PriorityQueue<Vehiculo> cola;
    private final int limite;
    
    /**
     * Constructor que inicializa la cola y el limite.
     */
    public Acceso(){
        limite = 10;
        cola = new PriorityQueue(limite);
    }
    
    /**
     * Método para dar de alta un vehiculo. Solo permite dar de alta si los
     * vehiculos registrados son menos que el limite.
     * @param v Vehiculo a dar de alta.
     * @return True si el vehiculo fue dado de alta exitosamente.
     */
    public boolean alta(Vehiculo v){
        if(cola.size() < limite){
            return cola.offer(v);
        }
        return false;
    }
    
    /**
     * Método para dar de baja un vehiculo. Solo permite dar de baja si la cola
     * no está vacía.
     * @return Retorna un vehiculo si la cola no está vacia.
     */
    public Vehiculo baja(){
        if(!cola.isEmpty())
            return cola.poll();
        return null;
    }
    
    /**
     * Método para saber si la cola está vacía.
     * @return 
     */
    public boolean isEmpty(){
        return cola.isEmpty();
    }
    
    /**
     * Método que retorna el vehiculo al tope de la cola.
     * @return Vehiculo al tope de la cola.
     */
    public Vehiculo peek(){
        return cola.peek();
    }
}
