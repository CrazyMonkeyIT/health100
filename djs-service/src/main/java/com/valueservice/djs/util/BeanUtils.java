package com.valueservice.djs.util;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class BeanUtils {

    private BeanUtils() {}

    /**
     * 用于model修改时的对象复制,把srcModel复制到destModel,srcModel中为null的字段不复制，同名且类型相同的属性才复制
     * 
     * @param srcModel
     *            表单提交的源对象
     * @param destModel
     *            数据库中的目标对象
     */
    public static void copyNotNullFields(Object srcModel, Object destModel) {
        if (srcModel == null || destModel == null) {
            return;
        }
        try {
            invokePropertyCopy(srcModel, destModel);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void invokePropertyCopy(Object srcModel, Object destModel) throws IntrospectionException,
            IllegalAccessException, InvocationTargetException {
        PropertyDescriptor[] srcDescriptors = Introspector.getBeanInfo(srcModel.getClass()).getPropertyDescriptors();
        PropertyDescriptor[] destDescriptors = Introspector.getBeanInfo(destModel.getClass()).getPropertyDescriptors();
        Map<String, PropertyDescriptor> destPropertyNameDescriptorMap = new HashMap<String, PropertyDescriptor>();
        for (PropertyDescriptor destPropertyDescriptor : destDescriptors) {
            destPropertyNameDescriptorMap.put(destPropertyDescriptor.getName(), destPropertyDescriptor);
        }
        for (PropertyDescriptor srcDescriptor : srcDescriptors) {
            PropertyDescriptor destDescriptor = destPropertyNameDescriptorMap.get(srcDescriptor.getName());
            // 类型相同的属性才复制
            if (destDescriptor != null && destDescriptor.getPropertyType() == srcDescriptor.getPropertyType()
                    && destDescriptor.getPropertyType() != Class.class) {
                Object val = srcDescriptor.getReadMethod().invoke(srcModel);
                if (val != null && destDescriptor.getWriteMethod() != null) {
                    destDescriptor.getWriteMethod().invoke(destModel, val);
                }
            }
        }

    }

    /**
     * 适用于copy非集合类的属性，主要适用场景，源对象和目标对象有相同名称且为集合类的属性
     * 使用该方法存在的问题：对于集合类的属性，即使类型相同，
     * 也没有办法copy成功 添加该方法的原因：对于集合类，在运行期间会丢失类型信息
     * 
     * @param source
     *            源对象
     * @param target
     *            目标对象
     */
    public static void copyNonCollectionProperties(Object source, Object target) {
        List<String> ignoreProperties = new ArrayList<>();
        Field[] declaredFields = target.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            if (Collection.class.isAssignableFrom(field.getType()) || Map.class.isAssignableFrom(field.getType())) {
                ignoreProperties.add(field.getName());
            }
        }
        org.springframework.beans.BeanUtils.copyProperties(source, target,
                ignoreProperties.toArray(new String[ignoreProperties.size()]));
    }
}
