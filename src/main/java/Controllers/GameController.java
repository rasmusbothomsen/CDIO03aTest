package Controllers;
import FieldHandling.Amusement;
import GUI.GUIHandler;
import TurnHandling.PlayTurn;
import FieldHandling.FieldHandler;
import TurnHandling.Player;

public class GameController {
    private static GUIHandler guiHandler;
    private static PlayTurn[] playTurn;
    private static Player[] players;
    private static String[] gameText;
    private static int nextPlayersTurn;

    public GameController(Player[] players) {
        GameController.players =players;
        guiHandler=new GUIHandler(players);
        playTurn= new PlayTurn[players.length];
        FieldHandler fieldHandler = new FieldHandler();
        initiatePlayturn();
    }
    private static void initiatePlayturn(){
        for(int i = 0;i<playTurn.length;i++){
            playTurn[i]=new PlayTurn(false,players[i]);
        }
    }




//alle tekst elementerne er placeholders
    public static void startOFGame(){
    GUIHandler.printText("Roll to start");
   int[] rolls = startOfGameThrows(players);
    int[][] comparedThrows= compareThrows(rolls);
    int throwIsSame=0;
        for (int i = 0; i < rolls.length-1; i++) {
            if(comparedThrows[i][0]==comparedThrows[comparedThrows.length-1][0]){
                throwIsSame++;
            }
        }
        if(throwIsSame>0) {
            rollAgain(throwIsSame);
            return;
        }
        setStartingPlayer(comparedThrows);
    }
    private static void setStartingPlayer(int[][] playersRoll){
        nextPlayersTurn=playersRoll[playersRoll.length-1][1];
    }
    private static int[] startOfGameThrows(Player[] playersToThrow){
        int[] rolls=new int[playersToThrow.length];
        for (int i = 0; i<playersToThrow.length;i++) {
            GUIHandler.askToRoll(playersToThrow[i].getName()+"'s Turn. Please roll");
            rolls[i]=playTurn[i].rollDice();
            GUIHandler.showDiceRoll(rolls[i]);
            GUIHandler.printText(playersToThrow[i].getName()+" rolled a " + rolls[i]+"!");
        }
        return rolls;
    }
    private static void rollAgain(int amoutSame){
        Player[] playersToRollAgain = new Player[amoutSame];
        for (int a = players.length-1, b=0; b < amoutSame; a--,b++) {
            playersToRollAgain[b]=players[a];
        }
        String rollAgainText = "";
        for (int a = 0, i =0; i < amoutSame; i++) {
        rollAgainText+=playersToRollAgain[a].getName();
            if (i<amoutSame-1){
                rollAgainText+=", ";
            }else{
                rollAgainText+=" and rolled the same, roll again";
            }
        }
        GUIHandler.printText(rollAgainText);
        startOfGameThrows(playersToRollAgain);
    }
    public static void passStart(Player player){
        player.addMoney(2);
        GUIHandler.movePlayer(player);
        GUIHandler.printText(player.getName()+" passed start, recive 2$");


    }
    protected static int[][] compareThrows(int[] rolls){
        int[][] playerRolls = new int[rolls.length][2];
        for (int i = 0; i < rolls.length; i++) {
            playerRolls[i][0]=rolls[i];
            playerRolls[i][1]=i;
        }
        for(int i =0;i<rolls.length-1;i++){
            int tempRoll;
            int tempPlayer;
            if(playerRolls[i][0]>playerRolls[i+1][0]){
                tempRoll=playerRolls[i+1][0];
                tempPlayer=playerRolls[i+1][1];
                playerRolls[i+1][0]=playerRolls[i][0];
                playerRolls[i+1][1]=playerRolls[i][1];
                playerRolls[i][0]=tempRoll;
                playerRolls[i][1]=tempPlayer;
            }
        }
        return playerRolls;
    }
    public static void setUpBoard(){
        for (int i = 0; i < players.length; i++) {
            players[i].setPlacementONBoard(0);
            GUIHandler.movePlayer(players[i]);
        }
    }
    public static void playOneTurn(){
    int diceroll = playTurn[nextPlayersTurn].rollDice();
        GUIHandler.askToRoll(players[nextPlayersTurn].getName()+"'s Turn. Please roll");
        GUIHandler.showDiceRoll(diceroll);
        playTurn[nextPlayersTurn].movePlayer(diceroll);
        GUIHandler.movePlayer(players[nextPlayersTurn]);
        FieldHandler.initiateField(players[nextPlayersTurn]);
        addOneToNextPlaer();

    }
    public static void playOneExtraTurn(){
        nextPlayersTurn+=-1;
        int diceroll = playTurn[nextPlayersTurn].rollDice();
        playTurn[nextPlayersTurn].movePlayer(diceroll);
        GUIHandler.movePlayer(players[nextPlayersTurn]);
        FieldHandler.initiateField(players[nextPlayersTurn]);
        addOneToNextPlaer();

    }
    public static Player returnIfPlayerBroke(){
        for (int i = 0; i < players.length; i++) {
            if(players[i].isBroke()){
                return players[i];
            }

        }
        return null;
    }
    public static Player[] comparePlayerMoney(){
        Player[] comparedPlayers = players;
        Player tempPlayer;
        for (int i = 0; i < players.length-1; i++) {
            if(comparedPlayers[i].getMoney()>comparedPlayers[i+1].getMoney()){
                tempPlayer=comparedPlayers[i+1];
                comparedPlayers[i+1]=comparedPlayers[i];
                comparedPlayers[i]=tempPlayer;

            }
        }
        return comparedPlayers;
    }
    public static void movePlayer(Player player){
        GUIHandler.movePlayer(player);
        FieldHandler.initiateField(player);
    }
    public static void getFreeField(Player player){
        Amusement amusement = (Amusement) FieldHandler.getOneField(player.getPlacementONBoard());
        int tempCost= amusement.getCost();
        amusement.setCost(0);
        FieldHandler.initiateField(player);
        amusement.setCost(tempCost);
        GUIHandler.movePlayer(player);

    }
    private static void addOneToNextPlaer(){
        nextPlayersTurn++;
        if(nextPlayersTurn>=players.length){
            nextPlayersTurn=0;
        }
    }

}
