package devs.team.net.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

import devs.team.net.domain.enumeration.Estado;

/**
 * A CostoMedidor.
 */
@Entity
@Table(name = "costo_medidor")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "costomedidor")
public class CostoMedidor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "fecha", nullable = false)
    private Instant fecha;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private Estado estado;

    @ManyToOne
    private Costo costo;

    @ManyToOne
    private Medidor medidor;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getFecha() {
        return fecha;
    }

    public CostoMedidor fecha(Instant fecha) {
        this.fecha = fecha;
        return this;
    }

    public void setFecha(Instant fecha) {
        this.fecha = fecha;
    }

    public Estado getEstado() {
        return estado;
    }

    public CostoMedidor estado(Estado estado) {
        this.estado = estado;
        return this;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Costo getCosto() {
        return costo;
    }

    public CostoMedidor costo(Costo costo) {
        this.costo = costo;
        return this;
    }

    public void setCosto(Costo costo) {
        this.costo = costo;
    }

    public Medidor getMedidor() {
        return medidor;
    }

    public CostoMedidor medidor(Medidor medidor) {
        this.medidor = medidor;
        return this;
    }

    public void setMedidor(Medidor medidor) {
        this.medidor = medidor;
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
        CostoMedidor costoMedidor = (CostoMedidor) o;
        if (costoMedidor.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), costoMedidor.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CostoMedidor{" +
            "id=" + getId() +
            ", fecha='" + getFecha() + "'" +
            ", estado='" + getEstado() + "'" +
            "}";
    }
}
