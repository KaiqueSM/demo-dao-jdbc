package entities;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class Seller implements Serializable{


	private static final long serialVersionUID = 1L;
	private Integer Id;
	private String Name;
	private String Email;
	private Date BirthDate;
	private Double BaseSalary;
	
	private Department department;
	
	public Seller() {}

	public Seller(Integer id, 
			String name, 
			String email, 
			Date birthDate, 
			Double baseSalary, 
			Department department) {
		super();
		Id = id;
		Name = name;
		Email = email;
		BirthDate = birthDate;
		BaseSalary = baseSalary;
		this.department = department;
	}

	

	public Integer getId() {
		return Id;
	}


	public void setId(Integer id) {
		Id = id;
	}


	public String getName() {
		return Name;
	}


	public void setName(String name) {
		Name = name;
	}


	public String getEmail() {
		return Email;
	}


	public void setEmail(String email) {
		Email = email;
	}


	public Date getBirthDate() {
		return BirthDate;
	}


	public void setBirthDate(Date birthDate) {
		BirthDate = birthDate;
	}


	public Double getBaseSalary() {
		return BaseSalary;
	}


	public void setBaseSalary(Double baseSalary) {
		BaseSalary = baseSalary;
	}
	
	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

			
	@Override
	public int hashCode() {
		return Objects.hash(Id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Seller other = (Seller) obj;
		return Objects.equals(Id, other.Id);
	}

	@Override
	public String toString() {
		return "Id: " + Id + 
				", Name: " + Name + 
				", Email: " + Email + 
				", BirthDate: " + BirthDate + 
				", BaseSalary: "+ String.format("%.2f", BaseSalary)+
				"Department: Id: " + department.getId() + ", Name: " + department.getName();
	}
	
	
	
	
}
