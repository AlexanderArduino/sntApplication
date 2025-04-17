package com.energetik.app.sntapplication.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "conversation")
public class Conversation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private LocalDateTime date;
    private boolean is_in_call;
    private boolean is_out_call;
    private String reason;
    private String about;
    private String note;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gardener_id")
    private Gardener gardener;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public boolean isIs_in_call() {
        return is_in_call;
    }

    public void setIs_in_call(boolean is_in_call) {
        this.is_in_call = is_in_call;
    }

    public boolean isIs_out_call() {
        return is_out_call;
    }

    public void setIs_out_call(boolean is_out_call) {
        this.is_out_call = is_out_call;
    }

    public String getReasone() {
        return reason;
    }

    public void setReasone(String reason) {
        this.reason = reason;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Gardener getGardener() {
        return gardener;
    }

    public void setGardener(Gardener gardener) {
        this.gardener = gardener;
    }
}