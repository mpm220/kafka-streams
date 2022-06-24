package com.example;

public class CompanySA extends Company{
    public String company_name;
    public String Founding_Member;
    public int Founding_Year;
    public int EmployeeCount;
    public String Ticker;

    public String getCompany_name() {
        return this.company_name;
    }

    public String getFounding_Member() {
        return this.Founding_Member;
    }

    public int getFounding_Year() {
        return this.Founding_Year;
    }

    public int getEmployeeCount() {
        return this.EmployeeCount;
    }

    public String getTicker() {
        return this.Ticker;
    }

    public CompanySA(){}


    @Override
    public String toString() {
        return "{" +
            " company_name='" + getCompany_name() + "'" +
            ", Founding_Member='" + getFounding_Member() + "'" +
            ", Founding_Year='" + getFounding_Year() + "'" +
            ", EmployeeCount='" + getEmployeeCount() + "'" +
            ", Ticker='" + getTicker() + "'" +
            "}";
    }


    
}
