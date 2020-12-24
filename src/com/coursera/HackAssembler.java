package com.coursera;

import java.io.File;
import java.io.FileNotFoundException;

public class HackAssembler {

    public static void main(String[] args) throws FileNotFoundException {
        String fileName = args[0];
        String filePath = "resources/" + fileName;
        File fileToParse = new File(filePath);
        Parser parser = new Parser();
        parser.parse(fileToParse);
    }
}
