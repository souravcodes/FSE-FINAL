package com.fse.s1.projectmanager.util;

import java.lang.reflect.Field;
import java.util.stream.Stream;

public class Util {
	
	public static <C> C convertObjectFieldsFromEmptyToNull(C c) {
		Field[] fields = c.getClass().getDeclaredFields();
		Stream<Field> stream = Stream.of(fields);
		stream.forEach(e -> {
			try {
				e.setAccessible(true);
				if(e.getType() == String.class && e.get(c).equals("")){
					e.set(c, null);
				}
			} catch (IllegalArgumentException | IllegalAccessException e1) {
				e1.printStackTrace();
			}
		});
		return c;
	}
	
	public static <C, T> T exchangeUserAndUserTo(C from, T to){
		
		Field[] fromFields = from.getClass().getDeclaredFields();
		Field[] toFields = to.getClass().getDeclaredFields();
		
		for (Field tf : toFields) {
			for (Field ff : fromFields) {
				if(ff.getName().equalsIgnoreCase(tf.getName())){
					ff.setAccessible(true);
					tf.setAccessible(true);
					try {
						tf.set(to, ff.get(from));
						break;
					} catch (IllegalArgumentException | IllegalAccessException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return to;
	}
}
