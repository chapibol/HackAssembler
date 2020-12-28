package com.coursera;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Code {

    private final Map<String, String> computationValues;
    private final Map<String, String> destinationValues;
    private final Map<String, String> jumpValues;

    public Code(){
        // Initializing C instruction meanings
        this.computationValues = Stream.of(new String[][]{
                {"0", "0101010"},
                {"1", "0111111"},
                {"-1", "0111010"},
                {"D", "0001100"},
                {"A", "0110000"},
                {"M", "1110000"},
                {"!D", "0001101"},
                {"!A", "0110001"},
                {"!M", "1110001"},
                {"-D", "0001111"},
                {"-A", "0110011"},
                {"-M", "1110011"},
                {"D+1", "0011111"},
                {"A+1", "0110111"},
                {"M+1", "1110111"},
                {"D-1", "0001110"},
                {"A-1", "0110010"},
                {"M-1", "1110010"},
                {"D+A", "0000010"},
                {"D+M", "1000010"},
                {"D-A", "0010011"},
                {"D-M", "1010011"},
                {"A-D", "0000111"},
                {"M-D", "1000111"},
                {"D&A", "0000000"},
                {"D&M", "1000000"},
                {"D|A", "0010101"},
                {"D|M", "1010101"}
        }).collect(Collectors.toMap(p -> p[0], p -> p[1]));

        this.destinationValues = Stream.of(new String[][]{
                {"", "000"},
                {"M", "001"},
                {"D", "010"},
                {"MD", "011"},
                {"A", "100"},
                {"AM", "101"},
                {"AD", "110"},
                {"AMD", "111"}
        }).collect(Collectors.toMap(p -> p[0], p -> p[1]));

        this.jumpValues = Stream.of(new String[][]{
                {"", "000"},
                {"JGT", "001"},
                {"JEQ", "010"},
                {"JGE", "011"},
                {"JLT", "100"},
                {"JNE", "101"},
                {"JLE", "110"},
                {"JMP", "111"}
        }).collect(Collectors.toMap(p -> p[0], p -> p[1]));
    }

    protected String toBinary(String dest, String comp, String jump){
        String cPadding = "111";
        String binaryDest = destinationValues.get(dest);
        String binaryComp = computationValues.get(comp);
        String binaryJump = jumpValues.get(jump);
        return cPadding + binaryComp + binaryDest + binaryJump;
    }


    protected String toBinary(Integer value){
        String baseValue = Integer.toBinaryString(value);
        int numPadding = 16 - baseValue.length();
        return "0".repeat(Math.max(0, numPadding)) + baseValue;
    }

    protected String getAInstructionValue(String aInstruction){
        return aInstruction.substring(1);
    }

}
