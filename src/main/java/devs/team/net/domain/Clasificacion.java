package devs.team.net.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import devs.team.net.domain.enumeration.Estado;

/**
 * not an ignored comment
 */
@ApiModel(description = "not an ignored comment")
@Entity
@Table(name = "clasificacion")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "clasificacion")
public class Clasificacion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private Estado estado;

    @OneToMany(mappedBy = "clasificacion")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Costo> clasificacionCostos = new HashSet<>();

    @OneToMany(mappedBy = "clasificacion")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<EscalasDelMedidor> clasificacionEscalasDelMedidors = new HashSet<>();

    @OneToMany(mappedBy = "clasificacion")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Medidor> clasificacionMedidors = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public Clasificacion nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Estado getEstado() {
        return estado;
    }

    public Clasificacion estado(Estado estado) {
        this.estado = estado;
        return this;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Set<Costo> getClasificacionCostos() {
        return clasificacionCostos;
    }

    public Clasificacion clasificacionCostos(Set<Costo> costos) {
        this.clasificacionCostos = costos;
        return this;
    }

    public Clasificacion addClasificacionCosto(Costo costo) {
        this.clasificacionCostos.add(costo);
        costo.setClasificacion(this);
        return this;
    }

    public Clasificacion removeClasificacionCosto(Costo costo) {
        this.clasificacionCostos.remove(costo);
        costo.setClasificacion(null);
        return this;
    }

    public void setClasificacionCostos(Set<Costo> costos) {
        this.clasificacionCostos = costos;
    }

    public Set<EscalasDelMedidor> getClasificacionEscalasDelMedidors() {
        return clasificacionEscalasDelMedidors;
    }

    public Clasificacion clasificacionEscalasDelMedidors(Set<EscalasDelMedidor> escalasDelMedidors) {
        this.clasificacionEscalasDelMedidors = escalasDelMedidors;
        return this;
    }

    public Clasificacion addClasificacionEscalasDelMedidor(EscalasDelMedidor escalasDelMedidor) {
        this.clasificacionEscalasDelMedidors.add(escalasDelMedidor);
        escalasDelMedidor.setClasificacion(this);
        return this;
    }

    public Clasificacion removeClasificacionEscalasDelMedidor(EscalasDelMedidor escalasDelMedidor) {
        this.clasificacionEscalasDelMedidors.remove(escalasDelMedidor);
        escalasDelMedidor.setClasificacion(null);
        return this;
    }

    public void setClasificacionEscalasDelMedidors(Set<EscalasDelMedidor> escalasDelMedidors) {
        this.clasificacionEscalasDelMedidors = escalasDelMedidors;
    }

    public Set<Medidor> getClasificacionMedidors() {
        return clasificacionMedidors;
    }

    public Clasificacion clasificacionMedidors(Set<Medidor> medidors) {
        this.clasificacionMedidors = medidors;
        return this;
    }

    public Clasificacion addClasificacionMedidor(Medidor medidor) {
        this.clasificacionMedidors.add(medidor);
        medidor.setClasificacion(this);
        return this;
    }

    public Clasificacion removeClasificacionMedidor(Medidor medidor) {
        this.clasificacionMedidors.remove(medidor);
        medidor.setClasificacion(null);
        return this;
    }

    public void setClasificacionMedidors(Set<Medidor> medidors) {
        this.clasificacionMedidors = medidors;
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
        Clasificacion clasificacion = (Clasificacion) o;
        if (clasificacion.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), clasificacion.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Clasificacion{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", estado='" + getEstado() + "'" +
            "}";
    }
}
