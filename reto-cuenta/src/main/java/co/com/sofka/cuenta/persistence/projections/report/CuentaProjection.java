package co.com.sofka.cuenta.persistence.projections.report;


import java.math.BigDecimal;

public interface CuentaProjection {
    String getNumeroCuenta();
    Long getClienteId();
    String getTipoCuenta();
    Boolean getEstado();
    BigDecimal getSaldoInicial();
    BigDecimal getSaldoDisponible();
}

