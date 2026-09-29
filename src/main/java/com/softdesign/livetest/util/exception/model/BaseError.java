package com.softdesign.livetest.util.exception.model;

public interface BaseError {
    String getCode();
    String getDescription();
    boolean getAcceptParameters();
    default boolean disableStackTraceLog() { return false;}
}
