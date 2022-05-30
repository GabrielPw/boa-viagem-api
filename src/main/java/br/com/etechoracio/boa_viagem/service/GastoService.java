	package br.com.etechoracio.boa_viagem.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.etechoracio.boa_viagem.entity.Gasto;
import br.com.etechoracio.boa_viagem.entity.Viagem;
import br.com.etechoracio.boa_viagem.repository.GastoRepository;
import br.com.etechoracio.boa_viagem.repository.ViagemRepository;


@Service
public class GastoService {

	@Autowired
	private GastoRepository repository;
	
	@Autowired
	private ViagemRepository viagemRepo;
	
	public List<Gasto> listarTodos(){
		return repository.findAll();
	}
	
	public Optional<Gasto> buscarPorId(Long id) {
		

		 return repository.findById(id);
	}
	
	public boolean deletarPorId(Long id) {
		boolean existe = repository.existsById(id);
		
		if (existe) {repository.deleteById(id);}
		return existe;
			
	}
	
	public Gasto inserir(Gasto obj) {
		
		
		Optional<Viagem> existe = viagemRepo.findById(obj.getViagem().getId());
		if(!existe.isPresent()) {

			throw new RuntimeException("Viagem não encontrada.");
		}
		
		if(existe.get().getSaida() != null) {
			throw new RuntimeException("Data de viagem já foi encerrada.");
		}
		
		if(obj.getData().isBefore(existe.get().getChegada())) {	// Verifica se data de gasto é depois de data de chegada da viagem.
			throw new RuntimeException("Erro. A data de viagem já foi encerrada.");
		}
		
		// ... 
		
		return repository.save(obj);
	}
	
	
	
	public Optional<Gasto> atualizar(Long id, Gasto gasto) {
		
		Optional<Viagem> viagem = viagemRepo.findById(gasto.getViagem().getId());
		
		if(viagem.get().getId() != gasto.getViagem().getId()) {
			
			throw new RuntimeException("Viagem inserida não corresponde com a viagem de atualização.");
		}
		
		boolean existe = repository.existsById(id);
		if (!existe) {return Optional.empty();}
		
		if (gasto.getData().isBefore(viagem.get().getChegada())) {
			
			throw new RuntimeException("Não foi possível atualizar o gasto, pois a data do gasto é anterior ao da viagem.");
		}
		return Optional.of(repository.save(gasto));
	}
}
