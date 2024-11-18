package co.com.sofka.cuenta.persistence.entities;

public enum TipoMovimiento {

    DEPOSITO("Deposito"),
    RETIRO("Retiro");

    private final String descripcion;

    TipoMovimiento(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    @Override
    public String toString() {
        return descripcion;
    }
}

