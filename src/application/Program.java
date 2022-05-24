package application;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

import entities.Department;
import entities.Seller;
import model.dao.DaoFactory;
import model.dao.SellerDao;

public class Program {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Scanner sc = new Scanner(System.in);
		SellerDao sellerDao = DaoFactory.createSellerDao(); 
		
		
		System.out.println("Test Find By Id Seller:");
		
		Seller seller = sellerDao.findById(1);
		
		System.out.println(seller);
		
		
		System.out.println("Test Find By Department:");

		Department dep = new Department(2, null);
		List<Seller> list = sellerDao.findNyDepartment(dep);
		
		list.forEach(System.out::println);
		
		
		System.out.println("Test Find All:");

		list = sellerDao.findAll();
		
		list.forEach(System.out::println);
		
		System.out.println("Test Insert");

		seller = new Seller(null, "Greg", "greg@gmai.com", new Date(), 4000.00, dep);
		sellerDao.insert(seller);
		
		System.out.println("Inserted! New id = "+seller.getId());
	
		System.out.println("Test Update");

		seller.setName("Greg Orange");
		sellerDao.update(seller);
		
		System.out.println("Updated! Atual data of user = "+sellerDao.findById(seller.getId()));
		
		System.out.println("Test Delete");
		System.out.print("Enter id for delete test: ");
		sellerDao.deletById(sc.nextInt()) ;
	}

}
