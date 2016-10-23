/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package uk.ac.dundee.computing.aec.instagrim.models;


import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.Statement;
import com.datastax.driver.core.querybuilder.Insert;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import uk.ac.dundee.computing.aec.instagrim.lib.AeSimpleSHA1;
import uk.ac.dundee.computing.aec.instagrim.stores.Pic;

/**
 *
 * @author Administrator
 */
public class User {
    Cluster cluster;
    public User(){
        
    }
    
    public boolean RegisterUser(String username, String Password){
        AeSimpleSHA1 sha1handler=  new AeSimpleSHA1();
        String EncodedPassword=null;
        try {
            EncodedPassword= sha1handler.SHA1(Password);
        }catch (UnsupportedEncodingException | NoSuchAlgorithmException et){
            System.out.println("Can't check your password");
            return false;
        }
        Session session = cluster.connect("instagrim");
       // PreparedStatement ps = session.prepare("insert into userprofiles (login,password,first_name,last_name,biodata) Values(?,?,?,?,?)");
  
        //We are assuming this always works.  Also a transaction would be good here !
     PreparedStatement ps = session.prepare("insert into userprofiles (login,password) Values(?,?)");
       
        BoundStatement boundStatement = new BoundStatement(ps);
        session.execute( // this is where the query is executed
                boundStatement.bind( // here you are binding the 'boundStatement'
                        username,EncodedPassword));
        //We are assuming this always works.  Also a transaction would be good here !
        
        System.out.println("Data inserted successfully: " + username);
        
       return true;    
    }
    
    
    public void createProfile(String username, String first_name, String last_name, String bio  ) {
        
        Session session = cluster.connect("instagrim");
        
        //PreparedStatement update = session.prepare("UPDATE instagrim.");
        
        QueryBuilder qb;
       
       Statement query = QueryBuilder.update("instagrim","userprofiles")
               .with(QueryBuilder.set("first_name", first_name))
                .and(QueryBuilder.set("last_name", last_name)) //are multiple columns not allowed?
               .and(QueryBuilder.set("biodata", bio)) 
               .where(QueryBuilder.eq("login", username));
       
               session.execute(query);
              System.out.println("Data inserted successfully:\n " + bio + " " + first_name + " " + last_name); 
        
   
    }
    
       public void displayProfile(String username) {
        
        Session session = cluster.connect("instagrim");
        QueryBuilder qb;
        
       Statement query = QueryBuilder.select().from("instagrim","userprofiles")       
               .where(QueryBuilder.eq("login", username));
      
        ResultSet results = session.execute(query);
        
        List<String> user_profile = new ArrayList<String>();
               
        for(Row row:results ) 
        {
            
         user_profile.add(row.getString("first_name"));
         user_profile.add(row.getString("last_name"));
         user_profile.add(row.getString("biodata"));
         
        
        }
    }
    
    
    
    public boolean IsValidUser(String username, String Password){
        AeSimpleSHA1 sha1handler=  new AeSimpleSHA1();
        String EncodedPassword=null;
        try {
            EncodedPassword= sha1handler.SHA1(Password);
        }catch (UnsupportedEncodingException | NoSuchAlgorithmException et){
            System.out.println("Can't check your password");
            return false;
        }
        Session session = cluster.connect("instagrim");
        PreparedStatement ps = session.prepare("select password from userprofiles where login =?");
        ResultSet rs = null;
        BoundStatement boundStatement = new BoundStatement(ps);
        rs = session.execute( // this is where the query is executed
                boundStatement.bind( // here you are binding the 'boundStatement'
                        username));
        if (rs.isExhausted()) {
            System.out.println("No Images returned");
            return false;
        } else {
            for (Row row : rs) {
               
                String StoredPass = row.getString("password");
                if (StoredPass.compareTo(EncodedPassword) == 0)
                    return true;
            }
        }
   
    
    return false;  
    }
       public void setCluster(Cluster cluster) {
        this.cluster = cluster;
    }

    
}
