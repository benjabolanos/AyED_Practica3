package uabc.ic.benjaminbolanos.practica3;

/**
 * Clase que modela un Vehiculo que entra a un servicio de CarWash. Implementa
 * Comparable para poder comparar dos vehiculos.
 * @author benjabolanos
 */
public class Vehiculo implements Comparable<Vehiculo>{
    private int ID;
    private String tam;
    private boolean servExpress;
    private boolean VIP;
    private boolean lavado;
    private boolean aspirado;

    /**
     * Constructor que recibe cuatro argumentos e inicializa lavado
     * y aspirado en false.
     * @param ID ID del vehiculo
     * @param tam Tamaño del Vehiculo
     * @param servExpress Si su servicio es express
     * @param VIP Si es cliente preferente
     */
    public Vehiculo(int ID, String tam, boolean servExpress, boolean VIP) {
        this.ID = ID;
        this.tam = tam;
        this.servExpress = servExpress;
        this.VIP = VIP;
        lavado = false;
        aspirado = false;
    }
    
    @Override
    public String toString(){
        return "Tamaño: " + tam + "Servicio Express: " + servExpress + "VIP: " + VIP;
    }
    
    /**
     * Método para comparar dos vehiculos dependiendo si es VIP.
     * @param o
     * @return 
     */
    @Override
    public int compareTo(Vehiculo o) {
        if(VIP == o.isVIP()) return 0;
        else if(VIP && !o.isVIP()) return 1;
        else return -1;
    }

    public String getTam() {
        return tam;
    }

    public void setTam(String tam) {
        this.tam = tam;
    }

    public boolean isServExpress() {
        return servExpress;
    }

    public void setServExpress(boolean servExpress) {
        this.servExpress = servExpress;
    }

    public boolean isVIP() {
        return VIP;
    }

    public void setVIP(boolean VIP) {
        this.VIP = VIP;
    }

    public boolean isLavado() {
        return lavado;
    }

    public void setLavado(boolean lavado) {
        this.lavado = lavado;
    }

    public boolean isAspirado() {
        return aspirado;
    }

    public void setAspirado(boolean aspirado) {
        this.aspirado = aspirado;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
    
}
