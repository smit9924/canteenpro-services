package com.app.canteenpro;

import com.app.canteenpro.database.models.Roles;
import com.app.canteenpro.database.repositories.RolesRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CanteenproApplication implements CommandLineRunner {
	@Autowired
	private RolesRepo rolesRepo;

	public static void main(String[] args) {
		SpringApplication.run(CanteenproApplication.class, args);
	}

	// ****************************
	// *** Loading initial Data ***
	// ****************************

	@Override
	public void run(String... args) throws Exception {
		Roles role;
//		String[] userroles = {"admin", "owner", "manager", "kitchener", "waiter", "cashier", "customer"};
//		int[] level = {1, 2, 3, 4, 5, 6, 7};
//
//		for(int index = 0; index < userroles.length; index++) {
//			// ADMIN
//			role = new Roles();
//			role.setRole(userroles[index]);
//			role.setLevel(level[index]);
//			rolesRepo.save(role);
//		}

		System.out.println(rolesRepo.findAll());


	}
}