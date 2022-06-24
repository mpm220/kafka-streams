package com.example;

public class CompanySB extends Company{
    public String companyName;
    public String FoundingMember;
    public int FoundingYear;
    public int Employee_Count;
    public String Ticker;

    public CompanySB(){}

    public String getCompanyName() {
        return this.companyName;
    }

    public String getFoundingMember() {
        return this.FoundingMember;
    }

    public int getFoundingYear() {
        return this.FoundingYear;
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
            " companyName='" + getCompanyName() + "'" +
            ", FoundingMember='" + getFoundingMember() + "'" +
            ", FoundingYear='" + getFoundingYear() + "'" +
            ", Employee_Count='" + getEmployee_Count() + "'" +
            ", Ticker='" + getTicker() + "'" +
            "}";
    }




   

}
