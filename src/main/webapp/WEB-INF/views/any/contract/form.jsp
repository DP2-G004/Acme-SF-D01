<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>	
	<acme:input-textbox code="any.contract.form.label.code" path="code" readonly="true"/>
	<acme:input-moment code="any.contract.form.label.instantiation" path="instantiation" readonly="true"/>
	<acme:input-textbox code="any.contract.form.label.providerName" path="providerName" readonly="true"/>
	<acme:input-textbox code="any.contract.form.label.customerName" path="customerName" readonly="true"/>
	<acme:input-textbox code="any.contract.form.label.goals" path="goals" readonly="true"/>
	<acme:input-money code="any.contract.form.label.budget" path="budget" readonly="true"/>		
</acme:form>
