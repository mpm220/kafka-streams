package com.example;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Company {
    public String company_name;
    public String companyName;
    public String Founding_Member;
    public String FoundingMember;
    public int Founding_Year;
    public int FoundingYear;
    public int EmployeeCount;
    public int Employee_Count;
    public String Ticker;

    public Company(){}


    public String getCompany_name() {
        return this.company_name;
    }

    public String getCompanyName() {
        return this.companyName;
    }

    public String getFounding_Member() {
        return this.Founding_Member;
    }

    public String getFoundingMember() {
        return this.FoundingMember;
    }

    public int getFounding_Year() {
        return this.Founding_Year;
    }

    public int getFoundingYear() {
        return this.FoundingYear;
    }

    public int getEmployeeCount() {
        return this.EmployeeCount;
    }

    public int getEmployee_Count() {
        return this.Employee_Count;
    }

    public String getTicker() {
        return this.Ticker;
    }

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
