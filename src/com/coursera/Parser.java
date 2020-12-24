package com.coursera;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Parser {
    int instructionNumber = 0;

    public void parse(File fileToParse) throws FileNotFoundException {
        Scanner scanner = new Scanner(fileToParse);

        while(scanner.hasNext()){
            String currentLine = scanner.nextLine();
            // if the line starts with // or is empty processing will be skipped
            if(isInstruction(currentLine)){
                int potentialCommentLocation = currentLine.indexOf("//");
                String cleanInstruction;
                if(potentialCommentLocation > 0){
                    // further clean up the string of potential comments after the instruction and then remove white spaces
                    cleanInstruction = currentLine.substring(0, potentialCommentLocation).trim();
                }else{
                    // if no comments after instruction remove white spaces only
                    cleanInstruction = currentLine.trim();
                }

                if(isAInstruction(cleanInstruction)) {

                }else{
                    // C instruction for now
                }

                System.out.println(cleanInstruction);
            }
        }
    }

    private boolean isInstruction(String instruction){
        return !instruction.startsWith("//") && !instruction.isEmpty();
    }

    public boolean isAInstruction(String instruction){
        return instruction.startsWith("@");
    }
}
