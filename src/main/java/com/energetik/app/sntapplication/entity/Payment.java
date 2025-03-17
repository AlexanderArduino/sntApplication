package com.energetik.app.sntapplication.entity;


import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "payment")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double summa; // сумма платежа
    private LocalDateTime dateOfPayment; // дата платежа
    private LocalDateTime periodFrom; // начало периода за который идет платеж
    private LocalDateTime periodTo; // окончание периода за который идет платеж
    private boolean targetPay; // оплата целевого взноса
    private boolean electricityPay; // оплата за электричество
    private boolean cash; // оплата наличкой

    @ManyToOne
    @JoinColumn(name = "gardener_id", nullable = false)
    private Gardener gardener;

    public Payment() {
    }

    public Payment(Long id, Double summa, LocalDateTime dateOfPayment,
                   LocalDateTime periodFrom, LocalDateTime periodTo,
                   boolean targetPay, boolean electricityPay, boolean cash, Gardener gardener) {
        this.id = id;
        this.summa = summa;
        this.dateOfPayment = dateOfPayment;
        this.periodFrom = periodFrom;
        this.periodTo = periodTo;
        this.targetPay = targetPay;
        this.electricityPay = electricityPay;
        this.cash = cash;
        this.gardener = gardener;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getSumma() {
        return summa;
    }

    public void setSumma(Double summa) {
        this.summa = summa;
    }

    public LocalDateTime getDateOfPayment() {
        return dateOfPayment;
    }

    public void setDateOfPayment(LocalDateTime dateOfPayment) {
        this.dateOfPayment = dateOfPayment;
    }

    public LocalDateTime getPeriodFrom() {
        return periodFrom;
    }

    public void setPeriodFrom(LocalDateTime periodFrom) {
        this.periodFrom = periodFrom;
    }

    public LocalDateTime getPeriodTo() {
        return periodTo;
    }

    public void setPeriodTo(LocalDateTime periodTo) {
        this.periodTo = periodTo;
    }

    public boolean isTargetPay() {
        return targetPay;
    }

    public void setTargetPay(boolean targetPay) {
        this.targetPay = targetPay;
    }

    public boolean isElectricityPay() {
        return electricityPay;
    }

    public void setElectricityPay(boolean electricityPay) {
        this.electricityPay = electricityPay;
    }

    public boolean isCash() {
        return cash;
    }

    public void setCash(boolean cash) {
        this.cash = cash;
    }

    public Gardener getGardener() {
        return gardener;
    }

    public void setGardener(Gardener gardener) {
        this.gardener = gardener;
    }
}
