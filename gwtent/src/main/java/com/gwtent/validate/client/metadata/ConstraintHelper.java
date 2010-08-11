package com.gwtent.validate.client.metadata;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ValidationException;
import javax.validation.constraints.AssertFalse;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Future;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.gwtent.validate.client.constraints.impl.AssertFalseValidator;
import com.gwtent.validate.client.constraints.impl.AssertTrueValidator;
import com.gwtent.validate.client.constraints.impl.DecimalMaxValidatorForNumber;
import com.gwtent.validate.client.constraints.impl.DecimalMaxValidatorForString;
import com.gwtent.validate.client.constraints.impl.DecimalMinValidatorForNumber;
import com.gwtent.validate.client.constraints.impl.DecimalMinValidatorForString;
import com.gwtent.validate.client.constraints.impl.DigitsValidatorForNumber;
import com.gwtent.validate.client.constraints.impl.DigitsValidatorForString;
import com.gwtent.validate.client.constraints.impl.FutureValidatorForDate;
import com.gwtent.validate.client.constraints.impl.MaxValidatorForNumber;
import com.gwtent.validate.client.constraints.impl.MaxValidatorForString;
import com.gwtent.validate.client.constraints.impl.MinValidatorForNumber;
import com.gwtent.validate.client.constraints.impl.MinValidatorForString;
import com.gwtent.validate.client.constraints.impl.NotNullValidator;
import com.gwtent.validate.client.constraints.impl.NullValidator;
import com.gwtent.validate.client.constraints.impl.PastValidatorForDate;
import com.gwtent.validate.client.constraints.impl.PatternValidator;
import com.gwtent.validate.client.constraints.impl.SizeValidatorForArray;
import com.gwtent.validate.client.constraints.impl.SizeValidatorForArraysOfBoolean;
import com.gwtent.validate.client.constraints.impl.SizeValidatorForArraysOfByte;
import com.gwtent.validate.client.constraints.impl.SizeValidatorForArraysOfChar;
import com.gwtent.validate.client.constraints.impl.SizeValidatorForArraysOfDouble;
import com.gwtent.validate.client.constraints.impl.SizeValidatorForArraysOfFloat;
import com.gwtent.validate.client.constraints.impl.SizeValidatorForArraysOfInt;
import com.gwtent.validate.client.constraints.impl.SizeValidatorForArraysOfLong;
import com.gwtent.validate.client.constraints.impl.SizeValidatorForCollection;
import com.gwtent.validate.client.constraints.impl.SizeValidatorForMap;
import com.gwtent.validate.client.constraints.impl.SizeValidatorForString;


/**
 * Keeps track of builtin constraints and their validator implementations, as well as already resolved validator definitions.
 *
 * @author Hardy Ferentschik
 * @author Alaa Nassef
 * 
 * @author James Luo addapted to GWT
 */
public class ConstraintHelper {

	private final HashMap<Class<? extends Annotation>, List<Class<? extends ConstraintValidator<?, ?>>>> builtinConstraints =
			new HashMap<Class<? extends Annotation>, List<Class<? extends ConstraintValidator<?, ?>>>>();
	
	private final HashMap<Class<? extends Annotation>, List<Class<? extends ConstraintValidator<? extends Annotation, ?>>>> constraintValidatorDefinitons =
			new HashMap<Class<? extends Annotation>, List<Class<? extends ConstraintValidator<? extends Annotation, ?>>>>();
	
	protected HashMap<Class<? extends Annotation>, List<Class<? extends ConstraintValidator<?, ?>>>> getBuiltinConstraints(){
		return builtinConstraints;
	}

	public ConstraintHelper() {
		List<Class<? extends ConstraintValidator<?, ?>>> constraintList =
				new ArrayList<Class<? extends ConstraintValidator<?, ?>>>();
		constraintList.add( AssertFalseValidator.class );
		builtinConstraints.put( AssertFalse.class, constraintList );

		constraintList = new ArrayList<Class<? extends ConstraintValidator<?, ?>>>();
		constraintList.add( AssertTrueValidator.class );
		builtinConstraints.put( AssertTrue.class, constraintList );

		constraintList = new ArrayList<Class<? extends ConstraintValidator<?, ?>>>();
		constraintList.add( DecimalMaxValidatorForNumber.class );
		constraintList.add( DecimalMaxValidatorForString.class );
		builtinConstraints.put( DecimalMax.class, constraintList );

		constraintList = new ArrayList<Class<? extends ConstraintValidator<?, ?>>>();
		constraintList.add( DecimalMinValidatorForNumber.class );
		constraintList.add( DecimalMinValidatorForString.class );
		builtinConstraints.put( DecimalMin.class, constraintList );

		constraintList = new ArrayList<Class<? extends ConstraintValidator<?, ?>>>();
		constraintList.add( DigitsValidatorForString.class );
		constraintList.add( DigitsValidatorForNumber.class );
		builtinConstraints.put( Digits.class, constraintList );

		constraintList = new ArrayList<Class<? extends ConstraintValidator<?, ?>>>();
		constraintList.add( FutureValidatorForDate.class );
		builtinConstraints.put( Future.class, constraintList );

		constraintList = new ArrayList<Class<? extends ConstraintValidator<?, ?>>>();
		constraintList.add( MaxValidatorForNumber.class );
		constraintList.add( MaxValidatorForString.class );
		builtinConstraints.put( Max.class, constraintList );

		constraintList = new ArrayList<Class<? extends ConstraintValidator<?, ?>>>();
		constraintList.add( MinValidatorForNumber.class );
		constraintList.add( MinValidatorForString.class );
		builtinConstraints.put( Min.class, constraintList );

		constraintList = new ArrayList<Class<? extends ConstraintValidator<?, ?>>>();
		constraintList.add( NotNullValidator.class );
		builtinConstraints.put( NotNull.class, constraintList );

		constraintList = new ArrayList<Class<? extends ConstraintValidator<?, ?>>>();
		constraintList.add( NullValidator.class );
		builtinConstraints.put( Null.class, constraintList );

		constraintList = new ArrayList<Class<? extends ConstraintValidator<?, ?>>>();
		constraintList.add( PastValidatorForDate.class );
		builtinConstraints.put( Past.class, constraintList );

		constraintList = new ArrayList<Class<? extends ConstraintValidator<?, ?>>>();
		constraintList.add( PatternValidator.class );
		builtinConstraints.put( Pattern.class, constraintList );

		constraintList = new ArrayList<Class<? extends ConstraintValidator<?, ?>>>();
		constraintList.add( SizeValidatorForString.class );
		constraintList.add( SizeValidatorForCollection.class );
		constraintList.add( SizeValidatorForArray.class );
		constraintList.add( SizeValidatorForMap.class );
		constraintList.add( SizeValidatorForArraysOfBoolean.class );
		constraintList.add( SizeValidatorForArraysOfByte.class );
		constraintList.add( SizeValidatorForArraysOfChar.class );
		constraintList.add( SizeValidatorForArraysOfDouble.class );
		constraintList.add( SizeValidatorForArraysOfFloat.class );
		constraintList.add( SizeValidatorForArraysOfInt.class );
		constraintList.add( SizeValidatorForArraysOfLong.class );
		builtinConstraints.put( Size.class, constraintList );
	}

	public List<Class<? extends ConstraintValidator<? extends Annotation, ?>>> getBuiltInConstraints(Class<? extends Annotation> annotationClass) {
		final List<Class<? extends ConstraintValidator<?, ?>>> builtInList = builtinConstraints.get( annotationClass );

		if ( builtInList == null || builtInList.size() == 0 ) {
			throw new ValidationException( "Unable to find constraints for  " + annotationClass );
		}

		List<Class<? extends ConstraintValidator<? extends Annotation, ?>>> constraints =
				new ArrayList<Class<? extends ConstraintValidator<? extends Annotation, ?>>>( builtInList.size() );
		for ( Class<? extends ConstraintValidator<?, ?>> validatorClass : builtInList ) {
			//safe cause all CV for a given annotation A are CV<A, ?>
			@SuppressWarnings("unchecked")
			Class<ConstraintValidator<? extends Annotation, ?>> safeValdiatorClass = ( Class<ConstraintValidator<? extends Annotation, ?>> ) validatorClass;
			constraints.add( safeValdiatorClass );
		}

		return constraints;
	}

	public boolean isBuiltinConstraint(Class<? extends Annotation> annotationType) {
		return builtinConstraints.containsKey( annotationType );
	}

	/**
	 * Checks whether a given annotation is a multi value constraint or not.
	 *
	 * @param annotation the annotation to check.
	 *
	 * @return <code>true</code> if the specified annotation is a multi value constraints, <code>false</code>
	 *         otherwise.
	 */
	public boolean isMultiValueConstraint(Annotation annotation) {
		boolean isMultiValueConstraint = false;
//		try {
//			final GetMethod getMethod = GetMethod.action( annotation.getClass(), "value" );
//			final Method method;
//			if ( System.getSecurityManager() != null ) {
//				method = AccessController.doPrivileged( getMethod );
//			}
//			else {
//				method = getMethod.run();
//			}
//			if (method != null) {
//				Class returnType = method.getReturnType();
//				if ( returnType.isArray() && returnType.getComponentType().isAnnotation() ) {
//					Annotation[] annotations = ( Annotation[] ) method.invoke( annotation );
//					for ( Annotation a : annotations ) {
//						if ( isConstraintAnnotation( a ) || isBuiltinConstraint( a.annotationType() ) ) {
//							isMultiValueConstraint = true;
//						}
//						else {
//							isMultiValueConstraint = false;
//							break;
//						}
//					}
//				}
//			}
//		}
//		catch ( IllegalAccessException iae ) {
//			// ignore
//		}
//		catch ( InvocationTargetException ite ) {
//			// ignore
//		}
		return isMultiValueConstraint;
	}


	/**
	 * Checks whether a given annotation is a multi value constraint and returns the contained constraints if so.
	 *
	 * @param annotation the annotation to check.
	 *
	 * @return A list of constraint annotations or the empty list if <code>annotation</code> is not a multi constraint
	 *         annotation.
	 */
	public <A extends Annotation> List<Annotation> getMultiValueConstraints(A annotation) {
		List<Annotation> annotationList = new ArrayList<Annotation>();
//		try {
//			final GetMethod getMethod = GetMethod.action( annotation.getClass(), "value" );
//			final Method method;
//			if ( System.getSecurityManager() != null ) {
//				method = AccessController.doPrivileged( getMethod );
//			}
//			else {
//				method = getMethod.run();
//			}
//			if (method != null) {
//				Class returnType = method.getReturnType();
//				if ( returnType.isArray() && returnType.getComponentType().isAnnotation() ) {
//					Annotation[] annotations = ( Annotation[] ) method.invoke( annotation );
//					for ( Annotation a : annotations ) {
//						if ( isConstraintAnnotation( a ) || isBuiltinConstraint( a.annotationType() ) ) {
//							annotationList.add( a );
//						}
//					}
//				}
//			}
//		}
//		catch ( IllegalAccessException iae ) {
//			// ignore
//		}
//		catch ( InvocationTargetException ite ) {
//			// ignore
//		}
		return annotationList;
	}

	/**
	 * Checks whether the specified annotation is a valid constraint annotation. A constraint annotations has to
	 * fulfill the following conditions:
	 * <ul>
	 * <li>Has to contain a <code>ConstraintValidator</code> implementation.</li>
	 * <li>Defines a message parameter.</li>
	 * <li>Defines a group parameter.</li>
	 * <li>Defines a payload parameter.</li>
	 * </ul>
	 *
	 * @param annotation The annotation to test.
	 *
	 * @return <code>true</code> if the annotation fulfills the above condtions, <code>false</code> otherwise.
	 */
	public boolean isConstraintAnnotation(Annotation annotation) {

		//TODO implements this
//		Constraint constraint = annotation.annotationType()
//				.getAnnotation( Constraint.class );
//		if ( constraint == null ) {
//			return false;
//		}
//
//		assertMessageParameterExists( annotation );
//		assertGroupsParameterExists( annotation );
//		assertPayloadParameterExists( annotation );
//
//		assertNoParameterStartsWithValid( annotation );

		return true;
	}

	private void assertNoParameterStartsWithValid(Annotation annotation) {
//		final Method[] methods;
//		final GetMethods getMethods = GetMethods.action( annotation.annotationType() );
//		if ( System.getSecurityManager() != null ) {
//			methods = AccessController.doPrivileged( getMethods );
//		}
//		else {
//			methods = getMethods.run();
//		}
//		for ( Method m : methods ) {
//			if ( m.getName().startsWith( "valid" ) ) {
//				String msg = "Parameters starting with 'valid' are not allowed in a constraint.";
//				throw new ConstraintDefinitionException( msg );
//			}
//		}
	}

	private void assertPayloadParameterExists(Annotation annotation) {
//		try {
//			final GetMethod getMethod = GetMethod.action( annotation.annotationType(), "payload" );
//			final Method method;
//			if ( System.getSecurityManager() != null ) {
//				method = AccessController.doPrivileged( getMethod );
//			}
//			else {
//				method = getMethod.run();
//			}
//			if (method == null) {
//				String msg = annotation.annotationType().getName() + " contains Constraint annotation, but does " +
//					"not contain a payload parameter.";
//				throw new ConstraintDefinitionException( msg );
//			}
//			Class<?>[] defaultPayload = ( Class<?>[] ) method.getDefaultValue();
//			if ( defaultPayload.length != 0 ) {
//				String msg = annotation.annotationType()
//						.getName() + " contains Constraint annotation, but the payload " +
//						"paramter default value is not the empty array.";
//				throw new ConstraintDefinitionException( msg );
//			}
//		}
//		catch ( ClassCastException e ) {
//			String msg = annotation.annotationType().getName() + " contains Constraint annotation, but the " +
//					"payload parameter is of wrong type.";
//			throw new ConstraintDefinitionException( msg );
//		}
	}

	private void assertGroupsParameterExists(Annotation annotation) {
//		try {
//			//final GetMethod getMethod = GetMethod.action( annotation.annotationType(), "groups" );
//			final GetMethod getMethod = GetMethod.action( annotation.annotationType(), "groups" );
//			final Method method;
//			if ( System.getSecurityManager() != null ) {
//				method = AccessController.doPrivileged( getMethod );
//			}
//			else {
//				method = getMethod.run();
//			}
//			if (method == null) {
//				String msg = annotation.annotationType().getName() + " contains Constraint annotation, but does " +
//					"not contain a groups parameter.";
//				throw new ConstraintDefinitionException( msg );
//			}
//			Class<?>[] defaultGroups = ( Class<?>[] ) method.getDefaultValue();
//			if ( defaultGroups.length != 0 ) {
//				String msg = annotation.annotationType()
//						.getName() + " contains Constraint annotation, but the groups " +
//						"paramter default value is not the empty array.";
//				throw new ConstraintDefinitionException( msg );
//			}
//		}
//		catch ( ClassCastException e ) {
//			String msg = annotation.annotationType().getName() + " contains Constraint annotation, but the " +
//					"groups parameter is of wrong type.";
//			throw new ConstraintDefinitionException( msg );
//		}
	}

	private void assertMessageParameterExists(Annotation annotation) {
//		try {
//			GetAnnotationParameter<?> action = GetAnnotationParameter.action( annotation, "message", String.class );
//			if (System.getSecurityManager() != null) {
//				AccessController.doPrivileged( action );
//			}
//			else {
//				action.run();
//			}
//		}
//		catch ( Exception e ) {
//			String msg = annotation.annotationType().getName() + " contains Constraint annotation, but does " +
//					"not contain a message parameter.";
//			throw new ConstraintDefinitionException( msg );
//		}
	}

	public <T extends Annotation> List<Class<? extends ConstraintValidator<T, ?>>> getConstraintValidatorDefinition(Class<T> annotationClass) {
		if ( annotationClass == null ) {
			throw new IllegalArgumentException( "Class cannot be null" );
		}

		final List<Class<? extends ConstraintValidator<? extends Annotation, ?>>> list = constraintValidatorDefinitons.get(
				annotationClass
		);

		List<Class<? extends ConstraintValidator<T, ?>>> constraintsValidators =
				new ArrayList<Class<? extends ConstraintValidator<T, ?>>>( list.size() );
		for ( Class<? extends ConstraintValidator<?, ?>> validatorClass : list ) {
			//safe cause all CV for a given annotation A are CV<A, ?>
			@SuppressWarnings("unchecked")
			Class<ConstraintValidator<T, ?>> safeValdiatorClass = ( Class<ConstraintValidator<T, ?>> ) validatorClass;
			constraintsValidators.add( safeValdiatorClass );
		}

		return constraintsValidators;
	}

	public <A extends Annotation> void addConstraintValidatorDefinition(Class<A> annotationClass, List<Class<? extends ConstraintValidator<? extends Annotation, ?>>> definitionClasses) {
		constraintValidatorDefinitons.put( annotationClass, definitionClasses );
	}

	public boolean containsConstraintValidatorDefinition(Class<? extends Annotation> annotationClass) {
		return constraintValidatorDefinitons.containsKey( annotationClass );
	}
}
