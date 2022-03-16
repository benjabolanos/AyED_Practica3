package uabc.ic.benjaminbolanos.practica3;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * Clase para modelar las colas de aspirado de un CarWash. La cola utilizada es
 * ArrayBlockingQueue en un array para simular las cuatro colas.
 * @author benjabolanos
 */
public class Aspirado {
    private ArrayBlockingQueue<Vehiculo>[] colas;
    private final int[] tiemposAspirado;
    private final boolean[] maquinasOcupadas;
    
    /**
     * Constructor que manda a iniciar las colas y los demas array de datos.
     */
    public Aspirado(){
        iniciarColas();
        tiemposAspirado = new int[4];
        maquinasOcupadas = new boolean[]{false, false, false, false};
    }
    
    /**
     * Método para iniciar las colas con una capacidad de 4.
     */
    private void iniciarColas(){
        colas = new ArrayBlockingQueue[4];
        for(int i = 0; i<4; i++) colas[i] = new ArrayBlockingQueue<>(4);
    }
    
    /**
     * Método para buscar que cola tiene menos vehiculos. Como va checando
     * desde la primera linea, asi va la prioridad.
     * @return El numero de la cola que contenga menos vehiculos.
     */
    public int buscarColaDisponible(){
        int minimo = 10, colaDisponible = 0;
        for(int i = 1; i < colas.length; i++){
            if(colas[i].size() < minimo && colas[i].remainingCapacity() > 0){
                minimo = colas[i].size();
                colaDisponible = i;
            }
        }
        return colaDisponible;
    }
    
    /**
     * Método para dar de alta un vehiculo. Busca una cola disponible e inserta
     * el vehiculo. Retorna true si el vehiculo fue dado de alta con exito.
     * @param v Vehiculo a dar de alta.
     * @return True si el vehiculo fue dado de alta.
     */
    public boolean alta(Vehiculo v){
        int linea = buscarColaDisponible();
        return colas[linea].offer(v);
    }
    
    /**
     * Método para dar de baja un vehiculo dependiendo de la linea ingresada.
     * Checa que el tope de la linea no sea nulo y que el vehiculo esté aspirado.
     * @param linea Linea de donde se dará de baja el vehiculo.
     * @return Retorna el vehiculo dado de baja.
     */
    public Vehiculo baja(int linea){
        if(vehiculoEnServicio(linea) != null && vehiculoEnServicio(linea).isAspirado()){
            return colas[linea].poll();
        }
        return null;
    }
    
    /**
     * Método para saber si las colas están llenas.
     * @return True si la capacidad restante de todas las colas es 0.
     */
    public boolean isFull(){
        int lineasLlenas = 0;
        for (ArrayBlockingQueue<Vehiculo> cola : colas) {
            if (cola.remainingCapacity() == 0) {
                lineasLlenas++;
            }
        }
        return lineasLlenas == colas.length;
    }
    
    /**
     * Método para aspirar los vehiculos en las cuatro lineas.
     */
    public void aspirar(){
        for(int i = 0; i < 4; i++){
            aspirar(i);
        }
    }
    
    /**
     * Método para aspirar un vehiculo de una linea.Si el vehiculo en servicio en esa linea no es nulo,
     * se verifica si la maquina de esa linea está ocupada. En caso de estar ocupada decrementa
     * el contador de tiempoRestante de esa linea. Al llegar a 0 se desocupa la maquina
     * y reinicia el tiempo.
     * @param i Linea que contiene el vehiculo a aspirar.
     */
    public void aspirar(int i){
        if(vehiculoEnServicio(i) != null){
            if(maquinasOcupadas[i]){
                tiemposAspirado[i]--;
                if(tiemposAspirado[i] == 0){
                    maquinasOcupadas[i] = false;
                    vehiculoEnServicio(i).setAspirado(true);
                }
            } else {
                maquinasOcupadas[i] = true;
                calcularTiempoAspirado(i);
            }
        }
    }
    
    /**
     * Método que retorna el vehiculo en servicio de cierta linea.
     * @param linea Linea del cual se requiere el tope.
     * @return Retorna el tope de cierta cola.
     */
    public Vehiculo vehiculoEnServicio(int linea){
        return colas[linea].peek();
    }
    
    /**
     * Método para calcular el tiempo de aspirado dependiendo del tamaño 
     * de un vehiculo.
     * @param linea Linea del cual se obtiene el vehiculo.
     */
    public void calcularTiempoAspirado(int linea){
        String tam = vehiculoEnServicio(linea).getTam();
        switch(tam){
            case "S" -> tiemposAspirado[linea] = 5;
            case "M" -> tiemposAspirado[linea] = 7;
            default -> tiemposAspirado[linea] = 10;
        }
    }
    
    /**
     * Método para saber si las colas están vacías.
     * @return True si la cola está vacía.
     */
    public boolean isEmpty(){
        for(ArrayBlockingQueue<Vehiculo> cola : colas){
            if(!cola.isEmpty()) return false;
        }
        return true;
    }
}
