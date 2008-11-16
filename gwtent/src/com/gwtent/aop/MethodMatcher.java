package com.gwtent.aop;

import java.lang.reflect.Method;

public interface MethodMatcher {
	boolean matches(Method method, Class<?> targetClass, Object... args);
}