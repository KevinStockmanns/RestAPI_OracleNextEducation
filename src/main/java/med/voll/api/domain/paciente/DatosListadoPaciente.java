package med.voll.api.domain.paciente;

public record DatosListadoPaciente(Long id, String nombre, String correo, String documento) {
    

    public DatosListadoPaciente(Paciente paciente){
        this(paciente.getId(), paciente.getNombre(), paciente.getCorreo(), paciente.getDocumento());
    }
}
