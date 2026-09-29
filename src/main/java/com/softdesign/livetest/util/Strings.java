package com.softdesign.livetest.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.helpers.MessageFormatter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.IntStream;

public class Strings {

    private Strings () {}

    public static String formatMessage(String message, Object... parameters) {
        int quantity = StringUtils.countMatches(message, "{}");
        if (Objects.isNull(parameters)) {
            parameters = getList(new Object[quantity], 0, quantity);

        } else if (parameters.length < quantity) {
            parameters = getList(parameters, parameters.length, quantity);
        }
        String response = MessageFormatter.arrayFormat(message, parameters).getMessage();
        return StringUtils.isNoneBlank(response) ? response.trim() : response;
    }

    private static Object[] getList(Object[] parameters, Integer init, Integer end) {
        ArrayList<Object> parameterList = new ArrayList(Arrays.asList(parameters));
        IntStream.range(init, end).forEach((n) -> parameterList.add(""));
        return parameterList.toArray();
    }

}
