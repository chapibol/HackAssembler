package com.coursera;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class HackAssembler {

    public static void main(String[] args) throws IOException {
        String fileName = args[0];
        String filePath = "resources/" + fileName;
        File fileToParse = new File(filePath);
        Parser parser = new Parser();
        String binaryProgramStr = parser.parse(fileToParse);

        Path hackFilePath = Path.of(toHackExtension(filePath));
        Files.writeString(hackFilePath, binaryProgramStr);
    }

    private static String toHackExtension(String filePath){
        return filePath.replace(".asm", ".hack");
    }
}
