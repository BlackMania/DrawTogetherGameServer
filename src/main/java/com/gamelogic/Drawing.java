package com.gamelogic;

import java.util.ArrayList;
import java.util.List;

public class Drawing {
    private String word;
    private List<DrawCoordinates> coordinates;

    public Drawing() {
        word = "";
        this.coordinates = new ArrayList<DrawCoordinates>();
    }

    public void addCoordinate(DrawCoordinates coordinate)
    {
        coordinates.add(coordinate);
    }
}
