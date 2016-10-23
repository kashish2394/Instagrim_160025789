<%-- 
    Document   : viewProfile
    Created on : Oct 23, 2016, 9:23:36 PM
    Author     : kashish
--%>

<%@page import="java.util.Iterator"%>
<%@page import="uk.ac.dundee.computing.aec.instagrim.stores.ViewProfile"%>
<%@page import="uk.ac.dundee.computing.aec.instagrim.stores.LoggedIn"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="Styles.css" />
        <title>Your Profile </title>
    </head>
    <% LoggedIn lg = (LoggedIn) session.getAttribute("LoggedIn");%>
    <body>
      
      <header>
        <h1>InstaGrim ! </h1>
        <h2>Your world in Black and White</h2>
        </header>
        
        <h3>Profile</h3>
        <p> you are currently logged in as:<b> <%=lg.getUsername() %> </b> </p>
        <% java.util.LinkedList<ViewProfile> user_profile = (java.util.LinkedList<ViewProfile>) request.getAttribute("user_profile"); %>  
       
        <%     if(user_profile == null) 
                { %>
        
        <p> Sorry we could not locate your profile. It may be that you have not created one. 
            
        <p> Would you like to create one now? <a href="create_profile.jsp"></a>
            
        <%      } 
              else   
                {  
                   Iterator<ViewProfile> iterator;
                   iterator = user_profile.iterator();
                        while (iterator.hasNext()) 
                            {
                            ViewProfile profile = (ViewProfile) iterator.next(); 
                            }
                }
        %>    
    </body>
</html>
