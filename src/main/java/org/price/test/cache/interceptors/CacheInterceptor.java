package org.price.test.cache.interceptors;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.price.test.cache.annotations.Cached;
import org.price.test.cache.annotations.RemoveCache;
import org.price.test.cache.annotations.RemoveCaches;

import com.google.appengine.api.memcache.ErrorHandlers;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;

public class CacheInterceptor implements MethodInterceptor {

    private static final Logger LOG       = Logger.getLogger(CacheInterceptor.class.getName());
    private MemcacheService     syncCache = MemcacheServiceFactory.getMemcacheService();

    public CacheInterceptor() {
        syncCache.setErrorHandler(ErrorHandlers.getConsistentLogAndContinue(Level.INFO));
    }

    @SuppressWarnings("unchecked")
    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        Annotation[] annotations = methodInvocation.getMethod().getAnnotations();
        StringBuilder keyBuilder = new StringBuilder();
        LOG.info("Detecting caches ...");

        for (Annotation a : annotations) {
            if (a.annotationType().equals(Cached.class)) {
                Cached cached = (Cached) a;
                LOG.info("Cached method found: " + methodInvocation.getMethod());
                Map<String, Object> subKeys = (Map<String, Object>) syncCache.get(cached.key());
                if (subKeys == null) {
                    subKeys = new HashMap<>();
                }

                for (Object arg : methodInvocation.getArguments()) {
                    keyBuilder.append(arg).append('.');
                }

                Object ret = subKeys.get(keyBuilder.toString());
                if (ret == null) {
                    ret = methodInvocation.proceed();
                    subKeys.put(keyBuilder.toString(), ret);
                }

                syncCache.put(cached.key(), subKeys);
                return ret;
            }
            else if (a.annotationType().equals(RemoveCache.class)) {
                RemoveCache cache = (RemoveCache) a;
                LOG.info("Remove cache method found: " + methodInvocation.getMethod());
                syncCache.delete(cache.key());
                return methodInvocation.proceed();
            }
            else if (a.annotationType().equals(RemoveCaches.class)) {
                RemoveCaches cache = (RemoveCaches) a;
                LOG.info("Remove multiple cache method found: " + methodInvocation.getMethod());
                for (String key : cache.keys()) {
                    syncCache.delete(key);
                }

                return methodInvocation.proceed();
            }

        }

        return methodInvocation.proceed();

    }
}
