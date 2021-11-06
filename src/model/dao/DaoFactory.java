package model.dao;

import db.DB;
import model.dao.impl.VendedorDaoJDBC;

public class DaoFactory {

	//lembre factory e fabrica e o nome que deveria estar
	
	// aqui eu so esgtou criando um metodo com retorno da interface vendedordao para injecao de dependencias
	//assim quando ele retornar o vendedor dao jdbc  ele vai ja instanciar um vendedor com base no sistema que eu utilizar
	// nesse  caso o jdbc
	
	public static VendedorDao criarVendedorDao() {
		return new VendedorDaoJDBC(DB.getConnection());
	}
	
}
