package med.voll.api.domain.medico;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import med.voll.api.domain.direccion.DatosDireccion;

public record DatosRegistroMedico(
    @NotBlank(message = "Nombre es obligatorio.")
    String nombre, 
    
    @NotBlank(message = "El correo es obligatorio") @Email
    String email, 
    
    @NotBlank(message = "Documento es obligatorio.") @Pattern(regexp = "\\d{4,6}")
    String documento, 

    @NotBlank
    String telefono,
    
    @NotNull
    Especialidad especialidad, 
    
    @Valid @NotNull
    DatosDireccion direccion) {
    
}
