package med.voll.api.domain.paciente;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import med.voll.api.domain.direccion.DatosDireccion;

public record DatosRegistroPaciente(
    @NotBlank(message = "El nombre es obligatorio.")
    String nombre,

    @NotBlank(message = "El correo es obligatorio.") @Email
    String correo,

    @NotBlank(message = "El telefono es obligatorio")
    String telefono,

    @NotBlank(message = "El documento es obligatorio.") @Pattern(regexp = "\\d{4,6}")
    String documento,

    @Valid @NotNull
    DatosDireccion direccion
) {
    
}
