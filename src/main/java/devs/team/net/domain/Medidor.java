package devs.team.net.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Medidor.
 */
@Entity
@Table(name = "medidor")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "medidor")
public class Medidor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "numeromedidor", nullable = false)
    private Integer numeromedidor;

    @NotNull
    @Column(name = "fechaadquirio", nullable = false)
    private Instant fechaadquirio;

    @NotNull
    @Column(name = "fechaactual", nullable = false)
    private Instant fechaactual;

    @OneToMany(mappedBy = "medidor")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<CostoMedidor> medidorCostoMedidors = new HashSet<>();

    @OneToMany(mappedBy = "medidor")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<LecturaMedidor> medidorLecturaMedidors = new HashSet<>();

    @ManyToOne
    private Usuario usuario;

    @ManyToOne
    private Sector sector;

    @ManyToOne
    private Clasificacion clasificacion;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumeromedidor() {
        return numeromedidor;
    }

    public Medidor numeromedidor(Integer numeromedidor) {
        this.numeromedidor = numeromedidor;
        return this;
    }

    public void setNumeromedidor(Integer numeromedidor) {
        this.numeromedidor = numeromedidor;
    }

    public Instant getFechaadquirio() {
        return fechaadquirio;
    }

    public Medidor fechaadquirio(Instant fechaadquirio) {
        this.fechaadquirio = fechaadquirio;
        return this;
    }

    public void setFechaadquirio(Instant fechaadquirio) {
        this.fechaadquirio = fechaadquirio;
    }

    public Instant getFechaactual() {
        return fechaactual;
    }

    public Medidor fechaactual(Instant fechaactual) {
        this.fechaactual = fechaactual;
        return this;
    }

    public void setFechaactual(Instant fechaactual) {
        this.fechaactual = fechaactual;
    }

    public Set<CostoMedidor> getMedidorCostoMedidors() {
        return medidorCostoMedidors;
    }

    public Medidor medidorCostoMedidors(Set<CostoMedidor> costoMedidors) {
        this.medidorCostoMedidors = costoMedidors;
        return this;
    }

    public Medidor addMedidorCostoMedidor(CostoMedidor costoMedidor) {
        this.medidorCostoMedidors.add(costoMedidor);
        costoMedidor.setMedidor(this);
        return this;
    }

    public Medidor removeMedidorCostoMedidor(CostoMedidor costoMedidor) {
        this.medidorCostoMedidors.remove(costoMedidor);
        costoMedidor.setMedidor(null);
        return this;
    }

    public void setMedidorCostoMedidors(Set<CostoMedidor> costoMedidors) {
        this.medidorCostoMedidors = costoMedidors;
    }

    public Set<LecturaMedidor> getMedidorLecturaMedidors() {
        return medidorLecturaMedidors;
    }

    public Medidor medidorLecturaMedidors(Set<LecturaMedidor> lecturaMedidors) {
        this.medidorLecturaMedidors = lecturaMedidors;
        return this;
    }

    public Medidor addMedidorLecturaMedidor(LecturaMedidor lecturaMedidor) {
        this.medidorLecturaMedidors.add(lecturaMedidor);
        lecturaMedidor.setMedidor(this);
        return this;
    }

    public Medidor removeMedidorLecturaMedidor(LecturaMedidor lecturaMedidor) {
        this.medidorLecturaMedidors.remove(lecturaMedidor);
        lecturaMedidor.setMedidor(null);
        return this;
    }

    public void setMedidorLecturaMedidors(Set<LecturaMedidor> lecturaMedidors) {
        this.medidorLecturaMedidors = lecturaMedidors;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public Medidor usuario(Usuario usuario) {
        this.usuario = usuario;
        return this;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Sector getSector() {
        return sector;
    }

    public Medidor sector(Sector sector) {
        this.sector = sector;
        return this;
    }

    public void setSector(Sector sector) {
        this.sector = sector;
    }

    public Clasificacion getClasificacion() {
        return clasificacion;
    }

    public Medidor clasificacion(Clasificacion clasificacion) {
        this.clasificacion = clasificacion;
        return this;
    }

    public void setClasificacion(Clasificacion clasificacion) {
        this.clasificacion = clasificacion;
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
        Medidor medidor = (Medidor) o;
        if (medidor.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), medidor.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Medidor{" +
            "id=" + getId() +
            ", numeromedidor=" + getNumeromedidor() +
            ", fechaadquirio='" + getFechaadquirio() + "'" +
            ", fechaactual='" + getFechaactual() + "'" +
            "}";
    }
}
