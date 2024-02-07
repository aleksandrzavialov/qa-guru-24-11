package model;

import java.util.List;

public class TVSeries {
    private String name;
    private int seasons;
    private int firstYear;
    private int lastYear;
    private List<Actor> actors;

    private float rating;

    public String getName(){
        return this.name;
    }

    public int getSeasons(){
        return this.seasons;
    }

    public int getFirstYear(){
        return this.firstYear;
    }

    public int getLastYear(){
        return this.lastYear;
    }

    public List<Actor> getActors(){
        return this.actors;
    }

    public float getRating(){
        return this.rating;
    }

}
