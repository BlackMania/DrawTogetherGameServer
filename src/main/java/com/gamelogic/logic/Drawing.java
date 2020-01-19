package com.gamelogic.logic;

import java.util.ArrayList;
import java.util.List;

public class Drawing {
    private String word;
    private List<DrawCoordinate> coordinates;

    public Drawing(String word) {
        this.word = word;
        this.coordinates = new ArrayList<DrawCoordinate>();
    }

    public List<DrawCoordinate> getCoordinates() {
        return coordinates;
    }

    public String getWord() {
        return word;
    }

    public void addCoordinate(DrawCoordinate coordinate)
    {
        coordinates.add(coordinate);
    }

}
