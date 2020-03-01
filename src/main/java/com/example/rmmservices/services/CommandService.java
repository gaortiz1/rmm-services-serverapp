package com.example.rmmservices.services;

public interface CommandService<T> {

    <Output> Output execute(T command);

}
