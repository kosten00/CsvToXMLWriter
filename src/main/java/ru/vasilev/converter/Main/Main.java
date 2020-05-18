package ru.vasilev.converter.Main;

import ru.vasilev.converter.Converter;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Converter converter = new Converter();

        converter.convert("in.csv", "out.xml");
    }
}
