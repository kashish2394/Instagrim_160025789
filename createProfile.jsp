<%-- 
    Document   : createProfile
    Created on : Oct 22, 2016, 2:36:29 PM
    Author     : kashish
--%>

<%@page import="uk.ac.dundee.computing.aec.instagrim.stores.LoggedIn"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="Styles.css" />
        <title>Create Profile</title>
    </head>
     
    <% LoggedIn lg = (LoggedIn) session.getAttribute("LoggedIn");%>
    
   
    <body>
    <h3>Create Profile</h3>
    
    <p> you are currently logged in as: <%=lg.getUsername() %> </p>
   
  <div id="create_profile">
    <form method="POST"  action="Profile">
        
       <p>  First Name <input type="text" name="first_name" style="width: 300px;" style="height:500px;"><br/> <br/></p>
       <p>  Last Name <input type="text" name="last_name" style="width: 300px;" style="height:500px;"><br/> <br/></p>
       
        <p>Say something about yourself: </p>
                
                <textarea name="bio" cols="40" rows="5"></textarea>
                
                <br/>
                <input type="submit" value="Create Profile">
       
    </form>
  </div>
    
    <footer id="footer_nav">
            <ul>
                <li class="footer"><a href="/Instagrim">Home</a></li>
            </ul>
        </footer>
  </body>
</html>
