package co.com.sofka.cuenta.persistence.entities;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Entity(name = "movimientos")
@Getter @Setter @NoArgsConstructor
public class Movimiento extends AbstractEntity<Long>{

    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @Enumerated(EnumType.STRING)
    private TipoMovimiento tipoMovimiento;
    private BigDecimal valor;

    @ManyToOne
    @JoinColumn(name = "cuenta_id", nullable = false)
    private Cuenta cuenta;

    @Builder
    public Movimiento(Long id, Date fecha, TipoMovimiento tipoMovimiento, BigDecimal valor, Cuenta cuenta) {
        super(id);
        this.fecha = fecha;
        this.tipoMovimiento = tipoMovimiento;
        this.valor = valor;
        this.cuenta = cuenta;
    }
}
