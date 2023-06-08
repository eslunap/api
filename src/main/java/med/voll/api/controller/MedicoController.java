package med.voll.api.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.direccion.DatosDireccion;
import med.voll.api.medico.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/medicos")
public class MedicoController {

   @Autowired
   private MedicoRepository medicoRepository;

   @PostMapping
   public void registrarMedico(@RequestBody @Valid DatosRegistroMedico datosRegistroMedico) {
      System.out.println("Request recibido exitosamente");
      System.out.println(datosRegistroMedico);
      medicoRepository.save(new Medico(datosRegistroMedico));
   }

   @GetMapping
   public Page<DatosListadoMedico> listadoMedicos(@PageableDefault(page = 0, size = 10, sort = {"nombre"}) Pageable paginacion){
      //return medicoRepository.findAll(paginacion).map(DatosListadoMedico::new);
      return medicoRepository.findByActivoTrue(paginacion).map(DatosListadoMedico::new);
   }

   @PutMapping
   @Transactional
   public ResponseEntity actualizarMedico(@RequestBody @Valid DatosActualizarMedico datosActualizarMedico){
      Medico medico = medicoRepository.getReferenceById(datosActualizarMedico.id());
      medico.actualizarDatos(datosActualizarMedico);
      return ResponseEntity.ok(new DatosRespuestaMedico(medico.getId(), medico.getNombre(), medico.getEmail(),
              medico.getTelefono(),medico.getDocumento(), medico.getEspecialidad().toString(),
              new DatosDireccion(medico.getDireccion().getCalle(), medico.getDireccion().getDistrito(),
                      medico.getDireccion().getCiudad(),medico.getDireccion().getNumero(),
                      medico.getDireccion().getComplemento())));
   }

   //DELETE LÓGICO
   @DeleteMapping("/{id}") //Path Variable
   @Transactional
   public ResponseEntity eliminarMedico(@PathVariable Long id){
      Medico medico = medicoRepository.getReferenceById(id);
      medico.desactivarMedico();
      return ResponseEntity.noContent().build();
   }

   //DELETE EN BASE DE DATOS
 /*   public void eliminarMedico(@PathVariable Long id){
      Medico medico = medicoRepository.getReferenceById(id);
      medicoRepository.delete(medico);
   }*/
}
