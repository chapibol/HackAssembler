package com.coursera;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Parser {
    int instructionNum = 0;
    private final SymbolTable symbolTable = new SymbolTable();
    private String parsedInstructions;

    public void parse(File fileToParse) throws FileNotFoundException {
        Scanner firstPassScanner = new Scanner(fileToParse);

        // first pass gathering
        while(firstPassScanner.hasNext()){
            String currentLine = firstPassScanner.nextLine();
            // if the line starts with // or is empty processing will be skipped
            if(isInstructionOrSymbol(currentLine)){
                String cleanInstruction = cleanUpInstruction(currentLine);

                if(isLabelInstruction(cleanInstruction)){
                    symbolTable.getSymbols().put(getLabel(cleanInstruction), instructionNum);
                }else{
                    instructionNum++; // only increment if not a symbol
                }
            }
        }
        System.out.println(symbolTable.getSymbols().toString());
        firstPassScanner.close();

        Scanner secondPassScanner = new Scanner(fileToParse);
    }

    private String cleanUpInstruction(String dirtyInstruction){
        int potentialCommentLocation = dirtyInstruction.indexOf("//");
        if(potentialCommentLocation > 0){
            // further clean up the string of potential comments after the instruction and then remove white spaces
            dirtyInstruction = dirtyInstruction.substring(0, potentialCommentLocation);
        }
        // if no comments after instruction remove white spaces only
        return dirtyInstruction.trim(); // return the cleaned up instruction
    }

    private String getLabel(String labelInstruction){
        return labelInstruction.substring(1, labelInstruction.indexOf(")"));
    }

    private boolean isLabelInstruction(String instruction){
        return instruction.startsWith("(");
    }

    private boolean isInstructionOrSymbol(String instruction){
        return !instruction.startsWith("//") && !instruction.isEmpty();
    }

    public boolean isAInstruction(String instruction){
        return instruction.startsWith("@");
    }
}
