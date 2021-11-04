package dramteatr;

import model.Article;
import model.Performance;
import parser.OnNewDataHandler;

import java.util.ArrayList;

public class NewDataDramteatr implements OnNewDataHandler<ArrayList<Performance>> {

    @Override
    public void onNewData(Object sender, ArrayList<Performance> args) {
        for (Performance performance : args) {
            System.out.println(performance);
        }
    }
}