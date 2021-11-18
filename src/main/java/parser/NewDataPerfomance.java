package parser;

import model.Performance;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class NewDataPerfomance implements OnNewDataHandler<ArrayList<Performance>> {

    @Override
    public void onNewData(Object sender, @NotNull ArrayList<Performance> args) {
        for (Performance performance : args) {
            System.out.println(performance);
        }
    }
}