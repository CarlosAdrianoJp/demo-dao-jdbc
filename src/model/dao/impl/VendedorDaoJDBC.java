package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.VendedorDao;
import model.entities.Departamento;
import model.entities.Vendedor;

public class VendedorDaoJDBC  implements VendedorDao{
	
	private Connection conn;
	
	public VendedorDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void inserir(Vendedor obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void atualizar(Vendedor obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deletePeloId(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Vendedor encontrePeloId(Integer id) {

		PreparedStatement st = null;
		ResultSet rs = null;
		try {
		st = conn.prepareStatement(
				"SELECT seller.*, department.Name as DepName FROM seller "
				+ "inner join department ON seller.DepartmentId = department.Id " 
				+ "WHERE seller.Id = ?");
		
		st.setInt(1, id);
		rs = st.executeQuery();
		
		if(rs.next()) {
			Departamento dep = new Departamento();
			dep.setId(rs.getInt("DepartmentId"));
			dep.setNome(rs.getString("DepName"));
			
			Vendedor ven = new Vendedor();
			ven.setId(rs.getInt("Id"));
			ven.setNome(rs.getString("Name"));
			ven.setEmail(rs.getString("Email"));
			ven.setDataNiver(rs.getDate("BirthDate"));
			ven.setSalarioBase(rs.getDouble("BaseSalary"));
			ven.setDepartamento(dep);
			return ven;
		}
		return null;
		}catch(SQLException e){
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	@Override
	public List<Vendedor> encontreTudo() {
		// TODO Auto-generated method stub
		return null;
	}

}
