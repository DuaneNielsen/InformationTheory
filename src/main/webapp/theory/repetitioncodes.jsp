<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="theory.*"%>
<html>
<head>
<script type="text/javascript"
	src="https://www.gstatic.com/charts/loader.js"></script>
<script type="text/javascript">
	google.charts.load('current', {
		'packages' : [ 'corechart' ]
	});
	google.charts.setOnLoadCallback(drawChart);
	function drawChart() {
		var data = new google.visualization.DataTable();

		data.addColumn('number', 'error rate');
		data.addColumn('number', 'rate');

		data.addRows(7);
		
<%RepetitionCodeModel repetitionCodeModel = new RepetitionCodeModel();

			for (int i = 1; i <= 7; i++) {
				int column = i -1;
				double errorRate = repetitionCodeModel.probabilityOfError(0.1, i);
				double rate = (double) 1 / (double) i;%>
				
				data.setCell(
						<%=column%>
							, 0,
						<%=rate%>
							);
				
				data.setCell(
						<%=column%>
							, 1,
						<%=errorRate%>
							);
				

				
<%}%>
	var options = {
			title : 'Repetition Codes: Error vs. Rate',
			hAxis : {
				title : 'Transmission Efficiency',
				minValue : 0,
				maxValue : 1
			},
			vAxis : {
				title : 'Error Rate',
				minValue : 0,
				maxValue : 0.3
			},
			legend : 'none'
		};

		var chart = new google.visualization.ScatterChart(document
				.getElementById('chart_div'));

		chart.draw(data, options);
	}
</script>
</head>
<body>
	<div id="chart_div" style="width: 900px; height: 500px;"></div>
	
	<p>An interesting observation is that a repetition code with 2 repetititions actually INCREASES your error rate, while halving your throughput! </p>> 
	<p>This makes sense, because flipping a repetition of 2 is more likely, but it only takes 1 flip to make it impossible to determine what the original signal was.  (you do know it was flipped though!)</p>
	
</body>
</html>