package com.example.realtimedatabase_java;
public class Modul {
    private String id;
    private String name;
    private String nim;

    public Modul(String id, String name, String nim) {
        this.id = id;
        this.name = name;
        this.nim = nim;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNim() {
        return nim;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }
}
