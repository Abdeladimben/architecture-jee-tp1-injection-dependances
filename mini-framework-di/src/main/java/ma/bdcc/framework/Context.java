package ma.bdcc.framework;

import ma.bdcc.framework.annotation.*;
import ma.bdcc.framework.xml.*;

import java.io.File;
import java.lang.reflect.*;
import java.util.*;

public class Context {
    private Map<String, Object> beans = new HashMap<>();

    public Context(String xmlConfigFile) throws Exception {
        Map<String, BeanDefinition> definitions = XmlParser.parse(xmlConfigFile);
        for (BeanDefinition def : definitions.values()) {
            Class<?> clazz = Class.forName(def.getClassName());
            Object instance = clazz.getDeclaredConstructor().newInstance();
            beans.put(def.getId(), instance);
        }
        for (BeanDefinition def : definitions.values()) {
            if (def.getPropertyRef() != null && !def.getPropertyRef().isEmpty()) {
                Object target = beans.get(def.getId());
                Object dependency = beans.get(def.getPropertyRef());
                String propName = def.getPropertyName();
                String setterName = "set" + Character.toUpperCase(propName.charAt(0)) + propName.substring(1);
                Method setter = target.getClass().getMethod(setterName, dependency.getClass().getInterfaces()[0]);
                setter.invoke(target, dependency);
            }
        }
    }

    public Context(String basePackage, boolean useAnnotations) throws Exception {
        scanPackage(basePackage);
        injectDependencies();
    }

    private void scanPackage(String basePackage) throws Exception {
        String path = basePackage.replace('.', '/');
        String fullPath = Thread.currentThread().getContextClassLoader().getResource(path).getFile();
        File dir = new File(fullPath);
        scanDirectory(dir, basePackage);
    }

    private void scanDirectory(File dir, String packageName) throws Exception {
        for (File file : dir.listFiles()) {
            if (file.isDirectory()) {
                scanDirectory(file, packageName + "." + file.getName());
            } else if (file.getName().endsWith(".class")) {
                String className = packageName + "." + file.getName().replace(".class", "");
                Class<?> clazz = Class.forName(className);
                if (clazz.isAnnotationPresent(Component.class)) {
                    Component component = clazz.getAnnotation(Component.class);
                    String beanName = component.value().isEmpty() ?
                        Character.toLowerCase(clazz.getSimpleName().charAt(0)) + clazz.getSimpleName().substring(1)
                        : component.value();
                    Object instance = clazz.getDeclaredConstructor().newInstance();
                    beans.put(beanName, instance);
                }
            }
        }
    }

    private void injectDependencies() throws Exception {
        for (Object bean : beans.values()) {
            Class<?> clazz = bean.getClass();
            for (Field field : clazz.getDeclaredFields()) {
                if (field.isAnnotationPresent(Autowired.class) || field.isAnnotationPresent(Inject.class)) {
                    field.setAccessible(true);
                    for (Object dep : beans.values()) {
                        if (field.getType().isAssignableFrom(dep.getClass())) {
                            field.set(bean, dep);
                            break;
                        }
                    }
                }
            }
            for (Constructor<?> constructor : clazz.getDeclaredConstructors()) {
                if (constructor.isAnnotationPresent(Autowired.class)) {
                    Class<?>[] paramTypes = constructor.getParameterTypes();
                    Object[] params = new Object[paramTypes.length];
                    for (int i = 0; i < paramTypes.length; i++) {
                        for (Object dep : beans.values()) {
                            if (paramTypes[i].isAssignableFrom(dep.getClass())) {
                                params[i] = dep;
                                break;
                            }
                        }
                    }
                }
            }
            for (Method method : clazz.getDeclaredMethods()) {
                if (method.isAnnotationPresent(Autowired.class)) {
                    Class<?>[] paramTypes = method.getParameterTypes();
                    Object[] params = new Object[paramTypes.length];
                    for (int i = 0; i < paramTypes.length; i++) {
                        for (Object dep : beans.values()) {
                            if (paramTypes[i].isAssignableFrom(dep.getClass())) {
                                params[i] = dep;
                                break;
                            }
                        }
                    }
                    method.invoke(bean, params);
                }
            }
        }
    }

    public <T> T getBean(Class<T> clazz) {
        for (Object bean : beans.values()) {
            if (clazz.isAssignableFrom(bean.getClass())) {
                return (T) bean;
            }
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public <T> T getBean(String name) {
        return (T) beans.get(name);
    }
}
