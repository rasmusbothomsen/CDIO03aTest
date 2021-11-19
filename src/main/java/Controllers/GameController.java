package Controllers;
import FieldHandling.Board;
import FieldHandling.Field;
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
        this.players =players;
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
    guiHandler.printText("Roll to start");
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
            guiHandler.askToRoll(playersToThrow[i].getName()+"'s Turn. Please roll");
            rolls[i]=playTurn[i].rollDice();
            guiHandler.showDiceRoll(rolls[i]);
            guiHandler.printText(playersToThrow[i].getName()+" rolled a " + rolls[i]+"!");
        }
        return rolls;
    }
    private static void rollAgain(int amoutSame){
        Player[] playersToRollAgain = new Player[amoutSame];
                    int i =0;
            switch (amoutSame){

                case 1:
                    playersToRollAgain[i]= players[4];
                    i++;
                    playersToRollAgain[i]= players[3];
                    i--;
                    break;
                case 2:
                    playersToRollAgain[i]= players[4];
                    i++;
                    playersToRollAgain[i]= players[3];
                    i++;
                    playersToRollAgain[i]= players[2];
                    i+=-2;
                    break;
                case 3:
                    playersToRollAgain[i]= players[4];
                    i++;
                    playersToRollAgain[i]= players[3];
                    i++;
                    playersToRollAgain[i]= players[2];
                    i++;
                    playersToRollAgain[i]= players[1];
                    i+=-3;
                    break;
                case 4:
                    playersToRollAgain=players;
                    break;
                default:
            }

        String rollAgainText = "";
        for (int a = 0; i < amoutSame; i++) {
        rollAgainText+=playersToRollAgain[a].getName();
            if (i<amoutSame-1){
                rollAgainText+=", ";
            }else{
                rollAgainText+=" and rolled the same, roll again";
            }
        }
        guiHandler.printText(rollAgainText);
        startOfGameThrows(playersToRollAgain);
    }
    public static void passStart(Player player){
        player.addMoney(2);
        guiHandler.movePlayer(player);
        guiHandler.printText(player.getName()+" passed start, recive 2$");


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
            guiHandler.movePlayer(players[i]);
        }
    }
    public static void playOneTurn(){
    int diceroll = playTurn[nextPlayersTurn].rollDice();
        playTurn[nextPlayersTurn].movePlayer(diceroll);
        GUIHandler.movePlayer(players[nextPlayersTurn]);
        FieldHandler.initiateField(players[nextPlayersTurn]);

    }

}
