package Classes;

import java.util.ArrayList;
import Classes.List;
import java.util.Collections;
import javax.swing.JOptionPane;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 *
 * @author Anas Abdullah Yousafzai
 * @description Feel Free to Modify and Use
 */
public class SearchService {
    
    private String query;
    private static SearchService instance=null;
    private String lastLink="";
    //Properties
    private String desc;
    private List gigTags;
    private String sellerName;
    private String gigName;
    private SearchService()
    {
        System.setProperty("webdriver.chrome.driver","chromedriver.exe");
    }
    
    private SearchService(String query)
    {
       
    }
    
    public static SearchService getInstance()
    {
        if(instance==null)
        {
            instance=new SearchService();
            return instance;
        }
        else
        {
            return instance;
        }
    }
    
    public void getDetails(String link)
    {
       //Find Description
            lastLink = link;
            WebDriver web = new ChromeDriver();
            web.get(link);//Open Link
            this.desc = web.findElement(By.className("gig-main-desc")).getText();
       //Find Tags
            ArrayList<WebElement> gigtags = (ArrayList<WebElement>) web.findElements(By.className("gig-tag"));
            List l = new List();
            
            for(int i=0;i<gigtags.size();i++)
            {
                l.add(gigtags.get(i).getText());
            }
            this.gigTags = l;
        //Find Gig Name
            this.gigName = web.findElement(By.className("gig-title")).getText();
        //Find Seller Name
            String[] temp = link.split("/");
            this.sellerName = temp[3];
        //Quit Browser    
            web.quit(); 
    }
    
    public String getGigDescription()
    {
        return this.desc;
    }
    
    public List getGigTags()
    {
        
           return this.gigTags;
    }
    
    public void gettopThreeTags(List list)
    {
        Collections.sort(list);
        int listSize = list.size();
        ArrayList l = new ArrayList();
        ArrayList temp = new ArrayList();
        for(int i=0;i<listSize;i++)
        {
            if(temp.contains(l.get(i)))
            {
              continue;
            }
            l.add(1);
            for(int j=i+1;j<listSize;j++)
            {
                
                boolean status = (list.get(i).toString()).equals(list.get(j).toString());
                if(status==true)
                {
                    l.set(i,((int)l.get(i)+1));
                    list.remove(j);
                    listSize = list.size();
                }
            }
            if(i==listSize-1)
            {
                break;
            }
        }
        System.out.println(l);
    }
    
    public String getSellerName(String link)
    {
           return this.sellerName;
    }
    
    public String getGigName()
    {
        return gigName;
    }
    
    public void setSearchQuery(String query)
    {
        this.query = query; 
    }
    
    public void checkPageValidity(WebDriver w)
    {
       boolean status = w.getPageSource().contains("Blocked");
       if(status==true)
       {
           JOptionPane.showMessageDialog(null,"Couldnt Scrape.\nThere is security system in place.\nTry Some other Keyword");
       }
    }
    
    public List search()
    {
       
           WebDriver w = new ChromeDriver();
           
           w.get("http://www.fiverr.com");
           
           checkPageValidity(w);
           
           w.findElement(By.className("js-search-input")).sendKeys(query);
           
           w.findElement(By.className("js-search-submit")).click();
           
           try
           {
               Thread.sleep(2000);
           
               checkPageValidity(w);
            ArrayList<WebElement> foundElements = (ArrayList<WebElement>) w.findElements(By.className("gig-link-main"));
            ArrayList<WebElement> foundSellers = (ArrayList<WebElement>) w.findElements(By.className("seller-name"));
            
            List links = new List();
           // List seller = new List();
            
               System.out.println("Gigs Size:"+foundElements.size());
               System.out.println("Seller Size:"+foundSellers.size());
               
            for(int i=0;i<foundElements.size();i++)
            {
                   String l  = foundElements.get(i).getAttribute("href");
                   String n = foundElements.get(i).getText();
                   links.add(l);
           //        seller.add(n);
            }
            
               System.out.println("All Links Scraped Successfully");
               System.out.println("Lets See Them \n\n");
               
               w.quit();//Exit Browser
               
               return links;
           }
           catch(Exception e)
           {
               System.out.println(e.getMessage());
               return null;
           }
        }
    }
