<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="sponsor.sponsorship.form.label.code" path="code"/>
	<acme:input-textbox code="sponsor.sponsorship.form.label.moment" path="moment"/>
	<acme:input-textbox code="sponsor.sponsorship.form.label.startDate" path="startDate"/>
	<acme:input-textbox code="sponsor.sponsorship.form.label.endDate" path="endDate"/>
	<acme:input-textbox code="sponsor.sponsorship.form.label.amount" path="amount"/>
	<acme:input-textbox code="sponsor.sponsorship.form.label.type" path="type"/>
	<acme:input-textbox code="sponsor.sponsorship.form.label.email" path="email"/>
	<acme:input-textbox code="sponsor.sponsorship.form.label.link" path="link"/>
	
	<%-- 
	<acme:submit test="${_command == 'create'}" code="authenticated.sponsor.form.button.create" action="/authenticated/sponsor/create"/>
	<acme:submit test="${_command == 'update'}" code="authenticated.manager.form.button.update" action="/authenticated/sponsor/update"/>
	 --%>
</acme:form>