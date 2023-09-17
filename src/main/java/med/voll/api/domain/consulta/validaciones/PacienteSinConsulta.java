package med.voll.api.domain.consulta.validaciones;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.DatosAgendarConsulta;

@Component
public class PacienteSinConsulta implements ValidadorDeConsultas {
    
    @Autowired
    private ConsultaRepository  consultaRepository;

    public void validar(DatosAgendarConsulta datos){
        var primerHorario = datos.fecha().withHour(7);
        var ultmoHorario = datos.fecha().withHour(18);

        var pacienteConConsulta = consultaRepository.existsByPacienteIdAndFechaBetween(datos.idPaciente(), primerHorario, ultmoHorario);

        if(pacienteConConsulta){
            throw new ValidationException("El paciente ya tiene una consulta para ese día");
        }
    }
}
