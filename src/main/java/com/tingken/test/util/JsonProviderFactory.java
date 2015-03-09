package com.tingken.test.util;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

/**
 * Jackson configuration:
 * <ul>
 * <li>WRAP_ROOT_VALUE = true</li>
 * <li>WRITE_DATES_AS_TIMESTAMPS = false</li>
 * <li>FAIL_ON_EMPTY_BEANS = false</li>
 * <li>SerializationInclusion = NON_NULL</li>
 * <li>WRITE_NULL_MAP_VALUES = false</li>
 * <li>WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED = true</li>
 * <li>WRITE_ENUMS_USING_TO_STRING = true</li>
 * <li>ACCEPT_SINGLE_VALUE_AS_ARRAY = true</li>
 * <li>READ_ENUMS_USING_TO_STRING = true</li>
 * <li>UNWRAP_ROOT_VALUE = true (default)</li>
 * </ul>
 */
public class JsonProviderFactory implements FactoryBean<JacksonJsonProvider> {

    private boolean unwrapRootValue = true;

    @Override
    public JacksonJsonProvider getObject() throws BeansException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.WRAP_ROOT_VALUE, true);
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        objectMapper.setSerializationInclusion(Include.NON_NULL);
        objectMapper.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false);
        objectMapper.configure(SerializationFeature.WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED, true);
        objectMapper.configure(SerializationFeature.WRITE_ENUMS_USING_TO_STRING, true);
        objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        objectMapper.configure(DeserializationFeature.READ_ENUMS_USING_TO_STRING, true);
        objectMapper.configure(DeserializationFeature.UNWRAP_ROOT_VALUE, unwrapRootValue);
        JacksonJaxbJsonProvider jacksonJsonProvider = new JacksonJaxbJsonProvider(objectMapper,
                JacksonJaxbJsonProvider.BASIC_ANNOTATIONS);
        return jacksonJsonProvider;
    }

    @Override
    public Class<?> getObjectType() {
        return JacksonJsonProvider.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    /**
     * @param unwrapRootValue The unwrapRootValue to set.
     */
    public void setUnwrapRootValue(boolean unwrapRootValue) {
        this.unwrapRootValue = unwrapRootValue;
    }
}
