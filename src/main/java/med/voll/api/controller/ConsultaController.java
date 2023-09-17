package med.voll.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.consulta.AgendaConsultaService;
import med.voll.api.domain.consulta.Consulta;
import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.DatosAgendarConsulta;
import med.voll.api.domain.consulta.DatosDetallesConsulta;

@RestController @RequestMapping("/consultas")
public class ConsultaController {

    @Autowired
    private AgendaConsultaService service;

    @Autowired
    private ConsultaRepository consultaRepository;
    
    @PostMapping @Transactional
    public ResponseEntity<DatosDetallesConsulta> agendar(@RequestBody @Valid DatosAgendarConsulta datos){

        var res = service.agendar(datos);

        return ResponseEntity.ok(res);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarAgenda(@RequestParam Long id){
        Consulta consulta = consultaRepository.getReferenceById(id);
        consultaRepository.delete(consulta);
        return ResponseEntity.ok().build();
    }
}
