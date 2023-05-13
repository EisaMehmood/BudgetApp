package com.example.omymoney.Models;

public class model_history {


    public model_history(String expense_Name, String amount, String date, String note, String day, String month, String year, String id) {
        Expense_Name = expense_Name;
        Amount = amount;
        Date = date;
        Note = note;
        Day = day;
        Month = month;
        Year = year;
        this.id = id;
    }

    String Expense_Name,Amount,Date,Note,Day,Month,Year,id;

    public String getExpense_Name() {
        return Expense_Name;
    }

    public void setExpense_Name(String expense_Name) {
        Expense_Name = expense_Name;
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getNote() {
        return Note;
    }

    public void setNote(String note) {
        Note = note;
    }

    public String getDay() {
        return Day;
    }

    public void setDay(String day) {
        Day = day;
    }

    public String getMonth() {
        return Month;
    }

    public void setMonth(String month) {
        Month = month;
    }

    public String getYear() {
        return Year;
    }

    public void setYear(String year) {
        Year = year;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public model_history() {

    }
}
