package co.com.sofka.cuenta.persistence.entities;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "cuentas")
@Getter
@Setter
@NoArgsConstructor
public class Cuenta extends AbstractEntity<Long> {

    @Column(nullable = false, unique = true)
    private String numeroCuenta;

    @Enumerated(EnumType.STRING)
    private TipoCuenta tipoCuenta;
    private BigDecimal saldoDisponible;
    private BigDecimal saldoInicial;
    private Boolean estado;

    @Column(nullable = false)
    private Long clienteId;

    @OneToMany(mappedBy = "cuenta", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Movimiento> movimientos = new ArrayList<>();

    @Builder
    public Cuenta(Long id, String numeroCuenta, TipoCuenta tipoCuenta, BigDecimal saldoDisponible,
                  BigDecimal saldoInicial, Boolean estado, Long clienteId, List<Movimiento> movimientos) {
        super(id);
        this.numeroCuenta = numeroCuenta;
        this.tipoCuenta = tipoCuenta;
        this.saldoDisponible = saldoDisponible;
        this.saldoInicial = saldoInicial;
        this.estado = estado;
        this.clienteId = clienteId;
        this.movimientos = movimientos;
    }

    @Override
    public String toString() {
        return "Cuenta{" +
                "numeroCuenta='" + numeroCuenta + '\'' +
                ", tipoCuenta=" + tipoCuenta +
                ", saldoDisponible=" + saldoDisponible +
                ", saldoInicial=" + saldoInicial +
                ", estado=" + estado +
                ", clienteId=" + clienteId +
                ", movimientos=" + movimientos +
                '}';
    }
}

