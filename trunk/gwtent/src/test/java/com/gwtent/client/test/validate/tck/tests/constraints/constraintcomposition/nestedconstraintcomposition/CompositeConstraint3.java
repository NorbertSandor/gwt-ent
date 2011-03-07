// $Id: CompositeConstraint3.java 17620 2009-10-04 19:19:28Z hardy.ferentschik $
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
package com.gwtent.client.test.validate.tck.tests.constraints.constraintcomposition.nestedconstraintcomposition;

import java.lang.annotation.Target;
import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.FIELD;
import javax.validation.constraints.Pattern;
import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * @author Hardy Ferentschik
 */
@Pattern(regexp = "abc", message = "Pattern must match {regexp}")
@Target({ METHOD, FIELD })
@Retention(RUNTIME)
@Constraint(validatedBy = { })
@NestedCompositeConstraint
public @interface CompositeConstraint3 {
	public abstract String message() default "CompositeConstraint3 failed.";

	public abstract Class<?>[] groups() default { };

	public abstract Class<? extends Payload>[] payload() default { };
}