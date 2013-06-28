package jee.reference.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InstanceBuilder {
    private static final Logger LOGGER = LoggerFactory.getLogger(InstanceBuilder.class);

    public static <T> T build(Class<T> clazz) throws InstantiationException, IllegalAccessException {
        T newInstance = clazz.newInstance();
        return build(newInstance, clazz, 3, null, new HashSet<Class<?>>());
    }

    public static <T> T build(Class<T> clazz, Package packageFilter) throws InstantiationException, IllegalAccessException {
        T newInstance = clazz.newInstance();
        return build(newInstance, clazz, 3, packageFilter, new HashSet<Class<?>>());
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    private static <T> T build(T instance, Class evaluatedClass, int level, Package packageFilter, Collection skippedTypes) {
        Class superClassOfEvaluatedClass = evaluatedClass.getSuperclass();

        fillFieldsFromSuperclass(instance, level, packageFilter, skippedTypes, superClassOfEvaluatedClass);

        for (Field field : evaluatedClass.getDeclaredFields()) {
            Class fieldClass = field.getType();

            if (skippedTypes.contains(field.getType()) || Modifier.isFinal(field.getModifiers())) {
                continue;
            }

            field.setAccessible(true);

            Object fieldInstance;
            try {
                if (fieldClass.isPrimitive()) {
                    if (boolean.class.equals(fieldClass)) {
                        boolean value = false;
                        fieldInstance = value;
                        field.set(instance, fieldInstance);
                    } else if (byte.class.equals(fieldClass)) {
                        byte value = 8;
                        fieldInstance = value;
                        field.set(instance, fieldInstance);
                    } else if (int.class.equals(fieldClass)) {
                        int value = 1;
                        fieldInstance = value;
                        field.set(instance, fieldInstance);
                    } else if (long.class.equals(fieldClass)) {
                        long value = 10;
                        fieldInstance = value;
                        field.set(instance, fieldInstance);
                    } else if (float.class.equals(fieldClass)) {
                        float value = 10.2F;
                        fieldInstance = value;
                        field.set(instance, fieldInstance);
                    } else if (double.class.equals(fieldClass)) {
                        double value = 123.45;
                        fieldInstance = value;
                        field.set(instance, fieldInstance);
                    } else if (char.class.equals(fieldClass)) {
                        char value = '-';
                        fieldInstance = value;
                        field.set(instance, fieldInstance);
                    }
                } else {
                    if (java.lang.Boolean.class.equals(fieldClass)) {
                        fieldInstance = new java.lang.Boolean(false);
                    } else if (java.lang.Byte.class.equals(fieldClass)) {
                        fieldInstance = new java.lang.Byte("88");
                    } else if (java.lang.Integer.class.equals(fieldClass)) {
                        fieldInstance = new java.lang.Integer(91);
                    } else if (java.lang.Long.class.equals(fieldClass)) {
                        fieldInstance = new java.lang.Long(910);
                    } else if (java.lang.Float.class.equals(fieldClass)) {
                        fieldInstance = new java.lang.Float(910.2);
                    } else if (java.lang.Double.class.equals(fieldClass)) {
                        fieldInstance = new java.lang.Double(9123.45);
                    } else if (java.math.BigDecimal.class.equals(fieldClass)) {
                        fieldInstance = new java.math.BigDecimal(91234.567);
                    } else if (java.util.Date.class.equals(fieldClass)) {
                        fieldInstance = new java.util.Date();
                    } else if (java.lang.String.class.equals(fieldClass)) {
                        fieldInstance = new java.lang.String("STRING");
                    } else if (java.lang.Character.class.equals(fieldClass)) {
                        fieldInstance = new java.lang.Character('=');
                    } else if (java.util.List.class.equals(fieldClass)) {
                        fieldInstance = buildList(evaluatedClass, level, packageFilter, field);
                    } else if (java.util.Set.class.equals(fieldClass)) {
                        fieldInstance = buildSet(evaluatedClass, level, packageFilter, field);
                    } else {
                        try {
                            Object[] enumConstants = fieldClass.getEnumConstants();
                            if (enumConstants == null) {
                                fieldInstance = fieldClass.newInstance();
                            } else {
                                fieldInstance = enumConstants[0];
                            }
                        } catch (InstantiationException e) {
                            fieldInstance = null;
                        }
                    }

                    field.set(instance, fieldInstance);

                    buildFieldsForFieldInstance(evaluatedClass, level, packageFilter, fieldClass, fieldInstance);
                }
            } catch (Exception e) {
                LOGGER.error("Exception occured during instance creation, class = " + evaluatedClass.getCanonicalName() + ", field = " + field.getName(), e);
            }
        }

        return instance;
    }

    @SuppressWarnings("rawtypes")
    private static Object buildSet(Class evaluatedClass, int level, Package packageFilter, Field field) throws InstantiationException, IllegalAccessException {
        Object fieldInstance;
        Set<Object> set = new HashSet<Object>();

        buildCollection(evaluatedClass, level, packageFilter, field, set);

        fieldInstance = set;
        return fieldInstance;
    }

    @SuppressWarnings("rawtypes")
    private static Object buildList(Class evaluatedClass, int level, Package packageFilter, Field field) throws InstantiationException, IllegalAccessException {
        Object fieldInstance;
        List<Object> list = new ArrayList<Object>();

        buildCollection(evaluatedClass, level, packageFilter, field, list);

        fieldInstance = list;
        return fieldInstance;
    }

    @SuppressWarnings("rawtypes")
    private static void buildCollection(Class evaluatedClass, int level, Package packageFilter, Field field, Collection<Object> collection)
            throws InstantiationException, IllegalAccessException {

        Type genericType = field.getGenericType();
        if (genericType instanceof ParameterizedType) {
            Class parameterTypeClass = (Class) ((ParameterizedType) genericType).getActualTypeArguments()[0];
            Object element = parameterTypeClass.newInstance();
            InstanceBuilder.build(element, element.getClass(), level - 1, packageFilter, Collections.singleton(evaluatedClass));
            collection.add(element);
        }
    }

    @SuppressWarnings("rawtypes")
    private static void buildFieldsForFieldInstance(Class evaluatedClass, int level, Package packageFilter, Class fieldClass, Object fieldInstance) {
        if (level > 0) {
            String fieldClassPackageName = fieldClass.getPackage().getName();

            if (packageFilter == null || fieldClassPackageName.startsWith(packageFilter.getName()))
                InstanceBuilder.build(fieldInstance, fieldClass, level - 1, packageFilter, Collections.singleton(evaluatedClass));
        }
    }

    private static <T> void fillFieldsFromSuperclass(T instance, int level, Package packageFilter, Collection<Class<?>> skippedTypes,
            Class<? extends Object> superClassOfEvaluatedClass) {

        if (superClassOfEvaluatedClass != Object.class) {
            String superClassPackageName = superClassOfEvaluatedClass.getPackage().getName();

            if (packageFilter == null || superClassPackageName.startsWith(packageFilter.getName()))
                InstanceBuilder.build(instance, superClassOfEvaluatedClass, level, packageFilter, skippedTypes);
        }
    }
}
