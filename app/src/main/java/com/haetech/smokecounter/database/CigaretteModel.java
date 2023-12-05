package com.haetech.smokecounter.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.sql.Date;

@Entity
@TypeConverters(Converters.class)
public class CigaretteModel {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "price")
    private double price;
    @ColumnInfo(name = "smoked_date")
    private Date smokedDate;

    public Date getSmokedDate() {
        return smokedDate;
    }

    public void setSmokedDate(Date smokedDate) {
        this.smokedDate = smokedDate;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(Double priceNew) {
        this.price = priceNew;
    }
}
