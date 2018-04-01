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

import devs.team.net.domain.enumeration.Estado;

import devs.team.net.domain.enumeration.Documento;

import devs.team.net.domain.enumeration.Sexo;

/**
 * A Usuario.
 */
@Entity
@Table(name = "usuario")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "usuario")
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private Estado estado;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "documento", nullable = false)
    private Documento documento;

    @NotNull
    @Column(name = "numero", nullable = false)
    private String numero;

    @NotNull
    @Column(name = "nombres", nullable = false)
    private String nombres;

    @NotNull
    @Column(name = "apellidos", nullable = false)
    private String apellidos;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "sexo", nullable = false)
    private Sexo sexo;

    @Column(name = "direccion")
    private String direccion;

    @Column(name = "telefono")
    private String telefono;

    @Column(name = "email")
    private String email;

    @OneToMany(mappedBy = "usuario")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Recibo> usuarioRecibos = new HashSet<>();

    @OneToMany(mappedBy = "usuario")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Medidor> usuarioMedidors = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Estado getEstado() {
        return estado;
    }

    public Usuario estado(Estado estado) {
        this.estado = estado;
        return this;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Documento getDocumento() {
        return documento;
    }

    public Usuario documento(Documento documento) {
        this.documento = documento;
        return this;
    }

    public void setDocumento(Documento documento) {
        this.documento = documento;
    }

    public String getNumero() {
        return numero;
    }

    public Usuario numero(String numero) {
        this.numero = numero;
        return this;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getNombres() {
        return nombres;
    }

    public Usuario nombres(String nombres) {
        this.nombres = nombres;
        return this;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public Usuario apellidos(String apellidos) {
        this.apellidos = apellidos;
        return this;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public Sexo getSexo() {
        return sexo;
    }

    public Usuario sexo(Sexo sexo) {
        this.sexo = sexo;
        return this;
    }

    public void setSexo(Sexo sexo) {
        this.sexo = sexo;
    }

    public String getDireccion() {
        return direccion;
    }

    public Usuario direccion(String direccion) {
        this.direccion = direccion;
        return this;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public Usuario telefono(String telefono) {
        this.telefono = telefono;
        return this;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public Usuario email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Recibo> getUsuarioRecibos() {
        return usuarioRecibos;
    }

    public Usuario usuarioRecibos(Set<Recibo> recibos) {
        this.usuarioRecibos = recibos;
        return this;
    }

    public Usuario addUsuarioRecibo(Recibo recibo) {
        this.usuarioRecibos.add(recibo);
        recibo.setUsuario(this);
        return this;
    }

    public Usuario removeUsuarioRecibo(Recibo recibo) {
        this.usuarioRecibos.remove(recibo);
        recibo.setUsuario(null);
        return this;
    }

    public void setUsuarioRecibos(Set<Recibo> recibos) {
        this.usuarioRecibos = recibos;
    }

    public Set<Medidor> getUsuarioMedidors() {
        return usuarioMedidors;
    }

    public Usuario usuarioMedidors(Set<Medidor> medidors) {
        this.usuarioMedidors = medidors;
        return this;
    }

    public Usuario addUsuarioMedidor(Medidor medidor) {
        this.usuarioMedidors.add(medidor);
        medidor.setUsuario(this);
        return this;
    }

    public Usuario removeUsuarioMedidor(Medidor medidor) {
        this.usuarioMedidors.remove(medidor);
        medidor.setUsuario(null);
        return this;
    }

    public void setUsuarioMedidors(Set<Medidor> medidors) {
        this.usuarioMedidors = medidors;
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
        Usuario usuario = (Usuario) o;
        if (usuario.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), usuario.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Usuario{" +
            "id=" + getId() +
            ", estado='" + getEstado() + "'" +
            ", documento='" + getDocumento() + "'" +
            ", numero='" + getNumero() + "'" +
            ", nombres='" + getNombres() + "'" +
            ", apellidos='" + getApellidos() + "'" +
            ", sexo='" + getSexo() + "'" +
            ", direccion='" + getDireccion() + "'" +
            ", telefono='" + getTelefono() + "'" +
            ", email='" + getEmail() + "'" +
            "}";
    }
}
