package med.voll.api.controller;


import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
import med.voll.api.domain.medico.DatosActualizarMedico;
import med.voll.api.domain.medico.DatosListadoMedico;
import med.voll.api.domain.medico.DatosRegistroMedico;
import med.voll.api.domain.medico.DatosRespuestaMedico;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;

@RestController
@RequestMapping("/medicos")
public class MedicoController {
    private final String MAX_SIZE = "5";

    @Autowired
    private MedicoRepository  medicoRepository;

    @PostMapping
    public ResponseEntity<DatosRespuestaMedico> registrarMedico(@RequestBody @Valid DatosRegistroMedico datosRegistroMedico, UriComponentsBuilder uriComponentsBuilder){
        Medico medico = medicoRepository.save(new Medico(datosRegistroMedico));

        DatosRespuestaMedico datosRespuestaMedico = new DatosRespuestaMedico(medico.getId(), medico.getNombre(), medico.getEmail(), medico.getTelefono(), medico.getDocumento(), medico.getEspecialidad().toString(), medico.getActivo(), new DatosDireccion(medico.getDireccion().getCalle(), medico.getDireccion().getDistrito(), medico.getDireccion().getNumero(), medico.getDireccion().getComplemento(), medico.getDireccion().getCiudad()));

        URI url = uriComponentsBuilder.path("medicos/{id}").build(medico.getId());

        return ResponseEntity.created(url).body(datosRespuestaMedico);
    }


    @GetMapping
    public ResponseEntity<Page<DatosListadoMedico>> listadoMedicos(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size){
        size = (size > Integer.valueOf(this.MAX_SIZE)) ? Integer.valueOf(this.MAX_SIZE) : size;

        Pageable paginacion = PageRequest.of(page, size);
        // return medicoRepository.findAll(paginacion).map(DatosListadoMedico::new);
        return ResponseEntity.ok(medicoRepository.findByActivoTrue(paginacion).map(DatosListadoMedico::new));
    }
    @GetMapping("/inactivos")
    public ResponseEntity<Page<DatosListadoMedico>> listadoMedicosInactivos(@RequestParam(defaultValue = "5") int size){
        size = (size > Integer.valueOf(this.MAX_SIZE)) ? Integer.valueOf(this.MAX_SIZE) : size;
        Pageable paginacion = PageRequest.ofSize(size);
        return ResponseEntity.ok(medicoRepository.findByActivoFalse(paginacion).map(DatosListadoMedico::new));
    }

    @PutMapping
    @Transactional
    public ResponseEntity<DatosRespuestaMedico> actualizarMedico(@RequestBody @Valid DatosActualizarMedico datosActualizarMedico){
        Medico medico = medicoRepository.getReferenceById(datosActualizarMedico.id());
        medico.actualizarDatos(datosActualizarMedico);

        return ResponseEntity.ok(
            new DatosRespuestaMedico(
                medico.getId(),
                medico.getNombre(),
                medico.getEmail(),
                medico.getTelefono(),
                medico.getDocumento(),
                medico.getEspecialidad().toString(),
                medico.getActivo(),
                new DatosDireccion(medico.getDireccion().getCalle(),
                    medico.getDireccion().getDistrito(),
                    medico.getDireccion().getNumero(),
                    medico.getDireccion().getComplemento(),
                    medico.getDireccion().getCiudad())));
    }


    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> eliminarMedico(@PathVariable Long id){
        Medico medico = medicoRepository.getReferenceById(id);
        // medicoRepository.delete(medico);
        medico.desactivarMedico();
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/{id}")
    public ResponseEntity<DatosRespuestaMedico> retornaMedico(@PathVariable Long id){
        Medico medico = medicoRepository.getReferenceById(id);
        
        DatosRespuestaMedico datosRespuestaMedico = new DatosRespuestaMedico(medico.getId(), medico.getNombre(), medico.getEmail(), medico.getTelefono(), medico.getDocumento(), medico.getEspecialidad().toString(), medico.getActivo(), new DatosDireccion(medico.getDireccion().getCalle(), medico.getDireccion().getDistrito(), medico.getDireccion().getNumero(), medico.getDireccion().getComplemento(), medico.getDireccion().getCiudad()));
        
        return ResponseEntity.ok(datosRespuestaMedico);
    }

}
