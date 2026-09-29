package com.softdesign.livetest.util;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class CommonsStringBuilder {
    public static String toString(Object object) {
        return ToStringBuilder.reflectionToString(object, ToStringStyle.JSON_STYLE);
    }

    public static String toStringGson(Object object) {
        Gson gson = (new GsonBuilder()).addSerializationExclusionStrategy(new ExclusionStrategy() {
            public boolean shouldSkipField(FieldAttributes f) {
                return f.hasModifier(128); }
            public boolean shouldSkipClass(Class<?> clazz) {
                return false;
            }
        }).create();
        return gson.toJson(object);
    }
}
