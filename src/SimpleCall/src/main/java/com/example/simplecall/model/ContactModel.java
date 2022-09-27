package com.example.simplecall.model;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class ContactModel implements Serializable {

    // Information about a contact
    private long id;
    private String name;
    private String phone;
    private String photo;
    private Integer isStarred;

    public ContactModel(Long id, String name, String phone, String photo, Integer isStarred){
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.photo = photo;
        this.isStarred = isStarred;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName(){
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone(){
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhoto() {
        return this.photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Integer getIsStarred() {
        return this.isStarred;
    }

    public void setIsStarred(Integer isStarred) {
        this.isStarred = isStarred;
    }

    @NonNull
    public String toString(){
        return "Información del contacto:" +
                "\nId: " + this.id +
                "\nNombre: " + this.name +
                "\nNumero: " + this.phone +
                "\nIs starred: " + this.isStarred + "\n";
    }

}
