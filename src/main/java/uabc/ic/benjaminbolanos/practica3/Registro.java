package uabc.ic.benjaminbolanos.practica3;

/**
 * Clase que modela un registro de salida de un vehiculo. Recibe los datos
 * del vehiculo y su tiempo de salida.
 * @author benjabolanos
 */
public class Registro {
    private Vehiculo vehiculo;
    private int horaSalida;
    private int minSalida;

    /**
     * Constructor que recibe el vehiculo del registro y su tiempo de salida.
     * @param v Vehiculo a registrar.
     * @param horaSalida Hora de salida.
     * @param minSalida Minuto de salida.
     */
    public Registro(Vehiculo v, int horaSalida, int minSalida) {
        this.vehiculo = v;
        this.horaSalida = horaSalida;
        this.minSalida = minSalida;
    }
    
    @Override
    public String toString(){
        String minString = new String(), tipoServicio = new String();
        if(minSalida < 10) minString = "0" + minSalida;
        else minString = String.valueOf(minSalida);
        
        if(vehiculo.isServExpress()) tipoServicio = "Secado Express";
        else tipoServicio = "Aspirado";
        
        return "Vehiculo con ID: " + vehiculo.getID() + "\nTiempo salida: [" 
                + horaSalida + ":" + minString + "]\nServicio Otorgado: "+tipoServicio
                +"\nCliente Preferente: "+vehiculo.isVIP() + "\nTamaño del Vehiculo: " + vehiculo.getTam();
    }
    
    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(Vehiculo v) {
        this.vehiculo = v;
    }

    public int getHoraSalida() {
        return horaSalida;
    }

    public void setHoraSalida(int horaSalida) {
        this.horaSalida = horaSalida;
    }

    public int getMinSalida() {
        return minSalida;
    }

    public void setMinSalida(int minSalida) {
        this.minSalida = minSalida;
    }
}
