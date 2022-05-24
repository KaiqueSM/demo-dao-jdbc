package model.dao.imp;

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
import entities.Department;
import entities.Seller;
import model.dao.SellerDao;

public class SellerDaoJDBC implements SellerDao{

	private Connection conn;
	
	public SellerDaoJDBC(Connection conn) {
		this.conn = conn;
	}
		
	@Override
	public void insert(Seller obj) {

		PreparedStatement st = null;
		
		try {
			st = conn.prepareStatement("INSERT INTO seller\r\n"
					+ "(Name, Email, BirthDate, BaseSalary, DepartmentId)\r\n"
					+ "VALUES\r\n"
					+ "(?, ?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			
			st.setString(1, obj.getName());
			st.setString(2, obj.getEmail());
			st.setDate(3, new java.sql.Date(obj.getBirthDate().getTime()));
			st.setDouble(4, obj.getBaseSalary());
			st.setInt(5, obj.getDepartment().getId());
			
			int rowsAffected = st.executeUpdate();
			
			if (rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					obj.setId(id);
				}
				DB.closeResultSet(rs);
			}else
				throw new DbException("Unexpected error! No rows affected!");
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new DbException(e.getMessage());
		}finally {
			DB.closeStatement(st);
		}
		
	}

	@Override
	public void update(Seller obj) {

		PreparedStatement st = null;
		
		try {
			st = conn.prepareStatement("UPDATE seller\r\n"
					+ "SET Name = ?, Email = ?, BirthDate = ?, BaseSalary = ?, DepartmentId = ?\r\n"
					+ "WHERE Id = ?");
			
			st.setString(1, obj.getName());
			st.setString(2, obj.getEmail());
			st.setDate(3, new java.sql.Date(obj.getBirthDate().getTime()));
			st.setDouble(4, obj.getBaseSalary());
			st.setInt(5, obj.getDepartment().getId());
			st.setInt(6, obj.getId());
			
			int rowsAffected = st.executeUpdate();
				
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new DbException(e.getMessage());
		}finally {
			DB.closeStatement(st);
		}
		
	}

	@Override
	public void deletById(Integer id) {

		PreparedStatement st = null;
		
		try {
			st = conn.prepareStatement("DELETE FROM seller\r\n"
					+ "WHERE Id = ?");
			
			st.setInt(1, id);
			
			int rowsAffected = st.executeUpdate();
				
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new DbException(e.getMessage());
		}finally {
			DB.closeStatement(st);
		}
		
	}

	@Override
	public Seller findById(Integer id) {

		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = conn.prepareStatement("SELECT seller.*,department.Name as DepName\r\n"
					+ "FROM seller INNER JOIN department\r\n"
					+ "ON seller.DepartmentId = department.Id\r\n"
					+ "WHERE seller.Id = ?");
			
			st.setInt(1,id);
			rs = st.executeQuery();
			
			if (rs.next()) {

				Department dep = instantiatDepartment(rs);	
				Seller seller = instantiatSeller(rs,dep);
				
				return seller;
			}
			return null;
		}catch(SQLException e) {
			throw new DbException(e.getMessage());
		}finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}

	}

	private Seller instantiatSeller(ResultSet rs, Department dep) throws SQLException {
		// TODO Auto-generated method stub
		return new Seller(rs.getInt("Id"), 
				rs.getString("Name"), 
				rs.getString("Email"), 
				rs.getDate("BirthDate"), 
				rs.getDouble("BaseSalary"), 
				dep);
	}

	private Department instantiatDepartment(ResultSet rs) throws SQLException {
		// TODO Auto-generated method stub
		return new Department(
				rs.getInt("DepartmentId"),
				rs.getString("DepName"));
	}

	@Override
	public List<Seller> findAll() {

		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = conn.prepareStatement("SELECT seller.*,department.Name as DepName\r\n"
					+ "FROM seller INNER JOIN department\r\n"
					+ "ON seller.DepartmentId = department.Id\r\n"
					+ "ORDER BY Name");
			
			rs = st.executeQuery();
			
			List<Seller> list = new ArrayList<>();
			Map<Integer, Department> map = new HashMap<>();
			
			while (rs.next()) {

				Department dep = map.get(rs.getInt("DepartmentId"));
				
				if (dep == null) {
					dep = instantiatDepartment(rs);	
					map.put(rs.getInt("DepartmentId"),dep);
				}
				
				Seller seller = instantiatSeller(rs,dep);
				
				list.add(seller);
			}
			return list;
		}catch(SQLException e) {
			throw new DbException(e.getMessage());
		}finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
		
	}

	@Override
	public List<Seller> findNyDepartment(Department department) {

		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = conn.prepareStatement("SELECT seller.*,department.Name as DepName\r\n"
					+ "FROM seller INNER JOIN department\r\n"
					+ "ON seller.DepartmentId = department.Id\r\n"
					+ "WHERE DepartmentId = ?\r\n"
					+ "ORDER BY Name");
			
			st.setInt(1,department.getId());
			rs = st.executeQuery();
			
			List<Seller> list = new ArrayList<>();
			Map<Integer, Department> map = new HashMap<>();
			
			while (rs.next()) {

				Department dep = map.get(rs.getInt("DepartmentId"));
				
				if (dep == null) {
					dep = instantiatDepartment(rs);	
					map.put(rs.getInt("DepartmentId"),dep);
				}
				
				Seller seller = instantiatSeller(rs,dep);
				
				list.add(seller);
			}
			return list;
		}catch(SQLException e) {
			throw new DbException(e.getMessage());
		}finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
		
	}

}
