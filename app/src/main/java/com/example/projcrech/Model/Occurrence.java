package com.example.projcrech.Model;

import com.example.projcrech.Event;

import java.time.LocalDate;
import java.util.ArrayList;

public class Occurrence {

    public static ArrayList<Occurrence> eventsList = new ArrayList<>();

    public static ArrayList<Occurrence> eventsForDate(LocalDate date)
    {
        ArrayList<Occurrence> events = new ArrayList<>();

        for(Occurrence event : eventsList)
        {
            if(event.getDate().equals(date))
                events.add(event);
        }

        return events;
    }

   
    private int idOcc;
    private String nameOcc;
    private String contentOcc;
    private String dateOcc;
    private String dataOcc;
    private LocalDate date;
    //private String nameChild;


    public Occurrence(String nameOcc , String contentOcc, String dateOcc, LocalDate date, int idOcc){
        this.nameOcc = nameOcc;
        this.contentOcc = contentOcc;
        this.dateOcc = dateOcc;
        //this.nameChild = nameChild;
        this.date = date;
        this.idOcc = idOcc;
    }

    public int getIdOcc() {
        return idOcc;
    }

    public void setIdOcc(int id) {
        this.idOcc = idOcc;
    }

    public String getNameOcc() {
        return nameOcc;
    }

    public void setNameOcc(String nameOcc) {
        this.nameOcc = nameOcc;
    }

    public String getContentOcc() {
        return contentOcc;
    }

    public void setContentOcc(String contentOcc) {
        this.contentOcc = contentOcc;
    }


    public String getDateOcc() {
        return dateOcc;
    }

    public void setDateOcc(String dateOcc) {
        this.dateOcc = dateOcc;
    }

    public LocalDate getDate()
    {
        return date;
    }

    public void setDate(LocalDate date)
    {
        this.date = date;
    }

    // getting name
    /*public String getNameChild() {
        return nameChild;
    }

    // setting name
    public void setNameChild(String nameChild){
        this.nameChild = nameChild;
    }*/
}
