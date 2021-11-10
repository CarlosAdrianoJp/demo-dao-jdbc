package aplicacao;

import java.util.List;
import java.util.Scanner;

import model.dao.DaoFactory;
import model.dao.DepartamentoDao;
import model.entities.Departamento;
import model.entities.Vendedor;

public class Programa1 {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);

		DepartamentoDao departamentoDao = DaoFactory.criarDepartamentoDao();

		/*
		 * System.out.println("=== Inserir Departamento ===="); Departamento
		 * novoDepartamento = new Departamento(null, "Toys");
		 * departamentoDao.inserir(novoDepartamento);
		 * System.out.println("Depart inserido.. id = " + novoDepartamento.getId());
		 * System.out.println();
		 * 
		 */

		System.out.println("=== Atualizando Departamento ===");
		Departamento departamento = departamentoDao.encontrePeloId(3);
		departamento.setNome("Brinquedos");
		departamentoDao.atualizar(departamento);
		System.out.println("Atualizacao concluida verifique o workbench");

		System.out.println();

		System.out.println("=== Encontrando Departamento Pelo Id===");
		departamento = departamentoDao.encontrePeloId(2);
		System.out.println(departamento);

		System.out.println();

		System.out.println("=== Encontre Todos ===");
		List<Departamento> lista = departamentoDao.encontreTudo();
		for (Departamento obj : lista) {
			System.out.println(obj);
		}

		System.out.println();

		System.out.println("=== Deletando Pelo ID ===");

		/*
		 * 
		System.out.println("digite o id no qual vc quer deletar");
		int id = in.nextInt();
		departamentoDao.deletePeloId(id);
		System.out.println("Deletado com sucesso");
		
		*/

		System.out.println();

		System.out.println("=== bonus encontrando os vendedores pelo id digitado no departamento === \n");
	
		System.out.println("digite o id do departamento para buscar os vendedores");
		int id = in.nextInt();
		
		 List<Vendedor> listaV = departamentoDao.encontreVendedoresPeloDep(id
				 );
		 for (Vendedor obj : listaV) {
				System.out.println(obj.mostrarVendedor());
			}
	}

}
