package med.voll.api.domain.consulta.validaciones;

import java.time.DayOfWeek;

import org.springframework.stereotype.Component;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.DatosAgendarConsulta;

@Component
public class HorarioDeFuncionamientoClinica implements ValidadorDeConsultas {

    public void validar(DatosAgendarConsulta datos){
        var domingo = DayOfWeek.SUNDAY.equals(datos.fecha().getDayOfWeek());
        var antesDeApertura= datos.fecha().getHour() < 7;
        var despuesDeCerrar= datos.fecha().getHour() >19;
        if(domingo || antesDeApertura || despuesDeCerrar){
            throw new ValidationException("El horario de atencion de la clínica es de lunes a sábado de 07:00hs hasta 19:00hs");
        }
    }
}
