<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>


<table class="table table-sm">
	<tr>
		<th scope="row">
			<acme:message code="developer.dashboard.form.label.total-number-of-training-modules-with-an-update-moment"/>
		</th>
		<td>
			<acme:print value="${totalNumberOfTrainingModulesWithAnUpdateMoment}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="developer.dashboard.form.label.total-number-of-training-sessions-with-a-link"/>
		</th>
		<td>
			<acme:print value="${totalNumberOfTrainingSessionsWithALink}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="developer.dashboard.form.label.average-time-of-the-training-modules"/>
		</th>
		<td>
			<acme:print value="${averageTimeOfTheTrainingModules}"/>
		</td>
	</tr>	
	<tr>
		<th scope="row">
			<acme:message code="developer.dashboard.form.label.deviation-time-of-the-training-modules"/>
		</th>
		<td>
			<acme:print value="${deviationTimeOfTheTrainingModules}"/>
		</td>
	</tr>	
	<tr>
		<th scope="row">
			<acme:message code="developer.dashboard.form.label.minimum-time-of-the-training-modules"/>
		</th>
		<td>
			<acme:print value="${minimumTimeOfTheTrainingModules}"/>
		</td>
	</tr>	
	<tr>
		<th scope="row">
			<acme:message code="developer.dashboard.form.label.maximum-time-of-the-training-modules"/>
		</th>
		<td>
			<acme:print value="${maximumTimeOfTheTrainingModules}"/>
		</td>
	</tr>
	
</table>
<acme:return/>