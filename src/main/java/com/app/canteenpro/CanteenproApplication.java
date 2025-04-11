package com.app.canteenpro;

import com.app.canteenpro.database.models.ContactUsSubject;
import com.app.canteenpro.database.repositories.ContactUsSubjectRepo;
import com.app.canteenpro.database.repositories.RolesRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.UUID;

@SpringBootApplication
public class CanteenproApplication implements CommandLineRunner {
	@Autowired
	private RolesRepo rolesRepo;

	@Autowired
	private ContactUsSubjectRepo contactUsSubjectRepo;

	public static void main(String[] args) {
		SpringApplication.run(CanteenproApplication.class, args);
	}

	// ****************************
	// *** Loading initial Data ***
	// ****************************

	@Override
	public void run(String... args) throws Exception {
//		String[] contactusTopics = {
//				"General Inquiry",
//				"Technical Support",
//				"Billing Question",
//				"Partnership Opportunity",
//				"Other"
//		  };
//
//		  for(int index = 0; index < contactusTopics.length; index++) {
//			ContactUsSubject contactUsTopic = new ContactUsSubject();
//			contactUsTopic.setSubject(contactusTopics[index]);
//			contactUsTopic.setGuid(UUID.randomUUID().toString());
//			contactUsSubjectRepo.save(contactUsTopic);
//		  }

		// Roles role;
		// String[] userroles = {"admin", "owner", "manager", "kitchener", "waiter", "cashier", "customer"};
		// int[] level = {1, 2, 3, 4, 5, 6, 7};

		// for(int index = 0; index < userroles.length; index++) {
		//   	// ADMIN
		//  	role = new Roles();
		//  	role.setRole(userroles[index]);
		//  	role.setLevel(level[index]);
		//  	rolesRepo.save(role);
		//  }

		System.out.println(rolesRepo.findAll());


	}
}