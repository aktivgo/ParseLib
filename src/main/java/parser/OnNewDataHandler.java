package parser;

import java.io.IOException;

public interface OnNewDataHandler<T> {
    void onNewData(Object sender, T e) throws IOException;
}