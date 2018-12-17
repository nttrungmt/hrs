<!DOCTYPE html>
﻿<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.List" %>

<%--@elvariable id="cassandra_info" type="usm.hrs.model.CassandraInfo"--%>
<%--@elvariable id="java_version" type="java.lang.String"--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en-us">
<head>
    <title>Hotel Reservation Website</title>
    <link rel="shortcut icon" href="images/favicon.png"/>    
        <script src="/js/bootstrap.min.js"></script>
        <script src="/js/jquery.min.js"></script>
        <script src="/js/tpcc.js"></script>

        <link href="/css/tpcc.css" rel="stylesheet">
        <link href="/css/bootstrap.min.css" rel="stylesheet">
        <link href="/css/bootstrap-responsive.min.css" rel="stylesheet">
</head>
<body>
	<header align=center >
		<h1>Welcome to Hotel Reservation Website</h1>
		<img class="dim" src="images/favicon.png"/>
		Java version: ${java_version} <br/>
		Cassandra version: ${cassandra_info.cassandraVersion} <br/>
		Cluster name: ${cassandra_info.clusterName}
	</header>
    
    <div class="row-fluid">
        <div class="table-responsive">
        	<form class="form-horizontal" name="hrs_searchform" action="hotels" method="post" onsubmit="return validateForm(event);">
              <table class="table table-bordered table-striped table-highlight" align=center width="600px" style="width: 600px; " border="">
           		<tr><td style="width: 200px; height: 31px; ">Location:</td>
           			<td style=""><input class="form-control" type="text" name="Location" style="width: 361px; " value="${param.Location}" pattern="\w+"> </td>
           		</tr>
           		<tr><td style="width: 200px; height: 31px; ">Checkin Date:</td>
            		<td><input class="form-control" type="text" name="CheckinDate" style="width: 363px; " value="${param.CheckinDate}" pattern="\d+"></td>	
				</tr>
           		<tr><td style="width: 200px; height: 31px; ">Checkout Date:</td>
           			<td><input class="form-control" type="text" name="CheckoutDate" style="width: 364px; " value="${param.CheckoutDate}" pattern="\d+"></td>
           		</tr>
                <tr><td style="width: 200px; height: 31px; ">Number of Guests:</td>
           			<td><input class="form-control" type="text" name="NoGuests" style="width: 364px; " value="${param.NoGuests}" pattern="\d+"></td>
           		</tr>
                <tr><td style="width: 200px; height: 31px; ">Number of Rooms:</td>
           			<td><input class="form-control" type="text" name="NoRooms" style="width: 364px; " value="${param.NoRooms}" pattern="\d+"></td>
           		</tr>
            </table>            
            <br/>
            
			<p/>		
			<div align=center><button class="btn btn-primary">Search</button></div>
			<p/>
			<div align=center>
			<%List<String> lstErrors = (List<String>) request.getAttribute("errors");
			if(lstErrors != null) {
			%>
			<textarea readonly class="form-control" style="width: 800px;" align=center>
				<%= lstErrors.toString()%> 
			</textarea>
			<%}%>
			</div>
		  </form>
		</div>
      </div>
      <hr>
      <div class="footer">
        <p align=center>&copy; USM 2018</p>
      </div>
</body>
</html>
