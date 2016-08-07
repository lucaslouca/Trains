package com.lucaslouca.service;

/**
 * {@code RuntimeException} that is thrown when an error occurs during a request to the {@code LLRailRoadService}. An
 * example would be, requesting the shortest path to a town where a route does not exist.
 */
public class LLRailRoadServiceException extends RuntimeException {
    public LLRailRoadServiceException(String message) {
        super(message);
    }
}
