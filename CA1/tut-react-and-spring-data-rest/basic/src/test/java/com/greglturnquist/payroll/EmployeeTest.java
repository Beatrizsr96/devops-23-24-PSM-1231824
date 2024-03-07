package com.greglturnquist.payroll;

import org.apache.naming.factory.SendMailFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeTest {
	@Test
	void validEmployeeCreated() {
		String firstName = "Frodo";
		String lastName = "Baggins";
		String description = "ring bearer";
		int jobYears = 50;
		String email = "frodo.baggins@live.org";
		Employee employee = new Employee(firstName,lastName,description,jobYears,email);
		assertNotNull(employee);
	}

	@Test
	void invalidEmployeeCreatedFirstNameEmpty() throws IllegalArgumentException {
		String firstName = "";
		String lastName = "Baggins";
		String description = "ring bearer";
		int jobYears = 50;
		String email = "frodo.baggins@live.org";
		assertThrows(IllegalArgumentException.class, () -> new Employee(firstName,lastName,description,jobYears,email));
	}

	@Test
	void invalidEmployeeCreatedFirstNameNull() throws IllegalArgumentException {
		String firstName = null;
		String lastName = "Baggins";
		String description = "ring bearer";
		int jobYears = 50;
		String email = "frodo.baggins@live.org";
		assertThrows(IllegalArgumentException.class, () -> new Employee(firstName,lastName,description,jobYears,email));
	}

	@Test
	void invalidEmployeeCreatedLastNameEmpty() throws IllegalArgumentException {
		String firstName = "Frodo";
		String lastName = "";
		String description = "ring bearer";
		int jobYears = 50;
		String email = "frodo.baggins@live.org";
		assertThrows(IllegalArgumentException.class, () -> new Employee(firstName,lastName,description,jobYears,email));
	}

	@Test
	void invalidEmployeeCreatedLastNameNull() throws IllegalArgumentException {
		String firstName = "Frodo";
		String lastName = null;
		String description = "ring bearer";
		int jobYears = 50;
		String email = "frodo.baggins@live.org";
		assertThrows(IllegalArgumentException.class, () -> new Employee(firstName,lastName,description,jobYears,email));
	}

	@Test
	void invalidEmployeeCreatedDescriptionEmpty() throws IllegalArgumentException {
		String firstName = "Frodo";
		String lastName = "Baggins";
		String description = "";
		int jobYears = 50;
		String email = "frodo.baggins@live.org";
		assertThrows(IllegalArgumentException.class, () -> new Employee(firstName,lastName,description,jobYears,email));
	}

	@Test
	void invalidEmployeeCreatedCreatedNull() throws IllegalArgumentException {
		String firstName = "Frodo";
		String lastName = "Baggins";
		String description = null;
		int jobYears = 50;
		String email = "frodo.baggins@live.org";
		assertThrows(IllegalArgumentException.class, () -> new Employee(firstName,lastName,description,jobYears,email));
	}

	@Test
	void validEmployeeCreatedZeroJobYears() {
		String firstName = "Frodo";
		String lastName = "Baggins";
		String description = "ring bearer";
		int jobYears = 0;
		String email = "frodo.baggins@live.org";
		Employee employee = new Employee(firstName,lastName,description,jobYears,email);
		assertNotNull(employee);		;
	}

	@Test
	void invalidEmployeeCreatedJobYearsNegativeValue() throws IllegalArgumentException {
		String firstName = "Frodo";
		String lastName = "Baggins";
		String description = "ring bearer";
		int jobYears = -1;
		String email = "frodo.baggins@live.org";
		assertThrows(IllegalArgumentException.class, () -> new Employee(firstName,lastName,description,jobYears,email));
	}

	@Test
	void invalidEmployeeCreatedEmptyEmail() throws IllegalArgumentException {
		String firstName = "Frodo";
		String lastName = "Baggins";
		String description = "ring bearer";
		int jobYears = 1;
		String email = null;
		assertThrows(IllegalArgumentException.class, () -> new Employee(firstName,lastName,description,jobYears,email));
	}

	@Test
	void invalidEmployeeCreatedNullEmail() throws IllegalArgumentException {
		String firstName = "Frodo";
		String lastName = "Baggins";
		String description = "ring bearer";
		int jobYears = 1;
		String email = "";
		assertThrows(IllegalArgumentException.class, () -> new Employee(firstName,lastName,description,jobYears,email));
	}
}