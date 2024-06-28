<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>	
	<acme:input-textbox code="authenticated.contract.form.label.contractCode" path="contractCode" readonly="true"/>
	<acme:input-moment code="authenticated.contract.form.label.instantiation" path="instantiation" readonly="true"/>
	<acme:input-textbox code="authenticated.contract.form.label.providerName" path="providerName" readonly="true"/>
	<acme:input-textbox code="authenticated.contract.form.label.customerName" path="customerName" readonly="true"/>
	<acme:input-textbox code="authenticated.contract.form.label.goals" path="goals" readonly="true"/>
	<acme:input-money code="authenticated.contract.form.label.budget" path="budget" readonly="true"/>		
</acme:form>
<acme:button code="authenticated.contract.form.button.progress"
		action="/authenticated/progress/list-by-contract?contractId=${id}" />