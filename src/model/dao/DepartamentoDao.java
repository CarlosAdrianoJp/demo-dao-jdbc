package model.dao;

import java.util.List;

import model.entities.Departamento;

public interface DepartamentoDao {

	void inserir(Departamento obj);
	void atualizar(Departamento obj);
	void deletePeloId(Integer id);
	Departamento encontrePeloId(Integer id);
	List<Departamento> encontreTudo();
	
}
