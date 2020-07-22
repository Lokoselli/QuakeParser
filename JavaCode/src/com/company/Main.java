package com.company;

import java.io.*;
import java.lang.reflect.Array;
import java.util.Hashtable;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {

        Hashtable<Integer, Game> games = runParser("game.log.txt");
        Scanner scan = new Scanner(System.in);
        String continueCheck = "";
        while (!continueCheck.equals("n")){
            menu(games);
            System.out.println("Want to check for another? Y/N");
            continueCheck = scan.next().toLowerCase();
        }



    }

    public static int menu(Hashtable<Integer,Game> games){
        Scanner scan = new Scanner(System.in);
        System.out.println("See results for What Games:");
        System.out.println("Games Available: 1 - " + (games.size() -1));
        int i;
        try{
            i = scan.nextInt();
        }catch (InputMismatchException exception){
            System.out.println("Please enter a number from 1 through " + (games.size() - 1));
            return menu(games);
        }
        if(i >= games.size() || i <= 0){
            System.out.println("Please enter a number from 1 through " + (games.size() - 1));
            return menu(games);
        }

        games.get(i).printGameResult();

        return i;
    }

    public static Hashtable<Integer, Game> runParser(String fileName) throws IOException {
        String path = "./" + fileName;
        File fileIn = new File(path);
        FileReader in = new FileReader(fileIn);
        BufferedReader br = new BufferedReader(in);


        String line;
        int gameN = 0;
        Game game = new Game("0");
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
                    break;
                case "ClientUserinfoChanged":
                    game.clientUserInfoChanged(commandText);
                    break;
                case "Kill":
                    game.kill(commandText);
                default:
                    break;

            }
        }


        return gamesPlayed;

    }

    public static String [] getCommand(String line){

        if(line == null){
            return new String[0];
        }

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
