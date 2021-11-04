package parser;

import model.Performance;

import java.util.ArrayList;

public class NewDataPerfomance implements OnNewDataHandler<ArrayList<Performance>> {

    @Override
    public void onNewData(Object sender, ArrayList<Performance> args) {
        for (Performance performance : args) {
            System.out.println(performance);
        }
    }
}