package com.coursera;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SymbolTable {

    private Map<String, Integer> symbols;

    public SymbolTable(){
        // Initializing symbol table
        this.symbols = Stream.of(new String[][]{
                {"R0", "0"},
                {"R1", "1"},
                {"R2", "2"},
                {"R3", "3"},
                {"R4", "4"},
                {"R5", "5"},
                {"R6", "6"},
                {"R7", "7"},
                {"R8", "8"},
                {"R9", "9"},
                {"R10", "10"},
                {"R11", "11"},
                {"R12", "12"},
                {"R13", "13"},
                {"R14", "14"},
                {"R15", "15"},
                {"SCREEN", "16384"},
                {"KBD", "24576"},
                {"SP", "0"},
                {"LCL", "1"},
                {"ARG", "2"},
                {"THIS", "3"},
                {"THAT", "4"}
        }).collect(Collectors.toMap(p -> p[0], p -> Integer.valueOf(p[1])));
    }

    public Map<String, Integer> getSymbols() {
        return symbols;
    }

    public void setSymbols(Map<String, Integer> symbols) {
        this.symbols = symbols;
    }
}
