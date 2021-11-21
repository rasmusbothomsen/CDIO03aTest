package FieldHandling;

import Controllers.Bank;
import GUI.GUIHandler;
import Objects.PlayerCreators;
import TurnHandling.Player;
import org.junit.jupiter.api.Test;

class FieldHandlerTest {
    @Test
    void chanceCardTest(){
        Player[] players = PlayerCreators.createPlayers();
        Player somo = new Player("tseter",2);
        Bank bank = new Bank(players);
        somo.setPlacementONBoard(3);

        GUIHandler guiHandler = new GUIHandler(players);
        FieldHandler fieldHandler = new FieldHandler();
        FieldHandler.initiateField(somo);
        boolean testagain = GUIHandler.askYesOrNo("test again?","ye","no");
        while(testagain){
            testagain = GUIHandler.askYesOrNo("test again?","ye","no");
            somo.setPlacementONBoard(3);
            FieldHandler.initiateField(somo);
        }
    }

}