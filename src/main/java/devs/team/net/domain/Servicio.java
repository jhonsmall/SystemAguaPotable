package devs.team.net.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import devs.team.net.domain.enumeration.Tipo;

/**
 * A Servicio.
 */
@Entity
@Table(name = "servicio")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "servicio")
public class Servicio implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "norma")
    private String norma;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false)
    private Tipo tipo;

    @OneToMany(mappedBy = "servicio")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Costo> servicioCostos = new HashSet<>();

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

    public Servicio nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNorma() {
        return norma;
    }

    public Servicio norma(String norma) {
        this.norma = norma;
        return this;
    }

    public void setNorma(String norma) {
        this.norma = norma;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public Servicio tipo(Tipo tipo) {
        this.tipo = tipo;
        return this;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public Set<Costo> getServicioCostos() {
        return servicioCostos;
    }

    public Servicio servicioCostos(Set<Costo> costos) {
        this.servicioCostos = costos;
        return this;
    }

    public Servicio addServicioCosto(Costo costo) {
        this.servicioCostos.add(costo);
        costo.setServicio(this);
        return this;
    }

    public Servicio removeServicioCosto(Costo costo) {
        this.servicioCostos.remove(costo);
        costo.setServicio(null);
        return this;
    }

    public void setServicioCostos(Set<Costo> costos) {
        this.servicioCostos = costos;
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
        Servicio servicio = (Servicio) o;
        if (servicio.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), servicio.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Servicio{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", norma='" + getNorma() + "'" +
            ", tipo='" + getTipo() + "'" +
            "}";
    }
}
