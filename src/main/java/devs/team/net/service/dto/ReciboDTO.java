package devs.team.net.service.dto;


import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import devs.team.net.domain.enumeration.Estado;

/**
 * A DTO for the Recibo entity.
 */
public class ReciboDTO implements Serializable {

    private Long id;

    @NotNull
    private Integer numero;

    @NotNull
    private Estado estado;

    @NotNull
    private BigDecimal pagoanterior;

    @NotNull
    private BigDecimal pagoactual;

    @NotNull
    private BigDecimal total;

    @NotNull
    private Instant fechagenera;

    @NotNull
    private Instant fechapaga;

    @NotNull
    private Integer anio;

    @NotNull
    private Integer mes;

    private Long usuarioId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public BigDecimal getPagoanterior() {
        return pagoanterior;
    }

    public void setPagoanterior(BigDecimal pagoanterior) {
        this.pagoanterior = pagoanterior;
    }

    public BigDecimal getPagoactual() {
        return pagoactual;
    }

    public void setPagoactual(BigDecimal pagoactual) {
        this.pagoactual = pagoactual;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public Instant getFechagenera() {
        return fechagenera;
    }

    public void setFechagenera(Instant fechagenera) {
        this.fechagenera = fechagenera;
    }

    public Instant getFechapaga() {
        return fechapaga;
    }

    public void setFechapaga(Instant fechapaga) {
        this.fechapaga = fechapaga;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public Integer getMes() {
        return mes;
    }

    public void setMes(Integer mes) {
        this.mes = mes;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ReciboDTO reciboDTO = (ReciboDTO) o;
        if(reciboDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), reciboDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ReciboDTO{" +
            "id=" + getId() +
            ", numero=" + getNumero() +
            ", estado='" + getEstado() + "'" +
            ", pagoanterior=" + getPagoanterior() +
            ", pagoactual=" + getPagoactual() +
            ", total=" + getTotal() +
            ", fechagenera='" + getFechagenera() + "'" +
            ", fechapaga='" + getFechapaga() + "'" +
            ", anio=" + getAnio() +
            ", mes=" + getMes() +
            "}";
    }
}
