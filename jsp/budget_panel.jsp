<%@ page import="database.Itinerary" %>
<%@ page import="database.Budget" %>
<%@ page import="database.Expenditure" %>
<%@ page import="database.DataManager" %>

<%@ page import="java.util.List" %>

<%
    Itinerary currentItinerary = (Itinerary) session.getAttribute("activeItinerary");
    int budgetID = currentItinerary.getBudgetID();
    Budget budget = DataManager.fetchBudget(budgetID);
    List<Expenditure> expenditures = budget.getExpenditures();
%>

<% if (budget.getID() == null || budget.getID() == 0) { %>
<div class="page-divider-header">
    <div style="display: inline-block">
        <h1><span class="glyphicon glyphicon-usd"></span> BUDGET</h1>
        <hr class="hr-title" />
    </div>
    <p><span style="font-size: 20px"><b>Create and Manage a Budget:</b></span><br />
        Create a new Budget below.<br />
        <span style="font-size: 20px"><b>Keep track of your expenses:</b></span><br />
        To manage a budget, add expenses to be subtracted from your budget.<br /><br />
    </p><br /><br />
</div>
<div class="custom-centered-pills">
    <ul class="nav nav-pills" style="padding-bottom: 20px">
        <li style="float: right">
            <a class="alert-danger" id="create-budget-pill"
               onclick="document.getElementById('createBudget-div').style.display='block';"
               data-toggle="collapse" data-parent="#accordion">
                <span class="glyphicon glyphicon-plus-sign"
                      style="position: relative; top: 2px">
                </span>
                <b>Create a Budget</b>
            </a>
        </li>
    </ul>
</div>
<div id="createBudget-div" class="panel-collapse collapse in" style="display:none">
    <div class="panel panel-danger" style="width:50%;margin:auto;">
        <div class="panel-heading" style="text-align:center;background-color:plum">
            New Budget
        </div>
        <div class="panel-body">
            <div class="row" style="margin:auto">
                <table class="table table-striped" style="width: 98%">
                    <form action="/CS2340Servlet/budget" method="POST">
                        <div class="row">
                            <div class="form-inline" style="float: left; padding-left: 50px">
                                <input type="hidden" name="itineraryID" value=<%=currentItinerary.getID()%> />
                                <input type="text" name="originalBudget" pattern="^\d*(\.\d{2}$)?" class="form-control"
                                       title="CDA Currency Format - no dollar sign and no comma(s) - cents (.##) are optional"
                                       placeholder="Enter total budget amount" style="min-width: 250px" />
                            </div>
                        </div>
                        <div class="row">
                            <div style="padding-left: 50px; padding-top: 20px;">
                                <input type="submit" class="form-control btn-primary" value="Submit"
                                       name="createBudgetButton" style="width: 150px;" />
                            </div>
                        </div>
                    </form><br />
                    <tbody>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
<% } else { %>
<div class="page-divider-header">
    <div style="display: inline-block;">
        <h1><span class="glyphicon glyphicon-usd"></span>BUDGET</h1>
        <hr class="hr-title" />
    </div>
</div>
<div class="custom-centered-pills" style="margin-bottom: 20px; margin-top: 20px">
    <ul class="nav nav-pills" style="padding-bottom: 20px">
        <li>
            <a class="alert-danger" id="addExpenditureButton"
               data-parent="#accordion" style="cursor: pointer">
                <span class="glyphicon glyphicon-plus-sign"
                      style="position: relative; top: 2px">
                </span>
                <b>Add Expenditure</b>
            </a>
        </li>
    </ul>
</div>
<div id="budgetOverview-div" class="panel-collapse">
    <div class="panel panel-danger" style="width:75%;margin:auto;">
        <div class="panel-heading" style="text-align:center;background-color:plum">
            Budget Overview
        </div>
        <div class="panel-body">
            <div class="row" style="margin:auto">
                <%  if (budget.getCurrentBudget() < 0) { %>
                <span class="alert alert-danger" style="float: right">
                    Note: You have exceeded your budget.
                </span>
                <%  } %>
                <p>Original Budget: $<%=budget.getOriginalBudget()%></p>
                <p>Current Budget:&nbsp;&nbsp;$<%=budget.getCurrentBudget()%>
                </p>
                <p>Budget Last Updated On: <%=budget.getLastUpdated()%></p>
                <form id="expenseForm" action="/CS2340Servlet/budget" method="POST" style="width: 100%">
                    <input type="hidden" name="budgetID" value=<%=budget.getID()%> />
                    <input type="hidden" name="currentBudget" value=<%=budget.getCurrentBudget()%> />
                    <input type="submit" class="form-control btn-primary" value="Submit"
                           name="saveExpenseBtn" style="width: 150px;display:none;float:right" />
                    <table class="table table-striped" style="width: 98%">
                        <div class="row">
                            <thead id="budget-table-head">
                                <tr>
                                    <th>Expense Description</th>
                                    <th>Expense Amount</th>
                                    <th>Expense Date</th>
                                </tr>
                            </thead>
                            <tbody id="expense-tbody">
                                <% for (Expenditure expense : expenditures) { %>
                                    <tr>
                                        <td style="max-width: 500px; overflow: auto"><%=expense.getDescription()%></td>
                                        <td>$<%=expense.getAmountSpent()%></td>
                                        <td><%=expense.getExpenseDate()%></td>
                                    </tr>
                                <% } %>
                            </tbody>
                        </div>
                    </table>
                </form>
            </div>
        </div>
    </div>
</div>
<% } %>

<script src="/CS2340Servlet/js/jquery.js"></script>
<script>
    var expenseFormStr =
            '<tr><td><textarea name="expenseDescription" required="required"></textarea></td>' +
            '<td><input type="text" name="amountSpent" required="required" /></td>' +
            '<td><input type="text" name="expenseDate" id="_datetimepicker" required="required" /></td></tr>';
    $('#addExpenditureButton').on("click", function(){
        $('#expense-tbody').prepend(expenseFormStr);
        $('input[name=saveExpenseBtn]').show();
        $('#_datetimepicker').datetimepicker();
    });
</script>


