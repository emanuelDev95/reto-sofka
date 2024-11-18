package co.com.sofka.cuenta.persistence.entities;

import jakarta.persistence.*;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Entity(name = "cuentas")

@Getter @Setter @NoArgsConstructor
public class Cuenta extends AbstractEntity <Long>{

    @Column(nullable = false, unique = true)
    private String numeroCuenta;
    @Enumerated(EnumType.STRING)
    private TipoCuenta tipoCuenta;
    private BigDecimal saldo;
    private Boolean estado;
    @Column(nullable = false)
    private Long clienteId;

    @OneToMany(mappedBy = "cuenta", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Movimiento> movimientos;

    @Builder
    public Cuenta(Long id, String numeroCuenta, TipoCuenta tipoCuenta, BigDecimal saldo, Boolean estado, Long clienteId, List<Movimiento> movimientos) {
        super(id);
        this.numeroCuenta = numeroCuenta;
        this.tipoCuenta = tipoCuenta;
        this.saldo = saldo;
        this.estado = estado;
        this.clienteId = clienteId;
        this.movimientos = movimientos;
    }
}
