package med.voll.api.domain.paciente;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import med.voll.api.domain.direccion.Direccion;

@Entity(name = "Paciente")
@Table(name = "pacientes")
@Getter @ToString @NoArgsConstructor @AllArgsConstructor
public class Paciente {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "activo")
    private Boolean activo;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "correo")
    private String correo;

    @Column(name = "telefono")
    private String telefono;

    @Column(name = "documento")
    private String documento;
    
    @Embedded
    private Direccion direccion;



    public Paciente(@Valid DatosRegistroPaciente datosRegistroPaciente) {
        this.activo = true;
        this.nombre = datosRegistroPaciente.nombre();
        this.correo = datosRegistroPaciente.correo();
        this.telefono = datosRegistroPaciente.telefono();
        this.documento = datosRegistroPaciente.documento();
        this.direccion = new Direccion(datosRegistroPaciente.direccion());
    }



    public void actualizarPaciente(DatosActualizarPaciente datosActualizarPaciente){
        if(datosActualizarPaciente.nombre() != null)
            this.nombre = datosActualizarPaciente.nombre();
        
        if(datosActualizarPaciente.telefono() != null)
            this.telefono = datosActualizarPaciente.telefono();
        
        if(datosActualizarPaciente.direccion() != null)
            this.direccion = direccion.actualizarDatos(datosActualizarPaciente.direccion());
    }



    public void desactivarPaciente() {
        this.activo = false;
    }
}
