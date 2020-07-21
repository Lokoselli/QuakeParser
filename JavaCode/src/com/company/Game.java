package com.company;

import java.io.File;
import java.util.*;
import java.util.regex.Pattern;

public class Game {

    private int totalKills;
    private String gameId;
    private HashMap<String, Player> playerHashMap = new HashMap<>();
    private ArrayList<String> playerList = new ArrayList<>();
    private String playerKills = "Kills\n";

    Game(String id){
        this.gameId = id;
    }


    public void clientConnect(String id){
        Player client = new Player(id, this);
        playerHashMap.put(client.getId(), client);
    }

    public void clientUserInfoChanged(String commandText){
        String[] commandsArr = commandText.split(" ", 2);

        String name = commandText.split(Pattern.quote(File.separator))[1];
        Player client = playerHashMap.get(commandsArr[0]);

        client.setName(name);

    }

    public void kill(String commandText){
        this.totalKills++;

        String[] commandTextArr = commandText.split(" ");

        String idKillerPlayer = commandTextArr[0];
        Player killerPlayer = playerHashMap.get(idKillerPlayer);

        String idKilledPlayer = commandTextArr[1];
        Player killedPlayer = playerHashMap.get(idKilledPlayer);

        String gunId = commandTextArr[2].split(":")[0];

        if(killerPlayer != null){
            killerPlayer.addKill(idKilledPlayer);
        }

        killedPlayer.computeDeath(idKillerPlayer);

    }


    public void printGameResult(){


        Iterator it = playerHashMap.entrySet().iterator();

        while (it.hasNext()){
            Map.Entry pair = (Map.Entry)it.next();
            Player player = (Player) pair.getValue();
            String name = player.getName();
            int kills = player.getKills();
            playerList.add(name);
            playerKills += name + " : " + kills + "\n";


        }

        String header = "Game Number " + this.gameId + "\n";
        String breaker = "-----------------------------\n";
        String totalKills = "Total Kills: " + this.totalKills + "\n";
        String players = "Players: " + this.playerList + "\n";
        System.out.println(header + breaker + totalKills + breaker + players + breaker + playerKills);


    }


}

