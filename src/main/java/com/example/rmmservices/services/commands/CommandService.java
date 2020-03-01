package com.example.rmmservices.services.commands;

public interface CommandService<T> {

    <Output> Output handle(T command);

}
