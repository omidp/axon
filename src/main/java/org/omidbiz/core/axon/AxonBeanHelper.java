package org.omidbiz.core.axon;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;

public class AxonBeanHelper
{

    private static final Class<?>[] PRIMITIVE_TYPES = { int.class, long.class, short.class, float.class, double.class, byte.class,
            boolean.class, char.class };

    private static final Class<?>[] WRAPPER_TYPES = { Integer.class, Long.class, Short.class, Float.class, Double.class, Byte.class,
            Boolean.class, Character.class, String.class };

    public static boolean isPrimitive(Class<?> clazz)
    {
        if (clazz == null)
            throw new RuntimeException("null value");
        for (int i = 0; i < PRIMITIVE_TYPES.length; i++)
        {
            if (clazz == PRIMITIVE_TYPES[i])
                return true;
        }
        return false;
    }

    public static boolean isWrapper(Class<?> clazz)
    {
        if (clazz == null)
            throw new RuntimeException("null value");
        for (int i = 0; i < WRAPPER_TYPES.length; i++)
        {
            if (clazz == WRAPPER_TYPES[i])
                return true;
        }
        return false;
    }

    public static boolean isPrimitive(Object obj)
    {
        if (obj == null)
            throw new RuntimeException("null value");
        return isPrimitive(obj.getClass());
    }

    public static boolean isWrapper(Object obj)
    {
        if (obj == null)
            throw new RuntimeException("null value");
        return isWrapper(obj.getClass());
    }

    public static boolean isPrimitiveOrWrapper(Class<?> clazz)
    {
        return isPrimitive(clazz) || isWrapper(clazz);
    }

    public static boolean isPrimitiveOrWrapper(Object obj)
    {
        if (obj == null)
            throw new RuntimeException("null value");
        return isPrimitiveOrWrapper(obj.getClass());
    }

    public static boolean isArrayOrCollection(Class<?> clazz)
    {
        if (clazz == null)
            throw new RuntimeException("null value");
        return clazz.isArray() || isSubclass(clazz, Collection.class);
    }

    public static boolean isArrayOrCollection(Object obj)
    {
        if (obj == null)
            throw new RuntimeException("null value");
        return isArrayOrCollection(obj.getClass());
    }

    public static boolean isMap(Class<?> clazz)
    {
        if (clazz == null)
            throw new RuntimeException("null value");
        return isSubclass(clazz, Map.class);
    }

    public static boolean isMap(Object obj)
    {
        if (obj == null)
            throw new RuntimeException("null value");
        return isMap(obj.getClass());
    }

    public static boolean isEnum(Class<?> clazz)
    {
        if (clazz == null)
            throw new RuntimeException("null value");
        return clazz.isEnum();
    }

    public static boolean isEnum(Object obj)
    {
        if (obj == null)
            throw new RuntimeException("null value");
        return isEnum(obj.getClass());
    }

    /**
     * for example when class1 is EpochDateConverter which implements
     * TypeConverter<Date>, and class2 is TypeConverter, the return value is
     * Date.class
     * 
     * @param class1
     * @param class2
     * @return
     */
    @SuppressWarnings("restriction")
    public static Type getGenericSuperType(Class<?> class1, Class<?> class2)
    {
        if (class1 == null || class2 == null)
            throw new RuntimeException("null value");
        List<Type> genericSupers = new ArrayList<Type>();
        genericSupers.add(class1.getGenericSuperclass());
        genericSupers.addAll(newArrayList(class1.getGenericInterfaces()));

        for (Type type : genericSupers)
        {
            if (type instanceof ParameterizedTypeImpl)
            {
                ParameterizedTypeImpl a = (ParameterizedTypeImpl) type;
                if (a.getRawType() == class2)
                    return a.getActualTypeArguments()[0];
            }
        }

        return null;
    }

    public static Class<?> getGenericFieldClassType(Class<?> clz, String propertyName) throws NoSuchFieldException, SecurityException
    {
        Field field = clz.getDeclaredField(propertyName);
        field.setAccessible(true);
        ParameterizedType parameterizedType = (ParameterizedType) field.getGenericType();
        Class<?> genericClass = (Class<?>) parameterizedType.getActualTypeArguments()[0];
        return genericClass;
    }

    public static List<Field> getAllFields(Class<?> clazz, boolean includeParents)
    {

        List<Field> fieldList = newArrayList(clazz.getDeclaredFields());
        if (includeParents)
        {
            List<Class<?>> superClasses = getAllSuperclasses(clazz);
            for (Class<?> superClazz : superClasses)
            {
                if (superClazz != Object.class)
                    fieldList.addAll(getAllFields(superClazz, false));
            }
        }
        return fieldList;
    }

    public static List<Class<?>> getAllSuperclasses(Class<?> cls)
    {
        if (cls == null)
        {
            return null;
        }
        List<Class<?>> classes = new ArrayList<Class<?>>();
        Class<?> superclass = cls.getSuperclass();
        while (superclass != null)
        {
            classes.add(superclass);
            superclass = superclass.getSuperclass();
        }
        return classes;
    }

    /**
     * returns all methods of a class (including parent in case includeParents
     * is true)
     * 
     * @param clazz
     * @param includeParents
     * @return
     */
    public static List<Method> getAllMethods(Class<?> clazz, boolean includeParents)
    {

        List<Method> methodList = newArrayList(clazz.getDeclaredMethods());
        if (includeParents)
        {
            List<Class<?>> superClasses = getAllSuperclasses(clazz);
            for (Class<?> superClazz : superClasses)
            {
                if (superClazz != Object.class)
                    methodList.addAll(getAllMethods(superClazz, false));
            }
        }
        return methodList;
    }

    public static List<Class<?>> getAllInterfaces(Class<?> cls)
    {
        if (cls == null)
        {
            return null;
        }

        LinkedHashSet<Class<?>> interfacesFound = new LinkedHashSet<Class<?>>();
        getAllInterfaces(cls, interfacesFound);

        return new ArrayList<Class<?>>(interfacesFound);
    }

    private static void getAllInterfaces(Class<?> cls, HashSet<Class<?>> interfacesFound)
    {
        while (cls != null)
        {
            Class<?>[] interfaces = cls.getInterfaces();

            for (Class<?> i : interfaces)
            {
                if (interfacesFound.add(i))
                {
                    getAllInterfaces(i, interfacesFound);
                }
            }

            cls = cls.getSuperclass();
        }
    }

    /**
     * Checks whether class1 is a subclass of class2
     * 
     * @param class1
     * @param class2
     * @return
     */
    public static boolean isSubclass(Class<?> class1, Class<?> class2)
    {
        List<Class<?>> superClasses = getAllSuperclasses(class1);
        List<Class<?>> superInterfaces = getAllInterfaces(class1);
        for (Class<?> c : superClasses)
        {
            if (class2 == c)
                return true;
        }
        for (Class<?> c : superInterfaces)
        {
            if (class2 == c)
                return true;
        }
        return false;
    }

    public static List<Property> getProperties(Object bean, boolean includeParents)
    {
        List<Property> result = new ArrayList<Property>();
        Method[] declaredMethods = bean.getClass().getDeclaredMethods();
        if (declaredMethods != null)
        {
            for (Method method : declaredMethods)
            {
                String methodName = method.getName();
                if (methodName.startsWith("get"))
                {
                    String propName = WordUtils.uncapitalize(methodName.substring("get".length(), methodName.length()));
                    // TODO : descriptor.getWriteMethod() instead of null
                    result.add(new Property(propName, method, null));
                }
            }
        }
        return result;

    }

    public static Property getProperty(Object bean, boolean searchParents, String name)
    {
        List<Property> props = getProperties(bean, searchParents);
        for (Property property : props)
        {
            if (property.getName().equals(name))
                return property;
        }
        return null;
    }

    public static Object getPropertyValue(Object target, Property p)
    {
        if (target == null)
            throw new RuntimeException("null target");

        if (p == null)
            throw new RuntimeException("null property");

        Class<?> targetClass = target.getClass();
        Class<?> propertyClass = p.getGetter().getDeclaringClass();
        if (propertyClass != targetClass && !isSubclass(targetClass, propertyClass))
            throw new RuntimeException("incompatible property");

        boolean isAccessible = p.getGetter().isAccessible();
        p.getGetter().setAccessible(true);
        try
        {
            Object value = p.getGetter().invoke(target);
            return value;
        }
        catch (IllegalAccessException e)
        {
            throw new RuntimeException(e);
        }
        catch (IllegalArgumentException e)
        {
            throw new RuntimeException(e);
        }
        catch (InvocationTargetException e)
        {
            throw new RuntimeException(e);
        }
        finally
        {
            p.getGetter().setAccessible(isAccessible);
        }
    }

    public static void setPropertyValue(Object target, Property p, Object value)
    {
        if (target == null)
            throw new RuntimeException("null target");

        if (p == null)
            throw new RuntimeException("null property");

        try
        {
            Field declaredField = target.getClass().getDeclaredField(p.getName());
            declaredField.setAccessible(true);
            declaredField.set(target, value);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    public static Collection<?> instantiateCollection(Property p)
    {
        Class<?> t = p.getType();

        if (t == Set.class)
        {
            return new HashSet<Object>();
        }
        else if (t == List.class)
        {
            return new ArrayList<Object>();
        }
        else if (t == Map.class)
        {
            throw new RuntimeException("can not instantiate map");
        }
        else if (t == Vector.class)
        {
            throw new RuntimeException("can not instantiate vector");
        }
        else
            throw new RuntimeException("unknown type");
    }

    public static <E> ArrayList<E> newArrayList(E... elements)
    {
        ArrayList<E> list = new ArrayList<E>(elements.length);
        Collections.addAll(list, elements);
        return list;
    }

}
