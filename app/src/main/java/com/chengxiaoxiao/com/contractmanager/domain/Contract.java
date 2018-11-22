package com.chengxiaoxiao.com.contractmanager.domain;

/**
 * @author XiaoXiao
 * @version $Rev$
 */

public class Contract
{
    private int Id;
    private String name;
    private String phoneNum;
    private int catalogId;
    private String department;

    public String getDepartment()
    {
        return department;
    }

    public void setDepartment(String department)
    {
        this.department = department;
    }

    public int getId()
    {
        return Id;
    }

    public void setId(int id)
    {
        Id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getPhoneNum()
    {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum)
    {
        this.phoneNum = phoneNum;
    }

    public int getCatalogId()
    {
        return catalogId;
    }

    public void setCatalogId(int catalogId)
    {
        this.catalogId = catalogId;
    }

    public String getGender()
    {
        return gender;
    }

    public void setGender(String gender)
    {
        this.gender = gender;
    }

    private String gender;
}
