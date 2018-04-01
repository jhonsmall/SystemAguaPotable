package devs.team.net.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
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

/**
 * Task entity.
 * @author The JHipster team.
 */
@ApiModel(description = "Task entity. @author The JHipster team.")
@Entity
@Table(name = "costo")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "costo")
public class Costo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "cuota", precision=10, scale=2, nullable = false)
    private BigDecimal cuota;

    @NotNull
    @Column(name = "fecha", nullable = false)
    private Instant fecha;

    @OneToMany(mappedBy = "costo")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<CostoMedidor> costoCostoMedidors = new HashSet<>();

    @ManyToOne
    private Servicio servicio;

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

    public BigDecimal getCuota() {
        return cuota;
    }

    public Costo cuota(BigDecimal cuota) {
        this.cuota = cuota;
        return this;
    }

    public void setCuota(BigDecimal cuota) {
        this.cuota = cuota;
    }

    public Instant getFecha() {
        return fecha;
    }

    public Costo fecha(Instant fecha) {
        this.fecha = fecha;
        return this;
    }

    public void setFecha(Instant fecha) {
        this.fecha = fecha;
    }

    public Set<CostoMedidor> getCostoCostoMedidors() {
        return costoCostoMedidors;
    }

    public Costo costoCostoMedidors(Set<CostoMedidor> costoMedidors) {
        this.costoCostoMedidors = costoMedidors;
        return this;
    }

    public Costo addCostoCostoMedidor(CostoMedidor costoMedidor) {
        this.costoCostoMedidors.add(costoMedidor);
        costoMedidor.setCosto(this);
        return this;
    }

    public Costo removeCostoCostoMedidor(CostoMedidor costoMedidor) {
        this.costoCostoMedidors.remove(costoMedidor);
        costoMedidor.setCosto(null);
        return this;
    }

    public void setCostoCostoMedidors(Set<CostoMedidor> costoMedidors) {
        this.costoCostoMedidors = costoMedidors;
    }

    public Servicio getServicio() {
        return servicio;
    }

    public Costo servicio(Servicio servicio) {
        this.servicio = servicio;
        return this;
    }

    public void setServicio(Servicio servicio) {
        this.servicio = servicio;
    }

    public Sector getSector() {
        return sector;
    }

    public Costo sector(Sector sector) {
        this.sector = sector;
        return this;
    }

    public void setSector(Sector sector) {
        this.sector = sector;
    }

    public Clasificacion getClasificacion() {
        return clasificacion;
    }

    public Costo clasificacion(Clasificacion clasificacion) {
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
        Costo costo = (Costo) o;
        if (costo.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), costo.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Costo{" +
            "id=" + getId() +
            ", cuota=" + getCuota() +
            ", fecha='" + getFecha() + "'" +
            "}";
    }
}
