package aplicacao;

import java.util.Date;

import model.entities.Departamento;
import model.entities.Vendedor;

public class Programa {

	public static void main(String[] args) {

		Departamento obj = new Departamento(1, "livros");

		
		Vendedor vendedor = new Vendedor(21, "bob", "bob@bob.com", new Date(), 3000.0, obj);
		
		
		System.out.println(vendedor);
	}
}
