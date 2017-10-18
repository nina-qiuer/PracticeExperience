package com.tuniu.qms.common.util;

import java.lang.reflect.Type;
import java.util.List;

import com.alibaba.fastjson.util.DeserializeBeanInfo;
import com.alibaba.fastjson.util.FieldInfo;
 import com.alibaba.fastjson.util.TypeUtils;
/**
 * Created by zhangsensen on 2017/1/10.
 */
public class CopyerUtil {
	
	/**
	 * 将父类中的值装配到子类
	 * @param baseClass
	 * @param subClass
	 */
    public static<B, S extends B> void copy(B baseClass, S subClass){
        try{
            if(baseClass == null || subClass == null){
                return;
            }

            DeserializeBeanInfo deserializeBeanInfo = DeserializeBeanInfo.computeSetters(subClass.getClass(), (Type) subClass.getClass());
            List<FieldInfo> setters = deserializeBeanInfo.getFieldList();//获得子类所有属性的set方法

            List<FieldInfo> getters = TypeUtils.computeGetters(baseClass.getClass(), null);//获得父类所有属性的get方法

            for(int i=0; i<getters.size(); i++){

                FieldInfo getterField = getters.get(i);
                for(int j=0; j<setters.size(); j++){

                    FieldInfo setterField = setters.get(j);
                    if(getterField.getName().compareTo(setterField.getName()) == 0){
                        Object object = getterField.getMethod().invoke(baseClass);
                        setterField.getMethod().invoke(subClass, object);
                        break;
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
