<%-- 
    Document   : home
    Created on : 14 Mar, 2017, 5:54:48 PM
    Author     : Orling
--%>

<%@page import="com.model.vo.HeatChart"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>SmartJi</title>
        <%@include file="includes.jsp"%>

        <script>
            $(function () {
                $("#datepicker").datepicker({
                    dateFormat: 'yy-mm-dd'
                });

            });
        </script>

    </head>
    <body>
        <%@include file="nav.jsp"%> 
        <div class ="container">
            <center><h3>Latest Update: <c:out value="${latest.timestamp}"></c:out></h3></center>
                <div class="row">
                    <div class="col-lg-3 col-md-6">
                        <div class="panel panel-primary">
                            <div class="panel-heading">
                                <div class="row">
                                    <div class="col-xs-3">
                                        <i class="fa fa-thermometer-quarter fa-5x"></i>
                                    </div>
                                    <div class="col-xs-9 text-right">
                                        <div style = "font-size:1.5em">Sensor 1: <c:out value="${latest.temp1}"></c:out>c</div>
                                    <div style = "font-size:1.5em">Sensor 2: <c:out value="${latest.temp2}"></c:out>c</div>
                                    </div>
                                </div>
                            </div>
                            <div class="panel-footer">
                                <span class="pull-right">Temperature</span>
                                <div class="clearfix"></div>
                            </div>
                        </div>
                    </div>
                    <div class="col-lg-3 col-md-6">
                        <div class="panel panel-green">
                            <div class="panel-heading">
                                <div class="row">
                                    <div class="col-xs-3">
                                        <i class="fa fa-tint fa-5x"></i>
                                    </div>
                                    <div class="col-xs-9 text-right">
                                        <div style = "font-size:1.5em">Sensor 1: <c:out value="${latest.humid1}"></c:out>%</div>
                                    <div style = "font-size:1.5em">Sensor 2: <c:out value="${latest.humid2}"></c:out>%</div>
                                    </div>
                                </div>
                            </div>
                            <div class="panel-footer">
                                <span class="pull-right">Humidity</span>
                                <div class="clearfix"></div>
                            </div>
                        </div>
                    </div>
                    <div class="col-lg-3 col-md-6">
                        <div class="panel panel-yellow">
                            <div class="panel-heading">
                                <div class="row">
                                    <div class="col-xs-3">
                                        <i class="fa fa-lightbulb-o fa-5x"></i>
                                    </div>
                                    <div class="col-xs-9 text-right">
                                        <div class="huge">
                                        <c:choose>
                                            <c:when test="${latest.light==true}">
                                                On
                                            </c:when>
                                            <c:otherwise>
                                                Off
                                            </c:otherwise>
                                        </c:choose>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="panel-footer">
                            <span class="pull-right">Current Light Status</span>
                            <div class="clearfix"></div>
                        </div>
                    </div>
                </div>
                <div class="col-lg-3 col-md-6">
                    <div class="panel panel-red">
                        <div class="panel-heading">
                            <div class="row">
                                <div class="col-xs-3">
                                    <i class="fa fa-support fa-5x"></i>
                                </div>
                                <div class="col-xs-9 text-right">
                                    <div class="huge">
                                        <c:choose>
                                            <c:when test="${latest.email==true}">
                                                Alert
                                            </c:when>
                                            <c:otherwise>
                                                Good
                                            </c:otherwise>
                                        </c:choose>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="panel-footer">
                            <span class="pull-right">Current Status</span>
                            <div class="clearfix"></div>
                        </div>
                    </div>
                </div>
            </div>





            <div class="row">
                <div class="col-md-9">
                    <div class="panel panel-default">
                        <div class="panel-heading">Egg Production</div>
                        <div class="panel-body">
                            <canvas id="chart"></canvas>
                        </div>
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="panel panel-default" >
                        <div class="panel-heading">Egg production</div>
                        <div class="panel-body">
                            <form action="${pageContext.servletContext.contextPath}/updateEgg" method ="POST">
                                Date: <input  class = "form-control" type ="text" name="date" id="datepicker" ><br/>
                                Eggs: <input class = "form-control" type ="text" name="eggs"><br/>
                                <button class="btn btn-default" type ="submit">Update</button>
                            </form>
                        </div>


                    </div>
                </div>
            </div>

        </div>
        <script>
            <%
                HeatChart heats = (HeatChart) session.getAttribute("heatChart");
            %>
            var ctx = document.getElementById("chart").getContext("2d");
            var myChart = new Chart(ctx, {
                type: "line",

                data: {
                    "labels": [
            <%
                out.print(heats.convertLabels());
            %>
                    ],
                    "datasets": [{
                            "label": "Egg Production",
                            fill: false,
                            yAxisID: "y-axis-0",
                            backgroundColor: "rgba(204,255,51,0.8)",
                            borderColor: "rgba(204,255,51,0.3)",
                            pointRadius: 10,
                            pointHoverRadius: 20,
                            "data": [
            <%
                out.print(heats.convertEggProduction());
            %>
                            ]
                        },
                        {
                            "label": "Heat Index",
                            fill: false,
                            yAxisID: "y-axis-1",
                            backgroundColor: "rgba(255,204,51,0.8)",
                            borderColor: "rgba(255,204,51,0.3)",
                            pointRadius: 10,
                            pointHoverRadius: 20,
                            "data": [
            <%
                out.print(heats.converHeatIndex());
            %>
                            ]
                        }]
                },
                options: {
                    scales: {
                        yAxes: [{
                                position: "left",
                                "id": "y-axis-0"
                            }, {
                                position: "right",
                                "id": "y-axis-1"
                            }]
                    }
                }
            });

        </script>
    </body>
</html>
