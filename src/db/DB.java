package db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DB {

	private static Connection conn = null;

	//metodo para conectar com o banco do mysql
	public static Connection getConnection() {

		if (conn == null) {
			try {
				Properties props = loadProperties();
				String url = props.getProperty("dburl");
				conn = DriverManager.getConnection(url, props);
			} 
			catch (SQLException e) {
				throw new DbException(e.getMessage());
			}
		}
		return conn;
	}
	
	//metodo para fechar a conexao com o banco do mysql
	 public static void closeConnection() {	
		 if(conn != null) {
			 try {
				conn.close();
			} catch (SQLException e) {
				throw new DbException(e.getMessage());
			}
		 }
	 }

	 //metodo para parregar o file db.properties na pasta raiz do projeto
	private static Properties loadProperties() {
		try (FileInputStream fs = new FileInputStream("db.properties")) {
			Properties props = new Properties();
			props.load(fs);
			return props;
		} catch (IOException e) {
			throw new DbException(e.getMessage());
		}
	}
	
	//metodo para fechar o Statement.. metodo auxiliar
	public static void closeStatement(Statement st) {
		if(st!= null) {
			try {
				st.close();
			} catch (SQLException e) {
				throw  new DbException(e.getMessage());
			}
		}
	}
	
	//metodo para fechar o result set.. metodo auxiliar
	public static void closeResultSet(ResultSet rs) {
		if (rs!= null) {
			try {
				rs.close();
			} catch (SQLException e) {
				throw  new DbException(e.getMessage());
			}
		}
	}
}
