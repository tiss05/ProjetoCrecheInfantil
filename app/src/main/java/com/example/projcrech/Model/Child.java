package com.example.projcrech.Model;

public class Child {

    //private variables
    private int idChild;
    private String nameChild;
    private byte[] imageChild;

    // Empty constructor
    public Child(){
    }

    // constructor
    public Child(String nameChild,  byte[] imageChild, int idChild){
        this.nameChild = nameChild;
        this.imageChild = imageChild;
        this.idChild = idChild;
    }


    public int getIdChild() {
        return idChild;
    }

    public void setIdChild(int idChild) {
        this.idChild = idChild;
    }

    // getting name
    public String getNameChild() {
        return nameChild;
    }

    // setting name
    public void setNameChild(String nameChild){
        this.nameChild = nameChild;
    }

    // getting image
    public byte[] getImageChild() {
        return imageChild;
    }

    // setting image
    public void setImageChild(byte[]  imageChild){
        this.imageChild = imageChild;
    }

}
