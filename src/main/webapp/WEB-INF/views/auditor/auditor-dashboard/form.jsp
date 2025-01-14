<%--
- form.jsp
-
- Copyright (C) 2012-2024 Rafael Corchuelo.
-
- In keeping with the traditional purpose of furthering education and research, it is
- the policy of the copyright owner to permit non-commercial use and redistribution of
- this software. It has been tested carefully, but it is not guaranteed for any particular
- purposes.  The copyright owner does not offer any warranties or representations, nor do
- they accept any liabilities with respect to them.
--%>

<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:input-integer code="auditor.dashboard.form.label.numTotalCodeAudits" path="numTotalCodeAudits" readonly="true"/>
	<acme:input-integer code="auditor.dashboard.form.label.totalNumCodeAuditsTypeStatic" path="totalNumCodeAuditsTypeStatic" readonly="true"/>
	<acme:input-integer code="auditor.dashboard.form.label.totalNumCodeAuditsTypeDynamic" path="totalNumCodeAuditsTypeDynamic" readonly="true"/>

	
	<acme:input-double code="auditor.dashboard.form.label.averageNumAuditRecords" path="averageNumAuditRecords == 0 ? 'NaN' : averageNumAuditRecords" readonly="true" placeholder="--"/>
	<acme:input-double code="auditor.dashboard.form.label.deviationNumAuditRecords" path="deviationNumAuditRecords == 0 ? 'NaN' : deviationNumAuditRecords" readonly="true" placeholder="--"/>
	<acme:input-integer code="auditor.dashboard.form.label.minNumAuditRecords" path="minNumAuditRecords" readonly="true" placeholder="--"/>
	<acme:input-integer code="auditor.dashboard.form.label.maxNumAuditRecords" path="maxNumAuditRecords" readonly="true" placeholder="--"/>
	<acme:input-double code="auditor.dashboard.form.label.averagePeriodLength" path="averagePeriodLength == 0 ? 'NaN' : averagePeriodLength" readonly="true" placeholder="--"/>
	<acme:input-double code="auditor.dashboard.form.label.deviationPeriodLength" path="deviationPeriodLength == 0 ? 'NaN' : deviationPeriodLength" readonly="true" placeholder="--"/>
	<acme:input-integer code="auditor.dashboard.form.label.minPeriodLength" path="minPeriodLength" readonly="true" placeholder="--"/>
	<acme:input-integer code="auditor.dashboard.form.label.maxPeriodLength" path="maxPeriodLength" readonly="true" placeholder="--"/>
</acme:form>
