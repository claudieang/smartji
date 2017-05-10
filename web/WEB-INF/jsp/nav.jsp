<%-- 
    Document   : header
    Created on : Aug 24, 2016, 5:02:56 PM
    Author     : Orling
--%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<style>
    body{
        margin-top:10%; 
    }
    .popover {
        z-index: 9999; /* A value higher than 1010 that solves the problem */
    }
</style>
<!DOCTYPE html>
<nav class="navbar navbar-default navbar-fixed-top" role="navigation">
    <div class="container">
        <div class="navbar-header page-scroll">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-ex1-collapse">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand page-scroll" href="${pageContext.servletContext.contextPath}/home">SmartJi</a>
        </div>

        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse navbar-ex1-collapse">
            <ul class="nav navbar-nav pull-right">
                <!-- Hidden li included to remove active class from about link when scrolled up past about section -->
                <li class="hidden">
                    <a class="page-scroll" href="${pageContext.servletContext.contextPath}/home">Home</a>
                </li>
                <li>
                    <a class="page-scroll" href="${pageContext.servletContext.contextPath}/analysis">Analysis</a>
                </li>
            </ul>
        </div>
    </div>
</nav>