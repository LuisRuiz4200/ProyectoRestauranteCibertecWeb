package com.web.restaurante.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.web.restaurante.model.Colaborador;



@Repository
public interface ColaboradorRepository extends JpaRepository<Colaborador,Integer>{
	
	Colaborador findByIdColaborador(int idColaborador);
	
}
