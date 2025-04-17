package com.energetik.app.sntapplication.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Debtors {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private LocalDateTime on_date;
    private Double balance_member_pay;
    private Double balance_electricity_pay;
    private Boolean to_exclusion;
    private Boolean exclude;
    private String note;

    @OneToOne
    @JoinColumn(name = "gardener_id")
    private Gardener gardener;


    public Gardener getGardener() {
        return gardener;
    }

    public void setGardener(Gardener gardener) {
        this.gardener = gardener;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getOn_date() {
        return on_date;
    }

    public void setOn_date(LocalDateTime on_date) {
        this.on_date = on_date;
    }

    public Double getBalance_member_pay() {
        return balance_member_pay;
    }

    public void setBalance_member_pay(Double balance_member_pay) {
        this.balance_member_pay = balance_member_pay;
    }

    public Double getBalance_electricity_pay() {
        return balance_electricity_pay;
    }

    public void setBalance_electricity_pay(Double balance_electricity_pay) {
        this.balance_electricity_pay = balance_electricity_pay;
    }

    public Boolean getTo_exclusion() {
        return to_exclusion;
    }

    public void setTo_exclusion(Boolean to_exclusion) {
        this.to_exclusion = to_exclusion;
    }

    public Boolean getExclude() {
        return exclude;
    }

    public void setExclude(Boolean exclude) {
        this.exclude = exclude;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
