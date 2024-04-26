<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form >
	<acme:input-textbox code="administrator.risk.form.label.code" path="code"/>
	<acme:input-textbox code="administrator.risk.form.label.description" path="description"/>
	<acme:input-url code="administrator.risk.form.label.link" path="link"/>
	<acme:input-double code="administrator.risk.form.label.probability" path="probability"/>
	<acme:input-double code="administrator.risk.form.label.impact" path="impact"/>
	<acme:input-moment code="administrator.risk.form.label.identificationDate" path="identificationDate"/>
	
	<jstl:if test="${_command == 'create'}">
			<acme:submit code="administrator.risk.form.button.create" action="/administrator/risk/create"/>
	</jstl:if>		
	
	<jstl:if test="${acme:anyOf(_command, 'show|update|delete')}">
			<acme:submit code="administrator.risk.form.button.update" action="/administrator/risk/update"/>
			<acme:submit code="administrator.risk.form.button.delete" action="/administrator/risk/delete"/>			
	</jstl:if>
	
</acme:form>