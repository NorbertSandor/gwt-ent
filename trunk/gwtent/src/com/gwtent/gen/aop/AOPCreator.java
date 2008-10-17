/*
 * Copyright 2007 JamesLuo.au@gmail.com
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.gwtent.gen.aop;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.ext.BadPropertyValueException;
import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.PropertyOracle;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.AnnotationsHelper;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.JField;
import com.google.gwt.core.ext.typeinfo.JMethod;
import com.google.gwt.core.ext.typeinfo.JParameter;
import com.google.gwt.core.ext.typeinfo.JPrimitiveType;
import com.google.gwt.core.ext.typeinfo.JRealClassType;
import com.google.gwt.core.ext.typeinfo.JType;
import com.google.gwt.core.ext.typeinfo.NotFoundException;
import com.google.gwt.user.rebind.ClassSourceFileComposerFactory;
import com.google.gwt.user.rebind.SourceWriter;

import com.gwtent.aop.BindRegistry;
import com.gwtent.aop.matcher.MethodMatcher;
import com.gwtent.client.aop.AOPRegistor;
import com.gwtent.client.aop.AOPRegistor.MethodAspect;
import com.gwtent.client.aop.intercept.Interceptor;
import com.gwtent.client.aop.intercept.MethodInterceptor;
import com.gwtent.client.reflection.PrimitiveTypeImpl;
import com.gwtent.client.reflection.Reflection;
import com.gwtent.client.reflection.impl.ClassTypeImpl;
import com.gwtent.client.reflection.impl.TypeImpl;
import com.gwtent.client.test.TestReflection;
import com.gwtent.client.test.annotations.Entity;
import com.gwtent.gen.LogableSourceCreator;
import com.gwtent.aop.*;

import java.io.PrintWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class AOPCreator extends LogableSourceCreator {

	private final boolean isUseLog = true;
	
	static final String SUFFIX = "__AOP";

	public AOPCreator(TreeLogger logger, GeneratorContext context,
			String typeName) {
		super(logger, context, typeName);
		
		AsyncRegistor();
		
//		PropertyOracle propertyOracle = context.getPropertyOracle();
//		try {
//			String matcher = propertyOracle.getPropertyValue(logger, "aop.matcher.test");
//			System.out.println(matcher);
//		} catch (BadPropertyValueException e) {
//			e.printStackTrace();
//		}
	}

	protected void createSource(SourceWriter source, JClassType classType){
		source.println("public " + getSimpleUnitName(classType) + "(){");
		source.indent();
		source.println("makeSureCreateClassType();");
		//source.println("addAnnotations();");
		//source.println("addFields();");
		//source.println("addMethods();");
		source.outdent();
		source.println("}");

		String reflectionClassName = getSimpleUnitName(classType) + "_";
		source.println("public static class " + reflectionClassName + " extends " + classType.getSimpleSourceName() + " implements Reflection {");
		source.println("}");
		
		source.println("public void makeSureCreateClassType() {");
		source.indent();
		//source.println("ClassType type = (ClassType)GWT.create(TestReflection.class);");
		source.println("ClassType type = (ClassType)GWT.create(" + reflectionClassName + ".class);");
		source.outdent();
		source.println("}");
		
		//processMethods(source, classType);
	}
	
	private void processMethods(SourceWriter source, JClassType classType){
		MatcherQuery query = BindRegistry.getInstance();
		Class<?> classz = null;
		try {
			classz = Class.forName(classType.getQualifiedSourceName());
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
		if ((classz != null) & (query.matches(classz))){
			Method[] methods = classz.getMethods();
			for (int i = 0; i < methods.length; i++) {
				Method method = methods[i];
				
				List<MethodInterceptor> interceptors = query.matches(method);
				if (interceptors.size() > 0){
					
				}
			}
		}
	}

	/**
	 * SourceWriter instantiation. Return null if the resource already exist.
	 * 
	 * @return sourceWriter
	 */
	public SourceWriter doGetSourceWriter(JClassType classType) {
		String packageName = classType.getPackage().getName();
		String simpleName = getSimpleUnitName(classType);
		ClassSourceFileComposerFactory composer = new ClassSourceFileComposerFactory(
				packageName, simpleName);
		composer.setSuperclass(classType.getQualifiedSourceName());
		// composer.addImplementedInterface(
		// "com.coceler.gwt.client.reflection.Class");
		composer.addImport(classType.getQualifiedSourceName());
		composer.addImport("com.google.gwt.core.client.*");
		composer.addImport("com.gwtent.client.reflection.*");
		composer.addImport("java.util.*");
		composer.addImport(classType.getPackage().getName() + ".*");

		PrintWriter printWriter = context.tryCreate(logger, packageName,
				simpleName);
		if (printWriter == null) {
			return null;
		} else {
			SourceWriter sw = composer.createSourceWriter(context, printWriter);
			return sw;
		}
		
	}

	@Override
	protected String getSUFFIX() {
		return SUFFIX;
	}
	
	/**
	 * We just Async if nothing in BindRegistry.
	 * We supposed everything should be there when Code Generator started
	 */
	protected void AsyncRegistor(){
		if (AOPRegistor.getInstance().size() > 0){
			if (BindRegistry.getInstance().size() == 0){
				for (int i = 0; i < AOPRegistor.getInstance().size(); i++){
					MethodAspect aspect = AOPRegistor.getInstance().getMethodAspects().get(i);
					try {
						Class<MethodMatcher> classz = (Class<MethodMatcher>) Class.forName(aspect.getMethodMatcherClassName());
						try {
							MethodMatcher matcher = classz.getConstructor(null).newInstance(null);
							BindRegistry.getInstance().bindInterceptor(matcher.getClassMatcher(), matcher.getMethodMatcher(), aspect.getInterceptors());
						} catch (Exception e) {
							throw new RuntimeException(e);
						}
					} catch (ClassNotFoundException e) {
						throw new RuntimeException(e);
					}
				}
			}
		}
	}
	

}
