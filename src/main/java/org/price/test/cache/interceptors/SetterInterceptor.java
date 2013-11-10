package org.price.test.cache.interceptors;

import java.lang.reflect.Method;
import java.util.Arrays;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import org.price.test.EntitySupport;

public class SetterInterceptor implements MethodInterceptor {

	@Override
	public Object intercept(Object obj, Method method, Object[] args,
			MethodProxy proxy) throws Throwable {
		System.out.println(method + " " + Arrays.asList(args) + " " + proxy);
		if (method.equals(EntitySupport.class.getMethod("getEntity"))) {
			return null;
		}

		return proxy.invokeSuper(obj, args);
	}

}
