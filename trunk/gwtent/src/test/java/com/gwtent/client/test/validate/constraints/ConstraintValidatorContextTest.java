// $Id: ConstraintValidatorContextTest.java 19547 2010-05-19 15:40:07Z hardy.ferentschik $
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
package com.gwtent.client.test.validate.constraints;

import java.util.List;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.junit.Test;

import com.gwtent.client.test.validate.VTestCase;
import com.gwtent.client.test.validate.util.TestUtil;
import com.gwtent.validate.client.impl.ConstraintValidatorContextImpl;
import com.gwtent.validate.client.impl.MessageAndPath;
import com.gwtent.validate.client.impl.PathImpl;

import static com.gwtent.client.test.validate.util.TestUtil.assertCorrectPropertyPaths;
import static com.gwtent.client.test.validate.util.TestUtil.assertNumberOfViolations;

/**
 * @author Hardy Ferentschik
 */
public class ConstraintValidatorContextTest extends VTestCase{

	/**
	 * HV-198
	 */
	@Test
	public void testCorrectSubNodePath() {
		Validator validator = TestUtil.getValidator();

		Item item = new Item();
		item.interval = new Interval();
		item.interval.start = 10;
		item.interval.end = 5;

		Set<ConstraintViolation<Item>> constraintViolations = validator.validate( item );
		assertNumberOfViolations( constraintViolations, 1 );
		assertCorrectPropertyPaths( constraintViolations, "interval.start" );
	}

	/**
	 * HV-208
	 */
	@Test
	public void testCorrectPath() {
		Validator validator = TestUtil.getValidator();

		Item item = new Item();
		Interval interval = new Interval();
		item.interval = interval;
		item.interval.start = 10;
		item.interval.end = 5;

		Set<ConstraintViolation<Interval>> constraintViolations = validator.validate( interval );
		assertNumberOfViolations( constraintViolations, 1 );
		assertCorrectPropertyPaths( constraintViolations, "start" );
	}

	@Test
	public void testDifferentPaths() {
		String message = "message";
		ConstraintValidatorContextImpl context = createEmptyConstraintValidatorContextImpl();
		context.buildConstraintViolationWithTemplate( message )
				.addNode( "foo" )
				.addNode( "bar" ).inIterable().atIndex( 3 )
				.addConstraintViolation();

		List<MessageAndPath> messageAndPathList = context.getMessageAndPathList();
		assertMessageAndPath( messageAndPathList.get( 0 ), message, "foo[3].bar" );


		context = createEmptyConstraintValidatorContextImpl();
		context.buildConstraintViolationWithTemplate( message )
				.addNode( "foo" )
				.addNode( null ).inIterable().atKey( "test" )
				.addConstraintViolation();

		messageAndPathList = context.getMessageAndPathList();
		assertMessageAndPath( messageAndPathList.get( 0 ), message, "foo[test]" );

		context = createEmptyConstraintValidatorContextImpl();
		context.buildConstraintViolationWithTemplate( message )
				.addNode( "foo" )
				.addNode( "bar" ).inIterable().atKey( "test" )
				.addNode( "fubar" )
				.addConstraintViolation();

		messageAndPathList = context.getMessageAndPathList();
		assertMessageAndPath( messageAndPathList.get( 0 ), message, "foo[test].bar.fubar" );

		context = createEmptyConstraintValidatorContextImpl();
		context.buildConstraintViolationWithTemplate( message )
				.addNode( "foo" )
				.addNode( "bar" ).inIterable().atKey( "test" )
				.addNode( "fubar" ).inIterable().atIndex( 10 )
				.addConstraintViolation();

		messageAndPathList = context.getMessageAndPathList();
		assertMessageAndPath( messageAndPathList.get( 0 ), message, "foo[test].bar[10].fubar" );

		context = createEmptyConstraintValidatorContextImpl();
		context.buildConstraintViolationWithTemplate( message )
				.addNode( "foo" )
				.addNode( "bar" ).inIterable().atKey( "test" )
				.addNode( "fubar" ).inIterable()
				.addConstraintViolation();

		messageAndPathList = context.getMessageAndPathList();
		assertMessageAndPath( messageAndPathList.get( 0 ), message, "foo[test].bar[].fubar" );
	}

	@Test
	public void testMultipleMessages() {
		String message1 = "message1";
		String message2 = "message2";
		ConstraintValidatorContextImpl context = createEmptyConstraintValidatorContextImpl();
		context.buildConstraintViolationWithTemplate( message1 )
				.addNode( "foo" )
				.addNode( "bar" ).inIterable().atKey( "key" )
				.addConstraintViolation();
		context.buildConstraintViolationWithTemplate( message2 )
				.addConstraintViolation();

		List<MessageAndPath> messageAndPathList = context.getMessageAndPathList();
		assertTrue( messageAndPathList.size() == 2 );
		assertMessageAndPath( messageAndPathList.get( 0 ), message1, "foo[key].bar" );
		assertMessageAndPath( messageAndPathList.get( 1 ), message2, "" );
	}

	private ConstraintValidatorContextImpl createEmptyConstraintValidatorContextImpl() {
		ConstraintValidatorContextImpl context = new ConstraintValidatorContextImpl(
				PathImpl.createNewPath( null ), null
		);
		context.disableDefaultConstraintViolation();
		return context;
	}

	private void assertMessageAndPath(MessageAndPath messageAndPath, String expectedMessage, String expectedPath) {
		assertEquals( messageAndPath.getPath(), PathImpl.createPathFromString( expectedPath ), "Wrong path" );
		assertEquals( messageAndPath.getMessage(), expectedMessage, "Wrong message" );
	}
}
