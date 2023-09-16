package med.voll.api.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.direccion.DatosDireccion;
import med.voll.api.domain.paciente.DatosActualizarPaciente;
import med.voll.api.domain.paciente.DatosListadoPaciente;
import med.voll.api.domain.paciente.DatosRegistroPaciente;
import med.voll.api.domain.paciente.DatosRespuestaPaciente;
import med.voll.api.domain.paciente.Paciente;
import med.voll.api.domain.paciente.PacienteRepository;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {

    @Autowired
    private PacienteRepository pacienteRepository;
    
    @PostMapping
    public ResponseEntity<DatosRespuestaPaciente> registrarPaciente(@RequestBody @Valid DatosRegistroPaciente datosRegistroPaciente, UriComponentsBuilder uriComponentsBuilder){
        Paciente paciente = pacienteRepository.save(new Paciente(datosRegistroPaciente));

        DatosRespuestaPaciente datosRespuestaPaciente = new DatosRespuestaPaciente(
            paciente.getId(), 
            paciente.getNombre(),
            paciente.getCorreo(), 
            paciente.getTelefono(),
            paciente.getDocumento(), 
            new DatosDireccion(
                paciente.getDireccion().getCalle(),
                paciente.getDireccion().getDistrito(),
                paciente.getDireccion().getNumero(),
                paciente.getDireccion().getComplemento(),
                paciente.getDireccion().getCiudad()));

        URI url = uriComponentsBuilder.path("/paciente/{id}").build(paciente.getId());

        return ResponseEntity.created(url).body(datosRespuestaPaciente);
    }


    @GetMapping
    public ResponseEntity<Page<DatosListadoPaciente>> listadoPacientes(@RequestParam(defaultValue="10") int size, @RequestParam(defaultValue = "0") int page){
        size = (size > 10) ? 10 : size;

        Sort sort = Sort.by(Sort.Order.asc("nombre"));
        Pageable paginacion = PageRequest.of(page, size, sort);

        return ResponseEntity.ok(pacienteRepository.findByActivoTrue(paginacion).map(DatosListadoPaciente::new));
    }
    @GetMapping("/inactivos")
    public ResponseEntity<Page<DatosListadoPaciente>> listadoPacientesInactivos(@RequestParam(defaultValue="10") int size, @RequestParam(defaultValue = "0") int page){
        size = (size > 10) ? 10 : size;

        Sort sort = Sort.by(Sort.Order.asc("nombre"));
        Pageable paginacion = PageRequest.of(page, size, sort);

        return ResponseEntity.ok(pacienteRepository.findByActivoFalse(paginacion).map(DatosListadoPaciente::new));
    }


    @PutMapping @Transactional
    public ResponseEntity<DatosRespuestaPaciente> actualizarPaciente(@RequestBody @Valid DatosActualizarPaciente datosActualizarPaciente){
        Paciente paciente = pacienteRepository.getReferenceById(datosActualizarPaciente.id());
        paciente.actualizarPaciente(datosActualizarPaciente);

        return ResponseEntity.ok(
            new DatosRespuestaPaciente(
                paciente.getId(), 
                paciente.getNombre(), 
                paciente.getCorreo(), 
                paciente.getTelefono(),
                paciente.getDocumento(), 
                new DatosDireccion(
                    paciente.getDireccion().getCalle(), 
                    paciente.getDireccion().getDistrito(), 
                    paciente.getDireccion().getNumero(), 
                    paciente.getDireccion().getComplemento(), 
                    paciente.getDireccion().getCiudad()))
        );
    }


    @DeleteMapping("/{id}") @Transactional
    public ResponseEntity<Void> eliminarPaciente(@PathVariable Long id){
        Paciente paciente = pacienteRepository.getReferenceById(id);
        paciente.desactivarPaciente();

        return ResponseEntity.noContent().build();
    }
}
