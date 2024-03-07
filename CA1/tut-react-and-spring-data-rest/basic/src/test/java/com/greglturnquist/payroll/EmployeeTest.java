package com.greglturnquist.payroll;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeTest {
	@Test
	void validEmployeeCreated() {
		String firstName = "Frodo";
		String lastName = "Baggins";
		String description = "ring bearer";
		int jobYears = 50;
		Employee employee = new Employee(firstName,lastName,description,jobYears);
		assertNotNull(employee);
	}

	@Test
	void invalidEmployeeCreatedFirstNameEmpty() throws IllegalArgumentException {
		String firstName = "";
		String lastName = "Baggins";
		String description = "ring bearer";
		int jobYears = 50;
		assertThrows(IllegalArgumentException.class, () -> new Employee(firstName,lastName,description,jobYears));
	}

	@Test
	void invalidEmployeeCreatedFirstNameNull() throws IllegalArgumentException {
		String firstName = null;
		String lastName = "Baggins";
		String description = "ring bearer";
		int jobYears = 50;
		assertThrows(IllegalArgumentException.class, () -> new Employee(firstName,lastName,description,jobYears));
	}

	@Test
	void invalidEmployeeCreatedLastNameEmpty() throws IllegalArgumentException {
		String firstName = "Frodo";
		String lastName = "";
		String description = "ring bearer";
		int jobYears = 50;
		assertThrows(IllegalArgumentException.class, () -> new Employee(firstName,lastName,description,jobYears));
	}

	@Test
	void invalidEmployeeCreatedLastNameNull() throws IllegalArgumentException {
		String firstName = "Frodo";
		String lastName = null;
		String description = "ring bearer";
		int jobYears = 50;
		assertThrows(IllegalArgumentException.class, () -> new Employee(firstName,lastName,description,jobYears));
	}

	@Test
	void invalidEmployeeCreatedDescriptionEmpty() throws IllegalArgumentException {
		String firstName = "Frodo";
		String lastName = "Baggins";
		String description = "";
		int jobYears = 50;
		assertThrows(IllegalArgumentException.class, () -> new Employee(firstName,lastName,description,jobYears));
	}

	@Test
	void invalidEmployeeCreatedCreatedNull() throws IllegalArgumentException {
		String firstName = "Frodo";
		String lastName = "Baggins";
		String description = null;
		int jobYears = 50;
		assertThrows(IllegalArgumentException.class, () -> new Employee(firstName,lastName,description,jobYears));
	}

	@Test
	void validEmployeeCreatedZeroJobYears() {
		String firstName = "Frodo";
		String lastName = "Baggins";
		String description = "ring bearer";
		int jobYears = 0;
		Employee employee = new Employee(firstName,lastName,description,jobYears);
		assertNotNull(employee);		;
	}

	@Test
	void invalidEmployeeCreatedJobYearsNegativeValue() throws IllegalArgumentException {
		String firstName = "Frodo";
		String lastName = "Baggins";
		String description = "ring bearer";
		int jobYears = -1;
		assertThrows(IllegalArgumentException.class, () -> new Employee(firstName,lastName,description,jobYears));
	}
}