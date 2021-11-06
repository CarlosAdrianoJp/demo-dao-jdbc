package aplicacao;

import java.util.Date;

import model.dao.DaoFactory;
import model.dao.VendedorDao;
import model.dao.impl.VendedorDaoJDBC;
import model.entities.Departamento;
import model.entities.Vendedor;

public class Programa {

	public static void main(String[] args) {

		//Departamento obj = new Departamento(1, "livros");

		
		//Vendedor vendedor = new Vendedor(21, "bob", "bob@bob.com", new Date(), 3000.0, obj);
		
		VendedorDao vendedorDao = DaoFactory.criarVendedorDao();
		
		Vendedor vendedor =  vendedorDao.encontrePeloId(3);
		
		System.out.println(vendedor);
	}
}
