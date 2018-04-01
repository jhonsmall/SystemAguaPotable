package devs.team.net.service.dto;


import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import devs.team.net.domain.enumeration.Estado;

/**
 * A DTO for the CostoMedidor entity.
 */
public class CostoMedidorDTO implements Serializable {

    private Long id;

    @NotNull
    private Instant fecha;

    @NotNull
    private Estado estado;

    private Long costoId;

    private Long medidorId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getFecha() {
        return fecha;
    }

    public void setFecha(Instant fecha) {
        this.fecha = fecha;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Long getCostoId() {
        return costoId;
    }

    public void setCostoId(Long costoId) {
        this.costoId = costoId;
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

        CostoMedidorDTO costoMedidorDTO = (CostoMedidorDTO) o;
        if(costoMedidorDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), costoMedidorDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CostoMedidorDTO{" +
            "id=" + getId() +
            ", fecha='" + getFecha() + "'" +
            ", estado='" + getEstado() + "'" +
            "}";
    }
}
