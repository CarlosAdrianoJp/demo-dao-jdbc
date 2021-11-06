package model.dao;

import java.util.List;

import model.entities.Vendedor;

public interface VendedorDao {

	
	
	void inserir(Vendedor obj);
	void atualizar(Vendedor obj);
	void deletePeloId(Integer id);
	Vendedor encontrePeloId(Integer id);
	List<Vendedor> encontreTudo();
	
}
