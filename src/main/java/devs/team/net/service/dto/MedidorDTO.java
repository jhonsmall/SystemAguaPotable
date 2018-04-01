package devs.team.net.service.dto;


import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Medidor entity.
 */
public class MedidorDTO implements Serializable {

    private Long id;

    @NotNull
    private Integer numeromedidor;

    @NotNull
    private Instant fechaadquirio;

    @NotNull
    private Instant fechaactual;

    private Long usuarioId;

    private Long sectorId;

    private Long clasificacionId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumeromedidor() {
        return numeromedidor;
    }

    public void setNumeromedidor(Integer numeromedidor) {
        this.numeromedidor = numeromedidor;
    }

    public Instant getFechaadquirio() {
        return fechaadquirio;
    }

    public void setFechaadquirio(Instant fechaadquirio) {
        this.fechaadquirio = fechaadquirio;
    }

    public Instant getFechaactual() {
        return fechaactual;
    }

    public void setFechaactual(Instant fechaactual) {
        this.fechaactual = fechaactual;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Long getSectorId() {
        return sectorId;
    }

    public void setSectorId(Long sectorId) {
        this.sectorId = sectorId;
    }

    public Long getClasificacionId() {
        return clasificacionId;
    }

    public void setClasificacionId(Long clasificacionId) {
        this.clasificacionId = clasificacionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MedidorDTO medidorDTO = (MedidorDTO) o;
        if(medidorDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), medidorDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MedidorDTO{" +
            "id=" + getId() +
            ", numeromedidor=" + getNumeromedidor() +
            ", fechaadquirio='" + getFechaadquirio() + "'" +
            ", fechaactual='" + getFechaactual() + "'" +
            "}";
    }
}
