package devs.team.net.service.dto;


import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import devs.team.net.domain.enumeration.Estado;

/**
 * A DTO for the LecturaMedidor entity.
 */
public class LecturaMedidorDTO implements Serializable {

    private Long id;

    @NotNull
    private Integer lecturainicial;

    @NotNull
    private Integer lecturafinal;

    @NotNull
    private Estado estado;

    @NotNull
    private Instant fecha;

    @NotNull
    private Integer anio;

    @NotNull
    private Integer mes;

    private String descripcion;

    private Set<ReciboDTO> lecturamedidorRecibos = new HashSet<>();

    private Long medidorId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getLecturainicial() {
        return lecturainicial;
    }

    public void setLecturainicial(Integer lecturainicial) {
        this.lecturainicial = lecturainicial;
    }

    public Integer getLecturafinal() {
        return lecturafinal;
    }

    public void setLecturafinal(Integer lecturafinal) {
        this.lecturafinal = lecturafinal;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Instant getFecha() {
        return fecha;
    }

    public void setFecha(Instant fecha) {
        this.fecha = fecha;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Set<ReciboDTO> getLecturamedidorRecibos() {
        return lecturamedidorRecibos;
    }

    public void setLecturamedidorRecibos(Set<ReciboDTO> recibos) {
        this.lecturamedidorRecibos = recibos;
    }

    public Long getMedidorId() {
        return medidorId;
    }

    public void setMedidorId(Long medidorId) {
        this.medidorId = medidorId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        LecturaMedidorDTO lecturaMedidorDTO = (LecturaMedidorDTO) o;
        if(lecturaMedidorDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), lecturaMedidorDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "LecturaMedidorDTO{" +
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
