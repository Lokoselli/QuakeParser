package com.company;

public class Player {

    private int kills = 0;
    private String name;
    private String id;
    Game game;

    Player(String id, Game game){
        this.id = id;
        this.game = game;
    }

    public void addKill(String killed){
        if(!killed.equals(this.id)){
            kills++;
        }
    }

    public void computeDeath(String killer){

        if (killer.equals("1022")){
            kills--;
        }

    }

    public int getKills() {
        return kills;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

}
