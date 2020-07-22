package com.company;

import java.io.File;
import java.lang.reflect.Array;
import java.util.*;
import java.util.regex.Pattern;

public class Game {

    private String[] meansOfDeath;
    private int totalKills;
    private String gameId;
    private HashMap<String, Player> playerHashMap = new HashMap<>();
    private ArrayList<String> playerList = new ArrayList<>();
    private Hashtable<String, Integer> meansOfDeathHashTable = new Hashtable<>();

    Game(String id){
        this.gameId = id;
        meansOfDeath = new String[]{
                "MOD_UNKNOWN",
                "MOD_SHOTGUN",
                "MOD_GAUNTLET",
                "MOD_MACHINEGUN",
                "MOD_GRENADE",
                "MOD_GRENADE_SPLASH",
                "MOD_ROCKET",
                "MOD_ROCKET_SPLASH",
                "MOD_PLASMA",
                "MOD_PLASMA_SPLASH",
                "MOD_RAILGUN",
                "MOD_LIGHTNING",
                "MOD_BFG",
                "MOD_BFG_SPLASH",
                "MOD_WATER",
                "MOD_SLIME",
                "MOD_LAVA",
                "MOD_CRUSH",
                "MOD_TELEFRAG",
                "MOD_FALLING",
                "MOD_SUICIDE",
                "MOD_TARGET_LASER",
                "MOD_TRIGGER_HURT",
                "MOD_NAIL",
                "MOD_CHAINGUN",
                "MOD_PROXIMITY_MINE",
                "MOD_KAMIKAZE",
                "MOD_JUICED",
                "MOD_GRAPPLE"};
    }


    public void clientConnect(String id){
        //Check if player had already been connected during the game
        if (playerHashMap.get(id) == null){
            Player client = new Player(id, this);
            playerHashMap.put(client.getId(), client);
        }
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



        if(killerPlayer != null){
            killerPlayer.addKill(idKilledPlayer);
        }

        killedPlayer.computeDeath(idKillerPlayer);

        int modId = Integer.parseInt(commandTextArr[2].split(":")[0]);

        String MOD = meansOfDeath[modId];

        if(meansOfDeathHashTable.get(MOD) == null){
            meansOfDeathHashTable.put(MOD, 1);
        }else{
            int count = meansOfDeathHashTable.get(MOD);
            meansOfDeathHashTable.put(MOD, count + 1);
        }

    }


    public void printGameResult(){


        Iterator it = playerHashMap.entrySet().iterator();
        String playerKills = "KILLS\n";

        while (it.hasNext()){
            Map.Entry pair = (Map.Entry)it.next();
            Player player = (Player) pair.getValue();
            String name = player.getName();
            int kills = player.getKills();
            playerList.add(name);
            playerKills += name + " : " + kills + "\n";
        }

        Iterator itMOD = meansOfDeathHashTable.entrySet().iterator();
        String MODString = "KILLS BY MOD\n";
        while (itMOD.hasNext()){
            Map.Entry pair = (Map.Entry)itMOD.next();
            MODString = MODString  + pair.getKey() + " : " + pair.getValue() + "\n";
        }



        String header = "\nGAME NUMBER " + this.gameId + "\n";
        String breaker = "-----------------------------\n";
        String totalKills = "TOTAL KILLS: " + this.totalKills + "\n";
        String players = "PLAYERS: " + this.playerList + "\n";
        System.out.println(header + breaker + totalKills + breaker + players + breaker + playerKills + breaker + MODString);


    }


}

