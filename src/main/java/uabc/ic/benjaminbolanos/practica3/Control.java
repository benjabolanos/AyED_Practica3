package uabc.ic.benjaminbolanos.practica3;

import java.util.ArrayList;
import java.util.Random;

/**
 * Clase que modela el control de un CarWash.
 * @author benjabolanos
 */
public class Control {
    private int carrosRegistrados; //Cantidad de vehiculos registrados en la jornada.
    private final Acceso acceso;  //Linea de acceso
    private final Lavado lavado; //Maquina de lavado
    private final Aspirado aspirado; //Lineas de aspirado
    private final SecadoExpress express; //Linea de secado express
    
    private final ArrayList<Registro> registros; //Registros de la jornada
    
    private int dia;
    private int hora;
    private int min;
    private String tiempo; //Tiempo actual en String
    private int horaSalida;
    
    private int sigHoraEntrada;
    private int sigMinEntrada;
    private boolean aceptandoCarros;
    
    /**
     * Constructor que inicializa los atributos.
     */
    public Control(){
        acceso = new Acceso();
        lavado = new Lavado();
        aspirado = new Aspirado();
        express = new SecadoExpress();
        carrosRegistrados = 0;
        sigHoraEntrada = 0;
        sigMinEntrada = 0;
        aceptandoCarros = false;
        registros = new ArrayList();
        horaSalida = 18;
    }
    
    /**
     * Método que crea un vehiculo con datos aleatorios y aumenta el contador
     * de carros registrados.
     * @return Retorna un vehiculo creado con datos aleatorios.
     */
    public Vehiculo crearVehiculo(){
        Random r = new Random();
        boolean vip, servExpress;
        String tam;
        
        switch(r.nextInt(20)){
            case 0,1,2,3,4,5,6,7,8,9 -> tam = "S";
            case 10,11,12,13,14,15,16 -> tam = "M";
            default -> tam = "L";
        }
        servExpress = r.nextDouble() < 0.2;
        vip = r.nextDouble() < 0.15;
        
        carrosRegistrados++;
        return new Vehiculo(carrosRegistrados,tam, vip, servExpress);
    }
    
    /**
     * Método para insertar un vehiculo en la linea de acceso. Crea un vehiculo
     * y lo da de alta en la cola de acceso. Si hay exito, se imprime en consola.
     */
    private void insertarAcceso(){
        Vehiculo v = crearVehiculo();
        if(acceso.alta(v)){
            System.out.println(tiempo + "Vehiculo con ID:" + v.getID() + " ingresado en Acceso.");
        } else {
            carrosRegistrados--;
        }
    }
    
    /**
     * Método para insertar un vehiculo en la linea de lavado. Checa que el lavado
     * no esté lleno y que el siguiente vehiculo no sea nulo. Si hay exito, se
     * imprime en consola.
     */
    private void insertarLavado(){
        if(!lavado.isFull()){
            Vehiculo v1 = acceso.peek();
            if(v1 != null)
                if((v1.isServExpress() && !express.isFull()) || (!v1.isServExpress() && !aspirado.isFull())){
                    Vehiculo v = acceso.baja();
                    if(v != null && lavado.alta(v)){
                        System.out.println(tiempo + "Vehiculo con ID:" + v.getID() + " ingresado en Lavado.");
                    }
                }
        }
    }
    
    /**
     * Método que manda un vehiculo de la maquina de lavado a la linea de secado
     * o aspirado dependiendo del servicio requerido. Si hay exito, imprime en 
     * consola.
     */
    private void mandarVehiculosAspirado(){
        if(lavado.vehiculoEnServicio() != null && lavado.vehiculoEnServicio().isLavado()){
            Vehiculo v = lavado.baja();
            if(v.isServExpress()){
                if(!express.isFull()){
                    express.alta(v);
                    System.out.println(tiempo + "Vehiculo con ID:" + v.getID() + " ingresado en Secado Express.");
                }
            } else {
                if(!aspirado.isFull()){
                    aspirado.alta(v);
                    System.out.println(tiempo + "Vehiculo con ID:" + v.getID() + " ingresado en Aspirado.");
            
                }
            }
        }
    }
    
    /**
     * Método que retira vehiculos de las colas de secado y aspirado, con estos
     * crea un registro y lo guarda en el ArrayList de registros. Impre en consola
     * cuando un registro es creado.
     */
    private void registrarAutos(){
        for(int i = 0; i < 4; i++){
            Vehiculo v = aspirado.baja(i);
            if(v != null){
                registros.add(new Registro(v, hora, min));
                System.out.println(tiempo + "Vehiculo con ID:" + v.getID() + " Registrado.");
            }
        }
        Vehiculo v = express.baja();
        if(v != null){
            registros.add(new Registro(v, hora, min));
            System.out.println(tiempo + "Vehiculo con ID:" + v.getID() + " Registrado.");
        }
    }
    
    /**
     * Método para reinicar las variables necesarias al cambiar de día. Aumenta
     * el contador de días.
     */
    public void iniciarNuevoDia(){
        dia++;
        hora = 8;
        horaSalida = 18;
        min = 0;
        registros.clear();
        System.out.println("Iniciando dia "+dia+".");
    }
    
    /**
     * Método para simular una jornada/dia de trabajo.
     */
    public void correrDia(){
        calcularSigEntrada();
        for(hora = 8; hora<horaSalida; hora++){
            for(min = 0; min<60; min++){
                aceptandoCarros = !((hora >= 17) && (min>=45));
                actualizarTiempo();
                if(aceptandoCarros){
                    if(hora == sigHoraEntrada && min == sigMinEntrada){
                        insertarAcceso();
                        calcularSigEntrada();
                    }
                }
                insertarLavado();
                lavado.lavar();
                mandarVehiculosAspirado();
                aspirado.aspirar();
                express.aspirar();
                registrarAutos();
            }
            if(checarSiHorasExtras()) horaSalida++; //Si se necesita más tiempo, va añadiendo una hora.
        }
        System.out.println("Final del dia "+dia+".");
    }
    
    /**
     * Método para saber si se ocupan horas extras.
     * @return True si ya llegó el final del día pero todavía hay colas con vehiculos.
     */
    private boolean checarSiHorasExtras(){
        return hora>=17 && min == 60 && !checarSiColasVacias();
    }
    
    /**
     * Método para verificar si todas las colas están vacías.
     * @return True si todas las colas están vacías.
     */
    private boolean checarSiColasVacias(){
        return acceso.isEmpty() && lavado.isEmpty() && express.isEmpty() && aspirado.isEmpty();
    }
    
    /**
     * Método que calcula el tiempo en el que se creará el siguiente vehiculo.
     */
    private void calcularSigEntrada(){
        Random rand = new Random();
        int t = rand.nextInt(7)+2;
        if(min + t >= 59){
            sigHoraEntrada = hora + 1;
            sigMinEntrada = min + t - 59;
        } else {
            sigHoraEntrada = hora;
            sigMinEntrada = min + t;
        }
    }
    
    /**
     * Método que actualiza el String tiempo, para mostrar el tiempo actual.
     */
    private void actualizarTiempo(){
        String minString = new String();
        if(min < 10) minString = "0" + min;
        else minString = String.valueOf(min);
        tiempo = "[" + hora + ":" + minString + "] ";
    }
    
    /**
     * Método para mostrar todos los registros creados en un día.
     */
    public void mostrarRegistrosDelDia(){
        System.out.println("Registros del dia " + dia + ".");
        for(Registro r : registros){
            System.out.println(r.toString()+"\n");
        }
    }
}
