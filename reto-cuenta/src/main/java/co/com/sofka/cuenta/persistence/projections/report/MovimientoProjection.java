package co.com.sofka.cuenta.persistence.projections.report;
import java.math.BigDecimal;
import java.util.Date;

public interface MovimientoProjection {
    Date getFecha();
    BigDecimal getValor();
    CuentaProjection getCuenta();
}

