package uabc.ic.benjaminbolanos.practica3;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * Clase que modela una cola de maquina de lavado en un CarWash. La cola utilizada
 * es ArrayBlockingQueue ya que permite inicializar la cola con un limite.
 * @author benjabolanos
 */
public class Lavado {
    private final ArrayBlockingQueue<Vehiculo> cola;
    private boolean maquinaOcupada;
    private int tiempoRestanteLavado;
    
    /**
     * Constructor que inicializa la cola con un limite de 3. La maquina desocupada
     * y el tiempo de lavado en 3 ciclos.
     */
    public Lavado(){
        cola = new ArrayBlockingQueue(3);
        maquinaOcupada = false;
        tiempoRestanteLavado = 3;
    }
    
    /**
     * Método para dar de alta un vehiculo. Solo permite dar de alta
     * si el vehiculo mandado no es nulo.
     * @param v Vehiculo a dar de alta.
     * @return True si el vehiculo fue registrado con exito.
     */
    public boolean alta(Vehiculo v){
        if(v != null)
            return cola.offer(v);
        return false;
    }
    
    /**
     * Método para dar de baja un vehiculo. Solo permite dar de baja si
     * la cola no está vacía y el vehiculo está lavado.
     * @return Retorna un vehiculo si el tope fue dado de baja con exito.
     */
    public Vehiculo baja(){
        if(!cola.isEmpty() && vehiculoEnServicio().isLavado()){
            return cola.poll();
        }
        return null;
    }
    
    /**
     * Método para lavar un vehiculo. Si el vehiculo en servicio no es nulo,
     * se verifica si la maquina esta ocupada. En caso de estar ocupada decrementa
     * el contador de tiempoRestante. Al llegar a 0 se desocupa la maquina
     * y reinicia el tiempo.
     */
    public void lavar(){
        if(vehiculoEnServicio() != null)
            if(maquinaOcupada){
                tiempoRestanteLavado--;
                if(tiempoRestanteLavado == 0){
                    tiempoRestanteLavado = 3;
                    maquinaOcupada = false;
                    vehiculoEnServicio().setLavado(true);
                }
            } else {
                maquinaOcupada = true;
            }
    }
    
    /**
     * Método para saber si la cola está llena.
     * @return True si la capacidad restante es igual a 0.
     */
    public boolean isFull(){
        return cola.remainingCapacity() == 0;
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
