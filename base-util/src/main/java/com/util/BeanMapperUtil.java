/**
 * Copyright 2019.
 */
package com.util;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.dozer.DozerBeanMapper;
import org.dozer.loader.api.BeanMappingBuilder;
import org.dozer.loader.api.TypeMappingOptions;


/**
 * The Class BeanMapperUtil.
 *
 * @author mary.jane
 * @since Oct 29, 2018
 */
public class BeanMapperUtil {

	/**
	 * Instantiates a new bean mapper util.
	 */
	private BeanMapperUtil() {
		throw new IllegalStateException("Utility class");
	}


	/**
	 * Map.
	 *
	 * @param <T> the generic type
	 * @param source the source
	 * @param cls the cls
	 * @return the t
	 */
	public static <T> T map(Object source, Class<T> cls) {
		DozerBeanMapper mapper = new DozerBeanMapper();
		return mapper.map(source, cls);
	}


	/**
	 * Map.
	 *
	 * @param source the source
	 * @param destination the destination
	 */
	public static void map(Object source, Object destination) {
		map(source, destination, false);
	}


	/**
	 * Map.
	 *
	 * @param source the source
	 * @param destination the destination
	 * @param ignoreNull the ignore null
	 */
	public static void map(final Object source, final Object destination, boolean ignoreNull) {
		DozerBeanMapper mapper = new DozerBeanMapper();
		if (ignoreNull) {
			mapper.addMapping(new BeanMappingBuilder() {

				@Override
				protected void configure() {
					mapping(source.getClass(), destination.getClass(), TypeMappingOptions.mapNull(false),
							TypeMappingOptions.mapEmptyString(true));
				}
			});
		}
		mapper.map(source, destination);
	}


	/**
	 * Map ignore null and empty.
	 *
	 * @param source the source
	 * @param destination the destination
	 */
	public static void mapIgnoreNullAndEmpty(final Object source, final Object destination) {
		DozerBeanMapper mapper = new DozerBeanMapper();
		mapper.addMapping(new BeanMappingBuilder() {

			@Override
			protected void configure() {
				mapping(source.getClass(), destination.getClass(), TypeMappingOptions.mapNull(false),
						TypeMappingOptions.mapEmptyString(false));
			}
		});
		mapper.map(source, destination);
	}


	/**
	 * Map list.
	 *
	 * @param <T> the generic type
	 * @param sourceList the source list
	 * @param destinationClass the destination class
	 * @return the list
	 */
	public static <T> List<T> mapList(Collection<T> sourceList, Class<T> destinationClass) {
		List<T> destinationList = new ArrayList<>();
		for (Object sourceObject : sourceList) {
			T destinationObject = map(sourceObject, destinationClass);
			destinationList.add(destinationObject);
		}
		return destinationList;
	}

}
