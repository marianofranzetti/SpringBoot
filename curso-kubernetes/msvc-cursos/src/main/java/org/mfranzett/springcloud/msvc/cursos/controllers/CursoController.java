package org.mfranzett.springcloud.msvc.cursos.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.mfranzett.springcloud.msvc.cursos.entity.Curso;
import org.mfranzett.springcloud.msvc.cursos.services.CursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/v1/cursos")
public class CursoController {

	@Autowired
	private CursoService service;

	@GetMapping
	public ResponseEntity<List<Curso>> listar(){
		return ResponseEntity.ok(service.listar());
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> obtenerPorId(@PathVariable Long id){
		Optional<Curso> o = service.porId(id);
		if (o.isPresent()) {
			return ResponseEntity.ok(o.get());
		}
		return ResponseEntity.notFound().build();
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> crear(@Valid @RequestBody Curso curso, BindingResult result){
		if (result.hasErrors()) {
			return validar(result);
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(curso));
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> editar(@Valid @RequestBody Curso curso, BindingResult result, @PathVariable Long id){
		if (result.hasErrors()) {
			return validar(result);
		}
		
		Optional<Curso> cursoObtenido = service.porId(id);
		if (cursoObtenido.isPresent()){
			Curso cursoDb = cursoObtenido.get();
			cursoDb.setNombre(curso.getNombre());
			return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(cursoDb));
		}
		return ResponseEntity.notFound().build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> eliminar(@PathVariable Long id){
		Optional<Curso> o = service.porId(id);
		if(o.isPresent()){
			service.eliminar(id);
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.notFound().build();
	}

	private ResponseEntity<?> validar(BindingResult result) {
		Map<String, String> errores = new HashMap<>();
		result.getFieldErrors().forEach(err -> {
			errores.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());
		});
		return ResponseEntity.badRequest().body(errores);
	}

}
