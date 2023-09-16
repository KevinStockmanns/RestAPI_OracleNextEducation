package med.voll.api.domain.direccion;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DatosDireccion(
    @NotBlank
    String calle, 
    
    @NotBlank
    String distrito, 
    
    @NotNull
    Integer numero, 
    
    @NotBlank
    String complemento, 
    
    @NotBlank
    String ciudad) {
    
}
