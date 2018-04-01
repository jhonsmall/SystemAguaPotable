package devs.team.net.domain;

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

import devs.team.net.domain.enumeration.Estado;

/**
 * A LecturaMedidor.
 */
@Entity
@Table(name = "lectura_medidor")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "lecturamedidor")
public class LecturaMedidor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "lecturainicial", nullable = false)
    private Integer lecturainicial;

    @NotNull
    @Column(name = "lecturafinal", nullable = false)
    private Integer lecturafinal;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private Estado estado;

    @NotNull
    @Column(name = "fecha", nullable = false)
    private Instant fecha;

    @NotNull
    @Column(name = "anio", nullable = false)
    private Integer anio;

    @NotNull
    @Column(name = "mes", nullable = false)
    private Integer mes;

    @Column(name = "descripcion")
    private String descripcion;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "lectura_medidor_lecturamedidor_recibo",
               joinColumns = @JoinColumn(name="lectura_medidors_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="lecturamedidor_recibos_id", referencedColumnName="id"))
    private Set<Recibo> lecturamedidorRecibos = new HashSet<>();

    @ManyToOne
    private Medidor medidor;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getLecturainicial() {
        return lecturainicial;
    }

    public LecturaMedidor lecturainicial(Integer lecturainicial) {
        this.lecturainicial = lecturainicial;
        return this;
    }

    public void setLecturainicial(Integer lecturainicial) {
        this.lecturainicial = lecturainicial;
    }

    public Integer getLecturafinal() {
        return lecturafinal;
    }

    public LecturaMedidor lecturafinal(Integer lecturafinal) {
        this.lecturafinal = lecturafinal;
        return this;
    }

    public void setLecturafinal(Integer lecturafinal) {
        this.lecturafinal = lecturafinal;
    }

    public Estado getEstado() {
        return estado;
    }

    public LecturaMedidor estado(Estado estado) {
        this.estado = estado;
        return this;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Instant getFecha() {
        return fecha;
    }

    public LecturaMedidor fecha(Instant fecha) {
        this.fecha = fecha;
        return this;
    }

    public void setFecha(Instant fecha) {
        this.fecha = fecha;
    }

    public Integer getAnio() {
        return anio;
    }

    public LecturaMedidor anio(Integer anio) {
        this.anio = anio;
        return this;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public Integer getMes() {
        return mes;
    }

    public LecturaMedidor mes(Integer mes) {
        this.mes = mes;
        return this;
    }

    public void setMes(Integer mes) {
        this.mes = mes;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public LecturaMedidor descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Set<Recibo> getLecturamedidorRecibos() {
        return lecturamedidorRecibos;
    }

    public LecturaMedidor lecturamedidorRecibos(Set<Recibo> recibos) {
        this.lecturamedidorRecibos = recibos;
        return this;
    }

    public LecturaMedidor addLecturamedidorRecibo(Recibo recibo) {
        this.lecturamedidorRecibos.add(recibo);
        recibo.getLecturaMedidors().add(this);
        return this;
    }

    public LecturaMedidor removeLecturamedidorRecibo(Recibo recibo) {
        this.lecturamedidorRecibos.remove(recibo);
        recibo.getLecturaMedidors().remove(this);
        return this;
    }

    public void setLecturamedidorRecibos(Set<Recibo> recibos) {
        this.lecturamedidorRecibos = recibos;
    }

    public Medidor getMedidor() {
        return medidor;
    }

    public LecturaMedidor medidor(Medidor medidor) {
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
        LecturaMedidor lecturaMedidor = (LecturaMedidor) o;
        if (lecturaMedidor.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), lecturaMedidor.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "LecturaMedidor{" +
            "id=" + getId() +
            ", lecturainicial=" + getLecturainicial() +
            ", lecturafinal=" + getLecturafinal() +
            ", estado='" + getEstado() + "'" +
            ", fecha='" + getFecha() + "'" +
            ", anio=" + getAnio() +
            ", mes=" + getMes() +
            ", descripcion='" + getDescripcion() + "'" +
            "}";
    }
}
