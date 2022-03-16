package uabc.ic.benjaminbolanos.practica3;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * Clase que modela una cola de secado express con una capacidad de 5. a cola utilizada
 * es ArrayBlockingQueue.
 * @author benjabolanos
 */
public class SecadoExpress {
    private final ArrayBlockingQueue<Vehiculo> cola;
    private boolean maquinaOcupada;
    private int tiempoRestanteSecado;
    
    /**
     * Constructor sin argumento que inicializa la cola y demas atributos.
     */
    public SecadoExpress(){
        cola = new ArrayBlockingQueue(5);
        maquinaOcupada = false;
        tiempoRestanteSecado = 3;
    }
    
    /**
     * Método para dar de alta un vehiculo. Retorna true si el vehiculo pudo
     * ser dado de alta.
     * @param v Vehiculo a dar de alta
     * @return True si el vehiculo fue dado de alta exitosamente.
     */
    public boolean alta(Vehiculo v){
        return cola.offer(v);
    }
    
    /**
     * Método para dar de baja un vehiculo. Solo permite dar de baja si
     * la cola no está vacía y el vehiculo está secado.
     * @return Retorna un vehiculo si el tope fue dado de baja con exito.
     */
    public Vehiculo baja(){
        if(!cola.isEmpty() && vehiculoEnServicio().isAspirado())
            return cola.poll();
        return null;
    }
    
    /**
     * Método para saber si la cola está llena.
     * @return True si la capacidad restante es igual a 0.
     */
    public boolean isFull(){
        return cola.remainingCapacity() == 0;
    }
    
    /**
     * Método para aspirar un vehiculo. Si el vehiculo en servicio no es nulo,
     * se verifica si la maquina esta ocupada. En caso de estar ocupada decrementa
     * el contador de tiempoRestante. Al llegar a 0 se desocupa la maquina
     * y reinicia el tiempo.
     */
    public void aspirar(){
        if(vehiculoEnServicio() != null){
            if(maquinaOcupada){
                tiempoRestanteSecado--;
                if(tiempoRestanteSecado == 0){
                    tiempoRestanteSecado = 3;
                    maquinaOcupada = false;
                    vehiculoEnServicio().setAspirado(true);
                }
            } else {
                maquinaOcupada = true;
            }
        }
    }
    
    /**
     * Método que retorna el vehiculo en servicio.
     * @return Vehiculo en tope de la cola.
     */
    public Vehiculo vehiculoEnServicio(){
        return cola.peek();
    }
    
    /**
     * Método para saber si la cola está vacía.
     * @return True si la cola está vacía.
     */
    public boolean isEmpty(){
        return cola.isEmpty();
    }
}
