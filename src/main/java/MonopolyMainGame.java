import Controllers.GameController;
import TurnHandling.Player;

public class MonopolyMainGame {
    public static void main(String[] args) {
        Player[] players = GuiStart.startGuiAndPlayerCreator();
        GameController gameController = new GameController(players);
        GameController.startOFGame();
        GameController.setUpBoard();

    }
    private static void playTurns(){
        boolean endOfGame=false;
        while(!endOfGame){
            GameController.playOneTurn();
            if(GameController.returnIfPlayerBroke()!=null){
                endOfGame=true;
            }
        }
    }
}
