package devs.team.net.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import devs.team.net.domain.enumeration.Estado;

/**
 * A Recibo.
 */
@Entity
@Table(name = "recibo")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "recibo")
public class Recibo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "numero", nullable = false)
    private Integer numero;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private Estado estado;

    @NotNull
    @Column(name = "pagoanterior", precision=10, scale=2, nullable = false)
    private BigDecimal pagoanterior;

    @NotNull
    @Column(name = "pagoactual", precision=10, scale=2, nullable = false)
    private BigDecimal pagoactual;

    @NotNull
    @Column(name = "total", precision=10, scale=2, nullable = false)
    private BigDecimal total;

    @NotNull
    @Column(name = "fechagenera", nullable = false)
    private Instant fechagenera;

    @NotNull
    @Column(name = "fechapaga", nullable = false)
    private Instant fechapaga;

    @NotNull
    @Column(name = "anio", nullable = false)
    private Integer anio;

    @NotNull
    @Column(name = "mes", nullable = false)
    private Integer mes;

    @ManyToOne
    private Usuario usuario;

    @ManyToMany(mappedBy = "lecturamedidorRecibos")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<LecturaMedidor> lecturaMedidors = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumero() {
        return numero;
    }

    public Recibo numero(Integer numero) {
        this.numero = numero;
        return this;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public Estado getEstado() {
        return estado;
    }

    public Recibo estado(Estado estado) {
        this.estado = estado;
        return this;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public BigDecimal getPagoanterior() {
        return pagoanterior;
    }

    public Recibo pagoanterior(BigDecimal pagoanterior) {
        this.pagoanterior = pagoanterior;
        return this;
    }

    public void setPagoanterior(BigDecimal pagoanterior) {
        this.pagoanterior = pagoanterior;
    }

    public BigDecimal getPagoactual() {
        return pagoactual;
    }

    public Recibo pagoactual(BigDecimal pagoactual) {
        this.pagoactual = pagoactual;
        return this;
    }

    public void setPagoactual(BigDecimal pagoactual) {
        this.pagoactual = pagoactual;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public Recibo total(BigDecimal total) {
        this.total = total;
        return this;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public Instant getFechagenera() {
        return fechagenera;
    }

    public Recibo fechagenera(Instant fechagenera) {
        this.fechagenera = fechagenera;
        return this;
    }

    public void setFechagenera(Instant fechagenera) {
        this.fechagenera = fechagenera;
    }

    public Instant getFechapaga() {
        return fechapaga;
    }

    public Recibo fechapaga(Instant fechapaga) {
        this.fechapaga = fechapaga;
        return this;
    }

    public void setFechapaga(Instant fechapaga) {
        this.fechapaga = fechapaga;
    }

    public Integer getAnio() {
        return anio;
    }

    public Recibo anio(Integer anio) {
        this.anio = anio;
        return this;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public Integer getMes() {
        return mes;
    }

    public Recibo mes(Integer mes) {
        this.mes = mes;
        return this;
    }

    public void setMes(Integer mes) {
        this.mes = mes;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public Recibo usuario(Usuario usuario) {
        this.usuario = usuario;
        return this;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Set<LecturaMedidor> getLecturaMedidors() {
        return lecturaMedidors;
    }

    public Recibo lecturaMedidors(Set<LecturaMedidor> lecturaMedidors) {
        this.lecturaMedidors = lecturaMedidors;
        return this;
    }

    public Recibo addLecturaMedidor(LecturaMedidor lecturaMedidor) {
        this.lecturaMedidors.add(lecturaMedidor);
        lecturaMedidor.getLecturamedidorRecibos().add(this);
        return this;
    }

    public Recibo removeLecturaMedidor(LecturaMedidor lecturaMedidor) {
        this.lecturaMedidors.remove(lecturaMedidor);
        lecturaMedidor.getLecturamedidorRecibos().remove(this);
        return this;
    }

    public void setLecturaMedidors(Set<LecturaMedidor> lecturaMedidors) {
        this.lecturaMedidors = lecturaMedidors;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Recibo recibo = (Recibo) o;
        if (recibo.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), recibo.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Recibo{" +
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
