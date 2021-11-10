package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import db.DB;
import db.DbException;
import model.dao.VendedorDao;
import model.entities.Departamento;
import model.entities.Vendedor;

public class VendedorDaoJDBC implements VendedorDao {

	private Connection conn;

	public VendedorDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void inserir(Vendedor obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("INSERT INTO seller " + "(Name, Email, BirthDate, BaseSalary, DepartmentId) "
					+ "VALUES " + "(?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
			st.setString(1, obj.getNome());
			st.setString(2, obj.getEmail());
			st.setDate(3, new java.sql.Date(obj.getDataNiver().getTime()));
			st.setDouble(4, obj.getSalarioBase());
			st.setInt(5, obj.getDepartamento().getId());

			int linhasAfetadas = st.executeUpdate();

			// se esse if der certo quer dizer que ele add
			// e que o mysql add umid que nao sei qual e
			if (linhasAfetadas > 0) {
				// ele pega u result set e pega o id que foi criado automaticamente e coloca na
				// rs
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					// vale lembrar que esse 1 referese ao resut set que pegou o id
					// nao e o 1 que adiciona um novo vendedor
					int id = rs.getInt(1);
					obj.setId(id);
				}
				DB.closeResultSet(rs);
			} else {
				throw new DbException("erro no sistema nao conseguiu adicionar o vendedor.");
			}
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}

	}

	@Override
	public void atualizar(Vendedor obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("UPDATE seller " + "SET Name = ?, Email = ?, BirthDate = ?, BaseSalary = ?, "
					+ "DepartmentId = ? " + "WHERE Id = ?");
			st.setString(1, obj.getNome());
			st.setString(2, obj.getEmail());
			st.setDate(3, new java.sql.Date(obj.getDataNiver().getTime()));
			st.setDouble(4, obj.getSalarioBase());
			st.setInt(5, obj.getDepartamento().getId());
			// nesse ultimo insert ele ta colocando a 6 ? o id para o where buscar o id do
			// obj
			st.setInt(6, obj.getId());

			int linhasAfetadas = st.executeUpdate();

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}

	}

	@Override
	public void deletePeloId(Integer id) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("DELETE FROM seller WHERE Id = ?");

			st.setInt(1, id);

			 int linha = st.executeUpdate();
			 
			 if(linha == 0) {
				 throw new DbException("erro id nao existe");
			 }
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public Vendedor encontrePeloId(Integer id) {

		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT seller.*, department.Name as DepName FROM seller "
					+ "inner join department ON seller.DepartmentId = department.Id " + "WHERE seller.Id = ?");

			st.setInt(1, id);
			rs = st.executeQuery();

			if (rs.next()) {
				Departamento dep = instaciarDepartamento(rs);

				Vendedor ven = instanciarVendedor(rs, dep);
				return ven;
			}
			return null;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	private Vendedor instanciarVendedor(ResultSet rs, Departamento dep) throws SQLException {
		Vendedor obj = new Vendedor();
		obj.setId(rs.getInt("Id"));
		obj.setNome(rs.getString("Name"));
		obj.setEmail(rs.getString("Email"));
		obj.setDataNiver(rs.getDate("BirthDate"));
		obj.setSalarioBase(rs.getDouble("BaseSalary"));
		obj.setDepartamento(dep);
		return obj;
	}

	private Departamento instaciarDepartamento(ResultSet rs) throws SQLException {
		Departamento dep = new Departamento();
		dep.setId(rs.getInt("DepartmentId"));
		dep.setNome(rs.getString("DepName"));
		return dep;
	}

	@Override
	public List<Vendedor> encontreTudo() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT seller.*, department.Name as DepName FROM seller inner join department ON seller.DepartmentId =\r\n"
							+ "department.Id order by Name");

			rs = st.executeQuery();

			List<Vendedor> lista = new ArrayList<>();
			Map<Integer, Departamento> map = new HashMap<>();

			while (rs.next()) {

				Departamento dep = map.get(rs.getInt("DepartmentId"));

				if (dep == null) {
					dep = instaciarDepartamento(rs);
					map.put(rs.getInt("DepartmentId"), dep);
				}

				Vendedor ven = instanciarVendedor(rs, dep);
				lista.add(ven);
			}
			return lista;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	// esse e complicado porem ta explicado
	@Override
	public List<Vendedor> encontrePeloDepartamento(Departamento departamento) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT seller.*, department.Name as DepName FROM seller inner join department ON seller.DepartmentId =\r\n"
							+ "department.Id WHERE DepartmentId = ? order by Name");

			st.setInt(1, departamento.getId());
			rs = st.executeQuery();

			// primeiro instaciasse uma lista vazia para guardar os dados do vendedor pelo
			// departamento
			List<Vendedor> lista = new ArrayList<>();
			// depois cria um map pra sempre colocar cada vendedor no mesmo departamento..
			// no mais agr esse map ta vazio
			Map<Integer, Departamento> map = new HashMap<>();
			// percorrendo o map
			while (rs.next()) {

				// na primeira verificacao ele ve se tem algum departamento com o id do
				// rs.getint
				// se o map get existir o dep vai pegar o valor dele... ele vai receber o
				// departamtno
				// entao ele n vai ser null ..por tanto o if vai dar falso e o departamento vai
				// pegar o valor que ja existia
				// reaproveitando o deperamtno que eu digitar no programa que no caso e o 2
				Departamento dep = map.get(rs.getInt("DepartmentId"));

				// se esse if der nulo e pq o while nao tinha pegado o departamento ainda entao
				// ele vai armazenar no dep o dep 2
				// por tanto so vai funcionar uma vez ja que ele n aceita repeticoes..
				// garantindo assim que todos os obj do rs
				// aponte sempre pro departamento 2.. cada objeto sempre vai apontar pra um dep
				// .. evitando assimq que ele crie 2 departamentos iguais
				if (dep == null) {
					dep = instaciarDepartamento(rs);
					map.put(rs.getInt("DepartmentId"), dep);
				}

				// quando der null ele pega o dep e adiciona o 1
				// ja na seg interacao 0 if vai dar falso e ele reaproveita o dep passado.
				Vendedor ven = instanciarVendedor(rs, dep);
				lista.add(ven);
			}
			return lista;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

}
