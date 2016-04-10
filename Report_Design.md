#Reports Design

1. [Report: Tickets by Category](#report-tickets-by-category#)
2. [Report: Ticket status by admin (assigned_to)](#report-ticket-status-by-admin-assigned_to)


###Report: Tickets by Category
category|count
--------|-----
demo    | 3
vm      | 4

![Pie Chart](https://raw.githubusercontent.com/tim-hoff/csra-lab-mgnt-system/features/report/public/images/piechart.png "Example Pie Chart")

[Pie Chart Docs](http://www.chartjs.org/docs/#doughnut-pie-chart)

Show tickets category count as a table and pie chart.
*note: Side by side on desktop*

**first pass**:  All time

**second pass**: Filter the list of tickets to tickets within the last month

**third pass**: make the filter method take in any number of months (*n*)

**fourth pass**: create a dropdown, or show a button group to display tickets by category for: *1 month, 3 months, 6 months, 1 year, all time*


**Route** `/tickets/reports/1`

**View file location** `/views/tickets/reports/report_1.scala.html`

**Page Name** `Report: Tickets by Category`


___


###Report: Ticket status by admin (assigned_to)
admin_name|low|medium|high|urgent|total
----------|---|------|----|------|------
bran      | 3 |  1   | 0  |  0   |  4   
tim       | 4 |  0   | 3  |  2   |  9   
abhaya    | 3 |  1   | 2  |  0   |  6   
total     | 10|  2   | 5  |  2   |  19

![Bar Chart](https://raw.githubusercontent.com/tim-hoff/csra-lab-mgnt-system/features/report/public/images/barchart.png "Example Bar Chart")

[Bar Chart Docs](http://www.chartjs.org/docs/#bar-chart)

Create a bar for each category for each admin, the color for each category should be of the same type should be consistent (*i.e. all low bars should be green, medium should be yellow, high should be urgent, and urgent should be red*)

**first pass**: All tickets

**second pass**: Only active tickets

**third pass** : One chart for active tickets, and one for Solved

**fourth pass**: create a dropdown, or show a button group to display by status for: *1 month, 3 months, 6 months, 1 year, All time*

**Route** `/tickets/reports/2`

**View file location** `/views/tickets/reports/report_2.scala.html`

**Page Name** `Report: Ticket Status by Admin` *maybe something else?*

---
