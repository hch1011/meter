<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- <%@ page import="paas.mall.util.ApplicationConfig" %> --%>

<meta charset="utf-8"/>
<%
	/* java */

%>

<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
<meta name="baidu-site-verification" content="Oij4MFUJnH" />
<%--  第一个title是controller中设置的值，title_in_tiles是tails配置文件中设置的值   --%>
<c:set var="title_in_tiles"><t:getAsString name="title"/></c:set>
<title>${title}<c:if test="${not empty title and not empty title_in_tiles}"> - </c:if>${title_in_tiles}</title>

<c:if test="${not empty meta}">
    <meta name="keywords" content="${meta.keywords}"/>
    <meta name="description" content="${meta.description}"/>
</c:if>
<!-- <link rel="shortcut icon" type="image/x-icon" href="/common/images/favicon-m.ico"> -->

<t:importAttribute name="commonCSSPath" />
<c:if test='${not empty commonCSSPath}'>
    <link rel="stylesheet" href="resources/css/merge/${commonCSSPath}.merge.css">
</c:if>
