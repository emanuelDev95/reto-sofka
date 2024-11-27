package co.com.sofka.cuenta.persistence.projections.report;


import java.math.BigDecimal;

public interface CuentaProjection {
    String getNumeroCuenta();
    String getTipoCuenta();
    Boolean getEstado();
    BigDecimal getSaldoInicial();
    BigDecimal getSaldoDisponible();
}

