package com.cloudsoft.common.util;

import com.orbitz.apache.commons.lang3.reflect.MethodUtils;
import org.apache.commons.collections.CollectionUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BeanUtils {
	/**
	 * 复制二个对象相同的属性到列表
	 * @param toClass 返回的List里面的对象类型
	 * @param froms 复制源对象列表
	 */
	public static <E> List<E> copySamePropertiesList(Class<E> toClass, List froms){
		List list = new ArrayList();
		if(CollectionUtils.isNotEmpty(froms)){
			try{
				for(Object from : froms){
					Object to = toClass.newInstance();
					copySameProperties(to, from, null);
					list.add(to);
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return list;
	}

	/**
	 * 复制二个对象相同的属性到列表
	 * @param toClass 返回的List里面的对象类型
	 * @param froms 复制源对象列表
	 * @param excludes 排除那些属性不复制，比如复制jdo对象时id一般都不需要复制的
	 */
	public static <E> List<E> copySamePropertiesList(Class<E> toClass, List froms, String... excludes){
		List list = new ArrayList();
		if(CollectionUtils.isNotEmpty(froms)){
			try{
				for(Object from : froms){
					Object to = toClass.newInstance();
					copySameProperties(to, from, excludes);
					list.add(to);
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return list;
	}

	/**
	 * 复制二个对象相同的属性
	 * @param to 复制到这个对象
	 * @param from 从这个对象复制
	 * @param excludes 排除那些属性不复制，比如复制jdo对象时id一般都不需要复制的
	 */
	public static void copySameProperties(Object to, Object from, String... excludes){
		try{
			if(to != null && from != null){
				List<String> list = null;
				if(null != excludes){
					list = Arrays.asList(excludes);
					if(!CollectionUtils.isNotEmpty(list)){
						list = null;
					}
				}
				List<Field> toFields = ReflectionUtils.getClassFields(to.getClass());
				List<Field> fromFields = ReflectionUtils.getClassFields(from.getClass());
				for(Field field : fromFields){
					String fieldName = field.getName();
					if("serialVersionUID".equalsIgnoreCase(fieldName)){
						continue;
					}
					for(Field f : toFields){
						if(f.getName().equals(fieldName) && f.getType() == field.getType()){
							Class fromclz = field.getType();
							Class toclz = f.getType();
							boolean b = false;
							Object fixValue = null;
							if(fromclz == toclz){
								b = true;
							}else if(toclz == Long.class && fromclz == long.class){
								b = true;
								fixValue = 0L;
							}else if(toclz == Integer.class && fromclz == int.class){
								b = true;
								fixValue = 0;
							}else if(toclz == Float.class && fromclz == float.class){
								b = true;
								fixValue = 0F;
							}else if(toclz == Double.class && fromclz == double.class){
								b = true;
								fixValue = 0D;
							}
							if(b){
								if(list == null || !list.contains(fieldName)){
									Object value = MethodUtils.invokeMethod(from, ReflectionUtils.getter(field));
									if(value == null && fixValue != null){
										value = fixValue;
									}
									MethodUtils.invokeMethod(to, ReflectionUtils.setter(field), value);
								}
							}
							break;
						}
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * 复制指定的字段到列表
	 * @param toClass 返回的List里面的对象类型
	 * @param froms 复制源对象列表
	 * @param copyFields 复制那些字段
	 */
	public static <E> List<E> copySpecifiedPropertiesList(Class<E> toClass, List froms, String... copyFields){
		List list = new ArrayList();
		if(CollectionUtils.isNotEmpty(froms)){
			try{
				for(Object from : froms){
					Object to = toClass.newInstance();
					copySpecifiedProperties(to, from, copyFields);
					list.add(to);
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return list;
	}

	/**
	 * 复制指定的字段
	 * @param to 复制到这个对象
	 * @param from 从这个对象复制
	 * @param copyFields 复制那些字段
	 */
	public static void copySpecifiedProperties(Object to, Object from, String... copyFields){
		try{
			if(to != null && from != null){
				List<String> list = Arrays.asList(copyFields);
				if(CollectionUtils.isNotEmpty(list)){
					List<Field> toFields = ReflectionUtils.getClassFields(to.getClass());
					List<Field> fromFields = ReflectionUtils.getClassFields(from.getClass());
					for(Field field : fromFields){
						String fieldName = field.getName();
						for(Field f : toFields){
							if(list.contains(fieldName) && f.getName().equals(fieldName)){
								Class fromclz = field.getType();
								Class toclz = f.getType();
								boolean b = false;
								boolean toString = false;
								if(fromclz == toclz || toclz == Object.class){
									b = true;
								}else if(toclz == String.class){
									b = true;
									toString = true;
								}else if(toclz == Long.class && (fromclz == long.class || fromclz == int.class)){
									b = true;
								}else if(toclz == long.class && fromclz == int.class){
									b = true;
								}else if(toclz == Integer.class && (fromclz == long.class || fromclz == int.class)){
									b = true;
								}else if(toclz == Double.class && (fromclz == double.class || fromclz == float.class || fromclz == long.class || fromclz == int.class)){
									b = true;
								}else if(toclz == double.class && (fromclz == float.class || fromclz == long.class || fromclz == int.class)){
									b = true;
								}else if(toclz == Float.class && (fromclz == float.class || fromclz == int.class)){
									b = true;
								}else if(toclz == float.class && (fromclz == int.class)){
									b = true;
								}
								if(b){
									Object value = MethodUtils.invokeMethod(from, ReflectionUtils.getter(field));
									MethodUtils.invokeMethod(to, ReflectionUtils.setter(field), toString ? (value != null ? value.toString() : value) : value);
									break;
								}
							}
						}
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public static boolean isBaseType(Object object) {
		Class className = object.getClass();
		if (className.equals(Integer.class) ||
				className.equals(Byte.class) ||
				className.equals(Long.class) ||
				className.equals(Double.class) ||
				className.equals(Float.class) ||
				className.equals(Character.class) ||
				className.equals(Short.class) ||
				className.equals(Boolean.class)) {
			return true;
		}
		return false;
	}
}
