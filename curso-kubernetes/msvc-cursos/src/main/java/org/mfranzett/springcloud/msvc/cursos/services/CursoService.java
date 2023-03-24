package org.mfranzett.springcloud.msvc.cursos.services;

import java.util.*;

import org.mfranzett.springcloud.msvc.cursos.entity.Curso;

public interface CursoService {
	List<Curso> listar();
	Optional<Curso> porId(Long id);
	Curso guardar(Curso curso);
	void eliminar(Long id);

}
