package devs.team.net.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * The Employee entity.
 */
@ApiModel(description = "The Employee entity.")
@Entity
@Table(name = "sector")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "sector")
public class Sector implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    /**
     * The firstname attribute.
     */
    @NotNull
    @ApiModelProperty(value = "The firstname attribute.", required = true)
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "descripcion")
    private String descripcion;

    @OneToMany(mappedBy = "sector")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Costo> sectorCostos = new HashSet<>();

    @OneToMany(mappedBy = "sector")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Medidor> sectorMedidors = new HashSet<>();

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

    public Sector nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Sector descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Set<Costo> getSectorCostos() {
        return sectorCostos;
    }

    public Sector sectorCostos(Set<Costo> costos) {
        this.sectorCostos = costos;
        return this;
    }

    public Sector addSectorCosto(Costo costo) {
        this.sectorCostos.add(costo);
        costo.setSector(this);
        return this;
    }

    public Sector removeSectorCosto(Costo costo) {
        this.sectorCostos.remove(costo);
        costo.setSector(null);
        return this;
    }

    public void setSectorCostos(Set<Costo> costos) {
        this.sectorCostos = costos;
    }

    public Set<Medidor> getSectorMedidors() {
        return sectorMedidors;
    }

    public Sector sectorMedidors(Set<Medidor> medidors) {
        this.sectorMedidors = medidors;
        return this;
    }

    public Sector addSectorMedidor(Medidor medidor) {
        this.sectorMedidors.add(medidor);
        medidor.setSector(this);
        return this;
    }

    public Sector removeSectorMedidor(Medidor medidor) {
        this.sectorMedidors.remove(medidor);
        medidor.setSector(null);
        return this;
    }

    public void setSectorMedidors(Set<Medidor> medidors) {
        this.sectorMedidors = medidors;
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
        Sector sector = (Sector) o;
        if (sector.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), sector.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Sector{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            "}";
    }
}
