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
            String currentLine = firstPassScanner.nextLine().trim();
            // if the line starts with // or is empty processing will be skipped
            if(isInstructionOrSymbol(currentLine)){
                if(isLabelInstruction(currentLine)){
                    symbolTable.addSymbol(getLabel(cleanUpInstruction(currentLine)), instructionNumber);
                }else{
                    instructionNumber++; // only increment if not a symbol declaration
                }
            }
        }
        firstPassScanner.close();

        //reset instruction number tracker
        instructionNumber = 0;
        int n = 16;// the next available memory address
        StringBuilder binaryProgram = new StringBuilder();

        Scanner secondPassScanner = new Scanner(fileToParse);
        Code code = new Code();
        while(secondPassScanner.hasNext()){
            String currentLine = secondPassScanner.nextLine().trim();
            if(isInstructionOrSymbol(currentLine) && !isLabelInstruction(currentLine) ){ // ignoring label declarations as well
                String cleanInstruction = cleanUpInstruction(currentLine);
                if(isAInstruction(cleanInstruction)){
                    String aInstructionValueOrSymbol = code.getAInstructionValue(cleanInstruction);// strip the @ from the command
                    String binaryAInstruction = "";
                    if(isNumber(aInstructionValueOrSymbol)){
                        // go ahead and allocate and translate to payload
                        binaryAInstruction += code.toBinary(Integer.parseInt(aInstructionValueOrSymbol));
                    }else{
                        // query symbol table and save it if not there
                        Integer value = symbolTable.getSymbol(aInstructionValueOrSymbol);
                        if(value == null){
                            // add symbol to table
                            symbolTable.addSymbol(aInstructionValueOrSymbol, n);
                            binaryAInstruction += code.toBinary(n);
                            n++; // increment next available address
                        }else{
                            binaryAInstruction += code.toBinary(value);
                        }
                    }
                    binaryProgram.append(binaryAInstruction).append("\n");
                }else{ // handling C instructions
                    String binaryCInstruction = code.toBinary(getDest(cleanInstruction), getComp(cleanInstruction), getJump(cleanInstruction));
                    binaryProgram.append(binaryCInstruction).append("\n");
                }
            }
        }
        return binaryProgram.toString().trim();
    }

    private String getJump(String cInstruction){
        int semiColonLocation = getSemiColonLocation(cInstruction);
        return semiColonLocation > 0 ? cInstruction.substring(semiColonLocation + 1) : "";// will get D=D+A;JGE   "JGE" or "" if no semi
    }

    private String getComp(String cInstruction){
        int semiColonLocation = getSemiColonLocation(cInstruction);
        int equalSignLoc = getEqualSignLocation(cInstruction);
        if(semiColonLocation > 0){
            return cInstruction.substring(equalSignLoc + 1, semiColonLocation);// D=D+A;JGE will get "D+A"
        }else{
            return cInstruction.substring(equalSignLoc + 1);//D=D+A  will get "D+A"
        }
    }

    private String getDest(String cInstruction){
        int equalSignLocation = getEqualSignLocation(cInstruction);
        return equalSignLocation > 0 ? cInstruction.substring(0, equalSignLocation): "";
    }

    private int getSemiColonLocation(String instruction){
        return instruction.indexOf(";");
    }

    private int getEqualSignLocation(String instruction){
        return instruction.indexOf("=");
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