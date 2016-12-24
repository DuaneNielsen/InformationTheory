<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="channel.*"%>
<%@ page import="java.util.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<script type="text/javascript"
	src="https://www.gstatic.com/charts/loader.js"></script>
<script type="text/javascript">
	google.charts.load("current", {
		packages : [ "corechart" ]
	});
	google.charts.setOnLoadCallback(drawChart);
	function drawChart() {

		var data = new google.visualization.DataTable();

		data.addColumn('number', 'error rate');

		data.addRows(100);
<%Driver driver = new Driver();
			driver.channel = new BinaryChannel(0.1);
			driver.encoder = new TriplicateEncoder();
			driver.decoder = new TriplicateDecoder();

			List<ResultSet> results = new ArrayList<ResultSet>();
			List<Double> errorRate = new ArrayList<Double>();
			List<Double> rate = new ArrayList<Double>();

			int i = 0;
			while (i < 100) {

				ResultSet resultset = driver.run(10, 10);
				resultset.computeStats();
				results.add(resultset);
				errorRate.add(resultset.errorRate);
				rate.add(resultset.rate);%>
	data.setCell(
<%=i%>
	, 0,
<%=resultset.errorRate%>
	);
<%i++;
			}%>
	var options = {
			title : 'Errors per trial using Triplicate Encoding (100 trials)',
			legend : {
				position : 'none'
			},
		};

		var chart = new google.visualization.Histogram(document
				.getElementById('chart_div'));
		chart.draw(data, options);
	}
</script>
</head>

<body>


	<div id="chart_div" style="width: 900px; height: 500px;"></div>

</body>
</html>