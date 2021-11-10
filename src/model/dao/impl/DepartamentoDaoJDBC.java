package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.DepartamentoDao;
import model.entities.Departamento;
import model.entities.Vendedor;

public class DepartamentoDaoJDBC implements DepartamentoDao {

	private Connection conn;

	public DepartamentoDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void inserir(Departamento obj) {

		PreparedStatement st = null;

		try {
			st = conn.prepareStatement("INSERT INTO department (name) VALUES (?)", Statement.RETURN_GENERATED_KEYS);

			st.setString(1, obj.getNome());

			int linhaAfetada = st.executeUpdate();

			if (linhaAfetada > 0) {
				System.out.println("adicionada com sucesso DEPARTAMENTO");

				ResultSet rs = st.getGeneratedKeys();

				if (rs.next()) {

					int i = rs.getInt(1);
					obj.setId(i);
				}
				DB.closeResultSet(rs);
			} else {
				throw new DbException("erro no sistema nao conseguiu adicionar o departamento");
			}
		} catch (SQLException e) {
			throw new DbException("erro iterno" + e.getMessage());
		} finally {
			DB.closeStatement(st);
		}

	}

	@Override
	public void atualizar(Departamento obj) {
		PreparedStatement st = null;

		try {

			st = conn.prepareStatement(

					"UPDATE department " + "SET Name = ? WHERE Id = ?");

			st.setString(1, obj.getNome());
			// pegando o id do obj
			st.setInt(2, obj.getId());

			int linhasAfetadas = st.executeUpdate();

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeResultSet(null);
		}

	}

	@Override
	public void deletePeloId(Integer id) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("Delete from department WHERE Id = ?");

			st.setInt(1, id);

			int linha = st.executeUpdate();

			if (linha == 0) {
				throw new DbException("erro id nao existe");
			}

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public Departamento encontrePeloId(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			st = conn.prepareStatement("SELECT * from department where id = ?");

			st.setInt(1, id);
			rs = st.executeQuery();

			if (rs.next()) {
				Departamento dep = instaciarDepartamento(rs);
				return dep;
			}
			return null;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	@Override
	public List<Departamento> encontreTudo() {
		PreparedStatement st = null;
		ResultSet rs = null;
		List<Departamento> lista = new ArrayList<>();

		try {

			st = conn.prepareStatement("SELECT * from department");

			rs = st.executeQuery();

			while (rs.next()) {

				Departamento dep = instaciarDepartamento(rs);
				lista.add(dep);
			}

			return lista;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
		}

	}

	private Departamento instaciarDepartamento(ResultSet rs) throws SQLException {

		Departamento dep = new Departamento();
		dep.setId(rs.getInt("Id"));
		dep.setNome(rs.getString("Name"));
		return dep;
	}

	public List<Vendedor> encontreVendedoresPeloDep(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {

			st = conn.prepareStatement("SELECT s.* FROM cursojdbc.seller as s join department as d"
					+ " on s.DepartmentId = d.Id WHERE d.Id = ? order by Name");

			st.setInt(1, id);
			rs = st.executeQuery();

			List<Vendedor> lista = new ArrayList<>();

			while (rs.next()) {
				Vendedor obj = new Vendedor();
				obj.setId(rs.getInt("Id"));
				obj.setNome(rs.getString("Name"));
				obj.setEmail(rs.getString("Email"));
				obj.setDataNiver(rs.getDate("BirthDate"));
				obj.setSalarioBase(rs.getDouble("BaseSalary"));

				lista.add(obj);
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
