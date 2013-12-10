package io.github.ibuildthecloud.gdapi.model;

import io.github.ibuildthecloud.gdapi.annotation.Field;

import java.net.URL;
import java.util.Map;

public interface Resource {

    String getId();

    String getType();

    Map<String,URL> getLinks();

    Map<String,URL> getActions();

    @Field(include = false)
    Map<String,Object> getFields();

}