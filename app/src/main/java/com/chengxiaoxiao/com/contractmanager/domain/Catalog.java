package com.chengxiaoxiao.com.contractmanager.domain;

/**
 * @author XiaoXiao
 * @version $Rev$
 */

public class Catalog
{

    private int Id;

    public int getId()
    {
        return Id;
    }

    public void setId(int id)
    {
        Id = id;
    }

    public String getcName()
    {
        return cName;
    }

    public void setcName(String cName)
    {
        this.cName = cName;
    }

    private String cName;
}
