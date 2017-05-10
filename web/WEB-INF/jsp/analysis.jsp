<%-- 
    Document   : data
    Created on : 24 Mar, 2017, 3:08:29 PM
    Author     : Orling
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="com.model.vo.DegreeChart"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Analysis</title>
        <%@include file="includes.jsp"%>
    </head>
    <body>
        <%@include file="nav.jsp"%> 

        <div class="container">

            <div class="row">
                <div class="col-md-4">
                    <div class="panel panel-primary">
                        <div class="panel-heading">
                            <div class="row">
                                <div class="col-xs-3">
                                    <i class="fa fa-thermometer-quarter fa-5x"></i>
                                </div>
                                <div class="col-xs-9 text-right">
                                    <div class="huge"><c:out value="${setting.current}"></c:out></div>
                                        <div>Current Setting</div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-4">
                        <div class="panel panel-green">
                            <div class="panel-heading">
                                <div class="row">
                                    <div class="col-xs-3">
                                        <i class="fa fa-tint fa-5x"></i>
                                    </div>
                                    <div class="col-xs-9 text-right">
                                        <div class="huge"><c:out value="${setting.recommended}"></c:out></div>
                                        <div>Recommended Setting</div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-4">
                        <div class="panel panel-default" >
                            <div class="panel-heading">Update Setting</div>
                            <div class="panel-body">
                                <form action="${pageContext.servletContext.contextPath}/updateSetting" method ="post">
                                Heat Index: <input type ="text" name="humid">
                                <button class="btn btn-default" type ="submit">Update</button>
                            </form>

                        </div>

                    </div>
                </div>
            </div>




            <div class="row">
                <div class="col-md-6">
                    <div class="panel panel-default">
                        <div class="panel-heading">Temperature Charts</div>
                        <div class="panel-body">
                            <canvas id="temp" style="width: 75%;"></canvas>
                        </div>
                    </div>
                </div>
                <div class="col-md-6">
                    <div class="panel panel-default">
                        <div class="panel-heading">Humidity Charts</div>
                        <div class="panel-body">
                            <canvas id="humid" style="width: 75%;"></canvas>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <script>
            <%
                ArrayList<DegreeChart> charts = (ArrayList<DegreeChart>) session.getAttribute("charts");
                DegreeChart temp = charts.get(0);
                DegreeChart humid = charts.get(1);
            %>
            var tempLabel = [<% out.print(temp.convertLabels());  %>];
            var humidLabel = [<% out.print(humid.convertLabels());  %>];


            var tempChartData = {
                labels: tempLabel,
                datasets: [{
                        label: 'Sensor1',
                        backgroundColor: "rgba(51, 102, 255,0.3)",
                        data: [<% out.print(temp.convertSensor1());  %>]
                    }, {
                        label: 'Sensor2',
                        backgroundColor: "rgba(51, 102, 255,0.8)",
                        data: [<% out.print(temp.convertSensor2());%>]
                    }]

            };
            var humidChartData = {
                labels: humidLabel,
                datasets: [{
                        label: 'Sensor1',
                        backgroundColor: "rgba(51, 204, 51,0.8)",
                        data: [<% out.print(humid.convertSensor1());  %>]
                    }, {
                        label: 'Sensor2',
                        backgroundColor: "rgba(0, 255, 153,0.3)",
                        data: [<% out.print(humid.convertSensor2());%>]
                    }]

            };

            window.onload = function () {
                var ctx = document.getElementById("temp").getContext("2d");
                var ctx1 = document.getElementById("humid").getContext("2d");
                window.myBar = new Chart(ctx, {
                    type: 'line',
                    data: tempChartData,
                });
                window.myBar = new Chart(ctx1, {
                    type: 'line',
                    data: humidChartData,
                    options: {
                        elements: {
                            rectangle: {
                                borderWidth: 2,
                                borderColor: 'rgb(0, 255, 0)',
                                borderSkipped: 'bottom'
                            }
                        },
                        responsive: true,
                        legend: {
                            position: 'top',
                        },
                        title: {
                            display: true,
                            text: 'Humidity Chart'
                        }
                    }
                });

            };

        </script>
    </body>
</html>
