package org.mfranzett.springcloud.msvc.cursos.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.mfranzett.springcloud.msvc.cursos.entity.Curso;
import org.mfranzett.springcloud.msvc.cursos.repositories.CursoRepository;
import org.mfranzett.springcloud.msvc.cursos.services.CursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CursoServiceImpl implements CursoService {

	@Autowired
	private CursoRepository repository;
	
	
	@Override
	@Transactional(readOnly = true)
	public List<Curso> listar() {
		// TODO Auto-generated method stub
		return (List<Curso>) repository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Curso> porId(Long id) {
		// TODO Auto-generated method stub
		return repository.findById(id);
	}

	@Override
	@Transactional
	public Curso guardar(Curso curso) {
		// TODO Auto-generated method stub
		return repository.save(curso);
	}

	@Override
	@Transactional
	public void eliminar(Long id) {
		// TODO Auto-generated method stub
		repository.deleteById(id);
	}

}
