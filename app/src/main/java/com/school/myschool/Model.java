package com.school.myschool;

import android.graphics.Bitmap;

public class Model {
    String name,id;
    byte[] picture, identification, residence, tom, certificate, document;

//    public Model(String name,String id, Bitmap picture, Bitmap identification, Bitmap residence, Bitmap tom, Bitmap certificate, Bitmap document) {
//        this.name = name;
//        this.id = id;
//        this.picture = picture;
//        this.identification = identification;
//        this.residence = residence;
//        this.tom = tom;
//        this.certificate = certificate;
//        this.document = document;
//    }

    public Model(String name,String id) {
        this.name = name;
        this.id = id;
    }

    public Model(String id,String name , byte[] picture, byte[] identification, byte[] residence, byte[] tom, byte[] certificate, byte[] document) {
        this.id = id;
        this.name = name;
        this.picture = picture;
        this.identification = identification;
        this.residence = residence;
        this.tom = tom;
        this.certificate = certificate;
        this.document = document;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    public byte[] getIdentification() {
        return identification;
    }

    public void setIdentification(byte[] identification) {
        this.identification = identification;
    }

    public byte[] getResidence() {
        return residence;
    }

    public void setResidence(byte[] residence) {
        this.residence = residence;
    }

    public byte[] getTom() {
        return tom;
    }

    public void setTom(byte[] tom) {
        this.tom = tom;
    }

    public byte[] getCertificate() {
        return certificate;
    }

    public void setCertificate(byte[] certificate) {
        this.certificate = certificate;
    }

    public byte[] getDocument() {
        return document;
    }

    public void setDocument(byte[] document) {
        this.document = document;
    }
//    public Bitmap getPicture() {
//        return picture;
//    }
//
//    public void setPicture(Bitmap picture) {
//        this.picture = picture;
//    }
//
//    public Bitmap getIdentification() {
//        return identification;
//    }
//
//    public void setIdentification(Bitmap identification) {
//        this.identification = identification;
//    }
//
//    public Bitmap getResidence() {
//        return residence;
//    }
//
//    public void setResidence(Bitmap residence) {
//        this.residence = residence;
//    }
//
//    public Bitmap getTom() {
//        return tom;
//    }
//
//    public void setTom(Bitmap tom) {
//        this.tom = tom;
//    }
//
//    public Bitmap getCertificate() {
//        return certificate;
//    }
//
//    public void setCertificate(Bitmap certificate) {
//        this.certificate = certificate;
//    }
//
//    public Bitmap getDocument() {
//        return document;
//    }
//
//    public void setDocument(Bitmap document) {
//        this.document = document;
//    }
}