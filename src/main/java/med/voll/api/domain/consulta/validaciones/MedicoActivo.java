package med.voll.api.domain.consulta.validaciones;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.DatosAgendarConsulta;
import med.voll.api.domain.medico.MedicoRepository;

@Component
public class MedicoActivo implements ValidadorDeConsultas {
    
    @Autowired
    private MedicoRepository medicoRepository;

    public void validar(DatosAgendarConsulta datos){

        if(datos.idMedico() == null)
            return;
        
        var medicoActivo = medicoRepository.findActivoById(datos.idMedico());

        if(!medicoActivo)
            throw new ValidationException("El medico ingresado esta inactivo");
    }
}
