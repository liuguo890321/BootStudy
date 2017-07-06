package com.asiainfo.util;

import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by lenovo on 2017/5/26.
 */
public class ReflectionUtil {

    private static final Logger logger = LoggerFactory.getLogger(ReflectionUtil.class);

    /**
     * 循环向上转型,获取类的所有DeclaredField.
     */
    @SuppressWarnings("unchecked")
    public static List<Field> getDeclaredFields(final Object object) {
        Validate.notNull(object, "object不能为空");
        Class clazz = object.getClass();
        List<Field> fieldList = new ArrayList<Field>();
        for (Class superClass = clazz; superClass != Object.class; superClass = superClass.getSuperclass()) {
            fieldList.addAll(Arrays.asList(superClass.getDeclaredFields()));
        }
        return fieldList;
    }


    /**
     * 直接读取对象属性值,无视private/protected修饰符,不经过getter函数.
     */
    public static Object getFieldValue(final Object object, final String fieldName) {
        Validate.notNull(object, "object不能为空");
        Field field = getDeclaredField(object, fieldName);

        if (field == null)
            throw new IllegalArgumentException("Could not find field [" + fieldName + "] on target [" + object + "]");

        makeAccessible(field);

        Object result = null;
        try {
            result = field.get(object);
        } catch (IllegalAccessException e) {
            logger.error("不可能抛出的异常{}", e.getMessage());
        }
        return result;
    }

    /**
     * 直接设置对象属性值,无视private/protected修饰符,不经过setter函数.
     */
    public static void setFieldValue(final Object object, final String fieldName, final Object value) {
        Validate.notNull(object, "object不能为空");
        Field field = getDeclaredField(object, fieldName);

        if (field == null)
            throw new IllegalArgumentException("Could not find field [" + fieldName + "] on target [" + object + "]");

        makeAccessible(field);

        try {
            field.set(object, value);
        } catch (IllegalAccessException e) {
            logger.error("不可能抛出的异常:{}", e.getMessage());
        }
    }


    /**
     * 循环向上转型,获取类的DeclaredField.
     */
    @SuppressWarnings("unchecked")
    protected static Field getDeclaredField(final Object object, final String fieldName) {
        Validate.notNull(object, "object不能为空");
        Validate.notEmpty(fieldName, "fieldName");
        Class clazz = object.getClass();
        for (Class superClass = clazz; superClass != Object.class; superClass = superClass.getSuperclass()) {
            try {
                return superClass.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
                // Field不在当前类定义,继续向上转型
            }
        }
        return null;
    }


    /**
     * 强制转换fileld可访问.
     */
    protected static void makeAccessible(final Field field) {
        if (!Modifier.isPublic(field.getModifiers()) || !Modifier.isPublic(field.getDeclaringClass().getModifiers())) {
            field.setAccessible(true);
        }
    }






}
