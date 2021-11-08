package aplicacao;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

import model.dao.DaoFactory;
import model.dao.VendedorDao;
import model.entities.Departamento;
import model.entities.Vendedor;

public class Programa {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);

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
		
		System.out.println("\n=== teste 3 vendedor - encontre todos ===");
		lista = vendedorDao.encontreTudo();
		for (Vendedor obj : lista) {
			System.out.println(obj);
		}
		
		System.out.println("\n=== teste 4 vendedor - Inserir vendedor ===");
		Vendedor novoVendedor = new Vendedor(null, "Greg", "greg@gmail.com", new Date(), 5000.0, depart);
		vendedorDao.inserir(novoVendedor);
		System.out.println("Inserido id do vendedor : " + novoVendedor.getId());
		
		
		
		System.out.println("\n=== teste 5 vendedor - atualizando dados do vendedor ===");
		vendedor = vendedorDao.encontrePeloId(1);
		vendedor.setNome("Bruce Wayne");
		vendedorDao.atualizar(vendedor);
		System.out.println("Atualizacao concluida verifique o workbench");
		
		System.out.println("\n=== teste 6 vendedor - deletar vendedor ===");
		System.out.println("Entre com o id do vendedor para excluir");
		int id = in.nextInt();
		vendedorDao.deletePeloId(id);
		System.out.println("Deletado com sucesso");
		
		
		
		
		in.close();

		
		
	}
	

	
	
	
}
