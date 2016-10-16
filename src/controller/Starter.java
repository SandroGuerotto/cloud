package controller;

/**
 * @author          :   Sandro Guerotto
 * Created          :   27.09.2016
 * Project          :   cloud
 * Package          :   controller
 * @version         :   1.0
 * LastUpdated      :
 * Description      :   Starter class für das Programm
 */
public class Starter {

    public static void main(String[] args){
        Controller controller = new Controller(args);
        controller.start();
    }
}
