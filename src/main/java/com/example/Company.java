package com.example;
import org.apache.avro.Schema;
import org.apache.avro.specific.SpecificRecordBase;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Company extends SpecificRecordBase{
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
    public Schema getSchema() {
        // TODO Auto-generated method stub
        return null;
    }


    @Override
    public Object get(int field) {
        // TODO Auto-generated method stub
        return null;
    }


    @Override
    public void put(int field, Object value) {
        // TODO Auto-generated method stub
        
    }

}


