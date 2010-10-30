// $Id: ValidatePropertyTest.java 20225 2010-08-23 14:02:20Z hardy.ferentschik $
/*
* JBoss, Home of Professional Open Source
* Copyright 2009, Red Hat, Inc. and/or its affiliates, and individual contributors
* by the @authors tag. See the copyright.txt in the distribution for a
* full listing of individual contributors.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
* http://www.apache.org/licenses/LICENSE-2.0
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,  
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package com.gwtent.client.test.validate.tck.tests.validation;

import static com.gwtent.client.test.validate.tck.util.TestUtil.assertConstraintViolation;
import static com.gwtent.client.test.validate.tck.util.TestUtil.assertCorrectConstraintTypes;
import static com.gwtent.client.test.validate.tck.util.TestUtil.assertCorrectConstraintViolationMessages;
import static com.gwtent.client.test.validate.tck.util.TestUtil.assertCorrectNumberOfViolations;
import static org.testng.Assert.fail;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ValidationException;
import javax.validation.Validator;
import javax.validation.constraints.Size;

import com.gwtent.client.test.validate.tck.AbstractTest;
import com.gwtent.client.test.validate.tck.SpecAssertion;
import com.gwtent.client.test.validate.tck.SpecAssertions;
import com.gwtent.client.test.validate.tck.Test;
import com.gwtent.client.test.validate.tck.util.TestUtil;

/**
 * Tests for the implementation of {@code Validator}.
 *
 * @author Hardy Ferentschik
 */

public class ValidatePropertyTest extends AbstractTest {

	@Test
	@SpecAssertion(section = "4.1.1", id = "e")
	@SuppressWarnings("NullArgumentToVariableArgMethod")
	public void testPassingNullAsGroup() {
		Validator validator = TestUtil.getValidatorUnderTest();
		Customer customer = new Customer();

		try {
			validator.validateProperty( customer, "firstName", null );
			fail();
		}
		catch ( IllegalArgumentException e ) {
			// success
		}
	}

	@Test
	@SpecAssertion(section = "4.1.1", id = "e")
	public void testIllegalArgumentExceptionIsThrownForNullValue() {
		Validator validator = TestUtil.getValidatorUnderTest();
		try {
			validator.validateProperty( null, "firstName" );
			fail();
		}
		catch ( IllegalArgumentException e ) {
			// success
		}
	}

	@Test
	@SpecAssertions({
			@SpecAssertion(section = "4.1.1", id = "e"),
			@SpecAssertion(section = "4.1.1", id = "f")
	})
	public void testValidatePropertyWithInvalidPropertyPath() {
		Validator validator = TestUtil.getValidatorUnderTest();

		Customer customer = new Customer();
		try {
			validator.validateProperty( customer, "foobar" );
			fail();
		}
		catch ( IllegalArgumentException e ) {
			// success
		}


		// firstname exists, but the capitalisation is wrong
		try {
			validator.validateProperty( customer, "FirstName" );
			fail();
		}
		catch ( IllegalArgumentException e ) {
			// success
		}
	}

	@Test
	@SpecAssertion(section = "4.1.1", id = "e")
	public void testValidatePropertyWithNullProperty() {
		Validator validator = TestUtil.getValidatorUnderTest();

		Customer customer = new Customer();

		try {
			validator.validateProperty( customer, null );
			fail();
		}
		catch ( IllegalArgumentException e ) {
			// success
		}
	}

	@Test
	@SpecAssertion(section = "4.1.1", id = "e")
	public void testValidatePropertyWithEmptyProperty() {
		Validator validator = TestUtil.getValidatorUnderTest();

		Customer customer = new Customer();
		Order order = new Order();
		customer.addOrder( order );

		try {
			validator.validateProperty( customer, "" );
			fail();
		}
		catch ( IllegalArgumentException e ) {
			// success
		}
	}

	@Test
	@SpecAssertions({
			@SpecAssertion(section = "4.1.1", id = "c"),
			@SpecAssertion(section = "4.1.1", id = "d"),
			@SpecAssertion(section = "4.1.1", id = "f")
	})
	public void testValidateProperty() {
		Validator validator = TestUtil.getValidatorUnderTest();

		Address address = new Address();
		address.setStreet( null );
		address.setZipCode( null );
		String townInNorthWales = "Llanfairpwllgwyngyllgogerychwyrndrobwyll-llantysiliogogogoch";
		address.setCity( townInNorthWales );

		Set<ConstraintViolation<Address>> constraintViolations = validator.validateProperty( address, "city" );
		assertCorrectNumberOfViolations( constraintViolations, 1 );
		assertCorrectConstraintTypes( constraintViolations, Size.class );
		ConstraintViolation<Address> violation = constraintViolations.iterator().next();
		assertConstraintViolation( violation, Address.class, townInNorthWales, "city" );
		assertCorrectConstraintViolationMessages(
				constraintViolations, "City name cannot be longer than 30 characters."
		);

		address.setCity( "London" );
		constraintViolations = validator.validateProperty( address, "city" );
		assertCorrectNumberOfViolations( constraintViolations, 0 );
	}

	@Test
	@SpecAssertion(section = "4.1.1", id = "g")
	public void testValidIsNotHonoredValidateProperty() {
		Validator validator = TestUtil.getValidatorUnderTest();

		Customer customer = new Customer();
		Order order = new Order();
		customer.addOrder( order );

		Set<ConstraintViolation<Customer>> constraintViolations = validator.validateProperty( customer, "orders" );
		assertCorrectNumberOfViolations( constraintViolations, 0 );
	}

	@Test(expectedExceptions = ValidationException.class)
	@SpecAssertion(section = "4.1.1", id = "k")
	public void testUnexpectedExceptionsInValidatePropertyGetWrappedInValidationExceptions() {
		Validator validator = TestUtil.getValidatorUnderTest();
		validator.validateProperty( new BadlyBehavedEntity(), "value" );
	}
}