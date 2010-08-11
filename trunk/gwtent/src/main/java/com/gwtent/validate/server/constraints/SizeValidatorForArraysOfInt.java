package com.gwtent.validate.server.constraints;

import java.lang.reflect.Array;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraints.Size;

import com.gwtent.validate.client.constraints.impl.SizeValidatorForArraysOfPrimitives;

/**
 * @author Hardy Ferentschik
*/
public class SizeValidatorForArraysOfInt extends SizeValidatorForArraysOfPrimitives
		implements ConstraintValidator<Size, int[]> {

	/**
	 * Checks the number of entries in an array.
	 *
	 * @param array The array to validate.
	 * @param constraintValidatorContext context in which the constraint is evaluated.
	 *
	 * @return Returns <code>true</code> if the array is <code>null</code> or the number of entries in
	 *         <code>array</code> is between the specified <code>min</code> and <code>max</code> values (inclusive),
	 *         <code>false</code> otherwise.
	 */
	public boolean isValid(int[] array, ConstraintValidatorContext constraintValidatorContext) {
		if ( array == null ) {
			return true;
		}
		int length = Array.getLength( array );
		return length >= min && length <= max;
	}
}