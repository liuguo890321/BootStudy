package com.asiainfo.util;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lenovo on 2017/6/26.
 */
public class MapUtil {


    public static Map<String, Object> toParamMap(Object source){
        Map<String, Object> paramMap = new HashMap<String, Object>();
        List<Field> fields = ReflectionUtil.getDeclaredFields(source);
        String fieldName;
        Object filedValue;
        for(Field field : fields){
            fieldName = field.getName();
            filedValue = ReflectionUtil.getFieldValue(source,fieldName);
            if(filedValue != null){
                paramMap.put(fieldName,filedValue);
            }
        }
        return paramMap;
    }



}
