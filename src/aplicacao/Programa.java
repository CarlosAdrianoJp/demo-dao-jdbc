package aplicacao;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.VendedorDao;
import model.entities.Departamento;
import model.entities.Vendedor;

public class Programa {

	public static void main(String[] args) {

		//Departamento obj = new Departamento(1, "livros");

		
		//Vendedor vendedor = new Vendedor(21, "bob", "bob@bob.com", new Date(), 3000.0, obj);
		
		VendedorDao vendedorDao = DaoFactory.criarVendedorDao();
		
		System.out.println("=== teste 1 vendedor - encontre pelo id ===");
		Vendedor vendedor =  vendedorDao.encontrePeloId(3);
		
		System.out.println(vendedor);
		
		System.out.println("\n=== teste 2 vendedor - encontre pelo departamento ===");
		
		Departamento depart = new Departamento(2, null);
		List<Vendedor> lista = vendedorDao.encontrePeloDepartamento(depart);
		
		for (Vendedor obj : lista) {
			System.out.println(obj);
		}
	}
}
