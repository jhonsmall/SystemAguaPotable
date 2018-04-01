package devs.team.net.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A EscalasDelMedidor.
 */
@Entity
@Table(name = "escalas_del_medidor")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "escalasdelmedidor")
public class EscalasDelMedidor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "inicio", nullable = false)
    private Integer inicio;

    @NotNull
    @Column(name = "fin", nullable = false)
    private Integer fin;

    @NotNull
    @Column(name = "fecha", nullable = false)
    private Instant fecha;

    @ManyToOne
    private Clasificacion clasificacion;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getInicio() {
        return inicio;
    }

    public EscalasDelMedidor inicio(Integer inicio) {
        this.inicio = inicio;
        return this;
    }

    public void setInicio(Integer inicio) {
        this.inicio = inicio;
    }

    public Integer getFin() {
        return fin;
    }

    public EscalasDelMedidor fin(Integer fin) {
        this.fin = fin;
        return this;
    }

    public void setFin(Integer fin) {
        this.fin = fin;
    }

    public Instant getFecha() {
        return fecha;
    }

    public EscalasDelMedidor fecha(Instant fecha) {
        this.fecha = fecha;
        return this;
    }

    public void setFecha(Instant fecha) {
        this.fecha = fecha;
    }

    public Clasificacion getClasificacion() {
        return clasificacion;
    }

    public EscalasDelMedidor clasificacion(Clasificacion clasificacion) {
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
        EscalasDelMedidor escalasDelMedidor = (EscalasDelMedidor) o;
        if (escalasDelMedidor.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), escalasDelMedidor.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EscalasDelMedidor{" +
            "id=" + getId() +
            ", inicio=" + getInicio() +
            ", fin=" + getFin() +
            ", fecha='" + getFecha() + "'" +
            "}";
    }
}
