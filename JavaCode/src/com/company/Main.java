package com.company;

import java.io.*;
import java.lang.reflect.Array;
import java.util.Hashtable;

public class Main {

    public static void main(String[] args) throws IOException {

        runParser("game.log.txt");

    }

    public static void runParser(String fileName) throws IOException {
        String path = "./" + fileName;
        File fileIn = new File(path);
        FileReader in = new FileReader(fileIn);
        BufferedReader br = new BufferedReader(in);

        String line;
        int gameN = 0;
        Game game = new Game();
        Hashtable <Integer, Game> gamesPlayed = new Hashtable<>();
        while ((line = br.readLine()) != null){

            String[] commandArr = getCommand(line);
            String command = commandArr[0];
            String commandText = commandArr[1];

            switch (command){
                case "InitGame":
                    gamesPlayed.put(gameN, game);
                    gameN++;
                    game = new Game("" + gameN);
                    break;
                case "ClientConnect":
                    game.clientConnect(commandText);
                case "ClientUserinfoChanged":
                    game.clientUserInfoChanged(commandText);
                default:
                    break;

            }
        }




    }

    public static String [] getCommand(String line){

        if(line == null){
            return new String[0];
        }
        //Gets a Line and separates into a list of [0] Time, [1] Command and [2] Command Text
        String [] finalArray = new String[2];

        String[] lineArray = line.split("\\d+:\\d+ ", 3);


        String[] commandArray = lineArray[1].split(": ",2);
        finalArray[0] = commandArray[0];
        if (commandArray.length > 1){
            finalArray[1] = commandArray[1];
        }else {
            finalArray[1] = "";
        }

        return finalArray;
    }
}
