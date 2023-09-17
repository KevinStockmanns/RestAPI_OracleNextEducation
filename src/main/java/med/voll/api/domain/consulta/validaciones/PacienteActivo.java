package med.voll.api.domain.consulta.validaciones;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.DatosAgendarConsulta;
import med.voll.api.domain.paciente.PacienteRepository;

@Component
public class PacienteActivo implements ValidadorDeConsultas {

    @Autowired
    private PacienteRepository pacienteRepository;

    public void validar(DatosAgendarConsulta datos){
        if(datos.idPaciente()== null){
            return;
        }

        var pacienteActivo = pacienteRepository.findActivoById(datos.idPaciente());
        System.out.println(pacienteActivo);
        System.out.println("******************************");
                System.out.println("******************************");

                        System.out.println("******************************");

                                System.out.println("******************************");


        if(!pacienteActivo){
            throw new ValidationException("No se puede permitir agendar citas con pacientes inactivos en el sistema");
        }
    }
}
