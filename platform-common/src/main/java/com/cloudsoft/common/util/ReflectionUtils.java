package com.cloudsoft.common.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.reflect.MethodUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 反射工具
 * @author zhujianrong
 */
public class ReflectionUtils {
	//
	private static final Map<Class, List<Field>> fieldsMap = new ConcurrentHashMap<Class, List<Field>>();
	public static final String EMPTY_STRING = "";

	/**
	 * 生成Getter方法
	 */
	public static String getter(Field f){
		String prefix = "get";
		if(f.getType() == Boolean.class || f.getType() == boolean.class){
			prefix = "is";
		}
		String field = f.getName();
		return getter(field, prefix);
	}

	/**
	 * 生成Getter方法
	 */
	public static String getter(String field){
		return getter(field, "get");
	}

	/**
	 * 生成Getter方法
	 */
	private static String getter(String field, String prefix){
		String getter = null;
		if(field.length() == 1){
			getter = prefix + field.toUpperCase();
		}else{
			if("is".equals(prefix) && StringUtils.startsWith(field, "is") && field.length() >= 3 && field.charAt(2) >= 'A' && field.charAt(2) <= 'Z'){
				getter = field;
			}else{
				char c0 = field.charAt(0);
				char c1 = field.charAt(1);
				if((c0 >= 'A' && c0 <= 'Z') && (c1 >= 'A' && c1 <= 'Z')){
					getter = prefix + field;
				}else if((c0 < 'A' || c0 > 'Z') && (c1 >= 'A' && c1 <= 'Z')){
					getter = prefix + field;
				}else if((c0 < 'A' || c0 > 'Z') && (c1 < 'A' || c1 > 'Z')){
					getter = prefix + unvar(field);
				}else{
					throw new RuntimeException("不能生成Getter方法，不合法的变量名：" + field);
				}
			}
		}
		return getter;
	}

	/**
	 * 生成Setter方法
	 */
	public static String setter(Field f){
		String field = f.getName();
		boolean isBoolean = f.getType() == Boolean.class || f.getType() == boolean.class;
		return setter(field, isBoolean);
	}

	/**
	 * 生成Setter方法
	 */
	public static String setter(String field){
		return setter(field, false);
	}

	/**
	 * 生成Setter方法
	 */
	private static String setter(String field, boolean isBoolean){
		String getter = null;
		if(field.length() == 1){
			getter = "set" + field.toUpperCase();
		}else{
			if(isBoolean && StringUtils.startsWith(field, "is") && field.length() >= 3 && field.charAt(2) >= 'A' && field.charAt(2) <= 'Z'){
				getter = "set" + field.substring(2);
			}else{
				char c0 = field.charAt(0);
				char c1 = field.charAt(1);
				if((c0 >= 'A' && c0 <= 'Z') && (c1 >= 'A' && c1 <= 'Z')){
					getter = "set" + field;
				}else if((c0 < 'A' || c0 > 'Z') && (c1 >= 'A' && c1 <= 'Z')){
					getter = "set" + field;
				}else if((c0 < 'A' || c0 > 'Z') && (c1 < 'A' || c1 > 'Z')){
					getter = "set" + unvar(field);
				}else{
					throw new RuntimeException("不能生成Setter方法，不合法的变量名：" + field);
				}
			}
		}
		return getter;
	}

	public static final boolean hasField(Class clazz, String fieldName){
		return ReflectionUtils.getClassField(clazz, fieldName) != null;
	}

	public static final boolean hasStaticField(Class clazz, String fieldName){
		return ReflectionUtils.getClassField(clazz, fieldName) != null;
	}

	/**
	 * 取类Fields
	 */
	public static final List<Field> getClassFields(Class clazz){
		if(fieldsMap.containsKey(clazz)){
			return fieldsMap.get(clazz);
		}
		Field[] fields = clazz.getDeclaredFields();
		List<Field> fieldList = new ArrayList<Field>(Arrays.asList(fields));
		Class parent = getMappedSuperclass(clazz);
		while(parent != null){
			fields = parent.getDeclaredFields();
			for(Field field : fields){
				boolean b = true;
				for(Field f : fieldList){
					if(f.getName().equals(field.getName()) && f.getType() == field.getType()){
						b = false;
						break;
					}
				}
				if(b){
					fieldList.add(field);
				}
			}
			parent = getMappedSuperclass(parent);
		}
		fieldsMap.put(clazz, fieldList);
		return fieldList;
	}

	private static final Map<String, Field> fieldMap = new ConcurrentHashMap<String, Field>(128);

	/**
	 * 取类Field
	 */
	public static final Field getClassField(Class clazz, String fieldName){
		Field field = null;
		if(clazz != null && StringUtils.isNotEmpty(fieldName)){
			field = fieldMap.get(clazz.getName() + "_" + fieldName);
			if(field == null){
				try{
					field = FieldUtils.getDeclaredField(clazz, fieldName, true);
					if(field == null){
						Class parent = getMappedSuperclass(clazz);
						while(parent != null){
							field = FieldUtils.getDeclaredField(parent, fieldName, true);
							if(field != null){
								break;
							}
							parent = getMappedSuperclass(parent);
						}
					}
					if(field != null){
						fieldMap.put(clazz.getName() + "_" + fieldName, field);
					}
				}catch(Exception e){
					System.out.println("clazz：" + clazz.getName() + " don't have field：" + fieldName);
				}
			}
		}
		return field;
	}

	private static final Map<String, Method> methodMap = new ConcurrentHashMap<String, Method>(128);

	/**
	 * 取类方法
	 */
	public static final Method getClassMethod(Class clazz, String methodName){
		Method method = methodMap.get(clazz.getName() + "_" + methodName);
		if(method == null){
			method = MethodUtils.getAccessibleMethod(clazz, methodName);
			if(method == null){
				Class parent = getMappedSuperclass(clazz);
				while(parent != null){
					method = MethodUtils.getAccessibleMethod(parent, methodName);
					if(method != null){
						break;
					}
					parent = getMappedSuperclass(parent);
				}
			}
			methodMap.put(clazz.getName() + "_" + methodName, method);
		}
		return method;
	}

	/**
	 * 取对象属性值
	 */
	public static String getFieldStringValue(Object obj, Field field){
		return getFieldStringValue(obj, field.getName());
	}

	/**
	 * 取对象属性值
	 */
	public static String getFieldStringValue(Object obj, String field){
		Object result = getFieldValue(obj, field);
		return result != null ? result.toString() : EMPTY_STRING;
	}

	public static Object getFieldValue(Object obj, Field field){
		return getFieldValue(obj, field.getName());
	}

	/**
	 * 取对象属性值
	 */
	public static Object getFieldValue(Object obj, String field){
		Object result = null;
		try{
			String[] arrays = field.trim().split("\\.");
			Object object = null;
			for(int i = 0; i < arrays.length; i++){
				String temp = dealReference(arrays[i]);
				if("id".equals(temp)){
					object = getObjectId(object == null ? obj : object);
				}else{
					Field f = getClassField(object == null ? obj.getClass() : object.getClass(), temp);
					String getter = (f != null ? getter(f) : getter(temp));
					object = MethodUtils.invokeMethod(object == null ? obj : object, getter);
				}
				if(object == null){
					break;
				}
				if(i == arrays.length - 1 && object != null){
					result = object;
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}

	public static void setFieldValue(Object target, Field field, Object value){
		setFieldValue(target, field.getName(), value);
	}

	public static void setFieldValue(Object target, String fieldName, Object value){
		try{
			if(value == null){
				Field field = getClassField(target.getClass(), fieldName);
				MethodUtils.invokeMethod(target, setter(fieldName), new Object[]{null}, new Class[]{field.getType()});
			}else{
				MethodUtils.invokeMethod(target, setter(fieldName), value);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public static final Field getFieldByType(Class clazz, Class type){
		for(Field field : ReflectionUtils.getClassFields(clazz)){
			if(field.getType() == type){
				return field;
			}
		}
		return null;
	}

	public static Field getFieldBySupperclass(Class clazz, String superclass){
		for(Field field : ReflectionUtils.getClassFields(clazz)){
			Class type = field.getType();
			if(type.getSimpleName().equals(superclass) || (type.getSuperclass() != null && type.getSuperclass().getSimpleName().equals(superclass))){
				return field;
			}
		}
		return null;
	}

	public static final long getObjectId(Object object){
		if(object != null){
			//TODO 如果存在超类 并且公共字段已经抽取出来，此处直接获取
			try{
				Object id = MethodUtils.invokeMethod(object, "getId");
				if(NumberUtils.isPositiveInteger(id)){
					return Long.parseLong(id.toString());
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return 0;
	}

	public static final Class getMappedSuperclass(Class clazz){
		if(clazz.getGenericSuperclass() != null && clazz.getGenericSuperclass() != Object.class){
			Class parent = clazz.getSuperclass();
			//TODO 是否判断存在jpa 注解  @MappedSuperclass
			return parent;
		}
		return null;
	}

	/**
	 * 如 name[code] 返回 name
	 */
	public static String dealReference(String reference){
		if(reference.contains("[")){
			reference = reference.substring(0, reference.indexOf("["));
		}
		return reference;
	}

	public static String unvar(String string){
		return string.substring(0, 1).toUpperCase() + string.substring(1);
	}
}
