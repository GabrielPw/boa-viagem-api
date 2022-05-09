package br.com.etechoracio.boa_viagem.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import br.com.etechoracio.boa_viagem.entity.Viagem;
import br.com.etechoracio.boa_viagem.repository.ViagemRepository;

public class ViagemService {

	
	@Autowired
	private ViagemRepository viagemRepo;
	

	public List<Viagem> listarTodos(){
		
		
		return viagemRepo.findAll();
	}
	

	public Optional<Viagem> buscarPorId(Long id){
		
		return viagemRepo.findById(id);
	}
	

	public Viagem inserir(Viagem obj) {
		
		return viagemRepo.save(obj);
	}
	

	public Optional<Viagem> atualizar(Long id, Viagem viagem) {
		
		boolean existe = viagemRepo.existsById(id);
		
		if (!existe) {return Optional.empty();}
		
		return Optional.of(viagemRepo.save(viagem));
	}
}
