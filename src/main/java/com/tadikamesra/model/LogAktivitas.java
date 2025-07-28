package com.tadikamesra.model;

import java.sql.Timestamp;

public class LogAktivitas {
    private int id;
    private int userId;
    private String aktivitas;
    private Timestamp timestamp;

    // Constructors
    public LogAktivitas() {
    }

    public LogAktivitas(int userId, String aktivitas, Timestamp timestamp) {
        this.userId = userId;
        this.aktivitas = aktivitas;
        this.timestamp = timestamp;
    }

    public LogAktivitas(int id, int userId, String aktivitas, Timestamp timestamp) {
        this.id = id;
        this.userId = userId;
        this.aktivitas = aktivitas;
        this.timestamp = timestamp;
    }

    // Getters & Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getAktivitas() {
        return aktivitas;
    }

    public void setAktivitas(String aktivitas) {
        this.aktivitas = aktivitas;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
