package com.coursera;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Parser {
    int instructionNumber = 0;
    private final SymbolTable symbolTable = new SymbolTable();

    public String parse(File fileToParse) throws FileNotFoundException {
        Scanner firstPassScanner = new Scanner(fileToParse);

        // first pass gathering
        while(firstPassScanner.hasNext()){
            String currentLine = firstPassScanner.nextLine();
            // if the line starts with // or is empty processing will be skipped
            if(isInstructionOrSymbol(currentLine)){
                if(isLabelInstruction(currentLine)){
                    symbolTable.addSymbol(getLabel(cleanUpInstruction(currentLine)), instructionNumber);
                }else{
                    instructionNumber++; // only increment if not a symbol
                }
            }
        }
        System.out.println(symbolTable.toString());
        firstPassScanner.close();

        //reset instruction number tracker
        instructionNumber = 0;
        int n = 16;// the next available memory address
        StringBuilder binaryProgram = new StringBuilder();

        Scanner secondPassScanner = new Scanner(fileToParse);
        Code code = new Code();
        while(secondPassScanner.hasNext()){
            String currentLine = secondPassScanner.nextLine();
            if(isInstructionOrSymbol(currentLine) || !isLabelInstruction(currentLine) ){ // ignoring label declarations
                String cleanInstruction = cleanUpInstruction(currentLine);
                if(isAInstruction(cleanInstruction)){
                    String aInstructionValueOrSymbol = code.getAInstructionValue(cleanInstruction);// strip the @ from the command
                    String binaryAInstruction = "";
                    if(isNumber(aInstructionValueOrSymbol)){
                        // go ahead and allocate and translate to payload
                        binaryAInstruction += code.toBinaryAInstruction(Integer.parseInt(aInstructionValueOrSymbol));
                    }else{
                        // query symbol table and save it if not there
                        Integer value = symbolTable.getSymbol(aInstructionValueOrSymbol);
                        if(value == null){
                            // add symbol to table
                            symbolTable.addSymbol(aInstructionValueOrSymbol, n);
                            binaryAInstruction += code.toBinaryAInstruction(n);
                            n++; // increment next available address
                        }else{
                            binaryAInstruction += code.toBinaryAInstruction(value);
                        }
                    }
                    binaryProgram.append(binaryAInstruction).append("\n");
                }else{ // handling C instructions

                }
            }
        }
        return binaryProgram.toString().trim();
    }

    private boolean isNumber(String aInstruction){
        try{
            Integer.parseInt(aInstruction);
        }catch (NumberFormatException e){
            return false;
        }
        return true;
    }

    /**
     * Method to clean up a dirty instruction. Dirty meaning it could have an inline comment or white space after the
     * instruction. This method will remove the comment if there as well as any white space. returning the clean
     * Instruction
     * @param dirtyInstruction the instruction to clean
     * @return clean instruction
     */
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