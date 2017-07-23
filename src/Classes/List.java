/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import java.util.ArrayList;

/**
 *
 * @author Lenovo
 */
public class List extends ArrayList{
    
    public List()
    {
        super();
    }
    
    public List toUniqueList()
            
    { 
        int listSize = this.size();
        
        for(int i=0;i<listSize;i++)
        {
            for(int j=i+1;j<listSize;j++)
            {
                if(this.get(i).toString().equals(this.get(j).toString()))
                {
                    this.remove(j);
                    listSize = this.size();
                }
            }
        }
      return this;
    }
    
    public void add(List list)
    {
        for(Object t:list)
        {
            String temp = (String)t;
            this.add(temp);
        }
    }
}
