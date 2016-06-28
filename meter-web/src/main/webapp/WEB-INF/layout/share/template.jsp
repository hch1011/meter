<%@ page  contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <t:insertAttribute name="head"/> 
        <link rel="stylesheet" href="/meter/resources/css/merge/<t:getAsString name="cssPath" />.merge.css">   
    </head>
    <body>
        <div class="main">
            <div class="main-wrap">
            	<t:insertAttribute name="top" />
            	<div class="content-wrap">
	                <t:insertAttribute name="nav" />
	            <div class="line"></div>
	            <div class="content">
	                <t:insertAttribute name="content" />
	            </div>
                </div>
            </div>
        </div>
        <t:insertAttribute name="footer" />
        <t:importAttribute name="commonJSPath" />
        <c:if test='${not empty commonJSPath}'>
            <script src="/meter/resources/js/merge/${commonJSPath}.merge.js"></script>
        </c:if>
        <t:importAttribute name="jsPath" />
        <c:if test='${not empty jsPath}'>
            <script src="/meter/resources/js/merge/${jsPath}.merge.js"></script>
        </c:if>
        
    </body>
    
</html>