<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form readonly="false">
	<acme:input-textbox code="sponsor.sponsorship.form.label.code" path="code"/>
	<jstl:if test="${acme:anyOf(_command,'show|update|delete|publish')}">	
		<acme:input-moment code="sponsor.sponsorship.form.label.moment" path="moment" readonly="true"/>
	</jstl:if>
	<acme:input-moment code="sponsor.sponsorship.form.label.startDate" path="startDate"/>
	<acme:input-moment code="sponsor.sponsorship.form.label.endDate" path="endDate"/>
	<acme:input-money code="sponsor.sponsorship.form.label.amount" path="amount"/>
	<acme:input-select code="sponsor.sponsorship.form.label.type" path="type" choices="${types}"/>
	<acme:input-email code="sponsor.sponsorship.form.label.email" path="email"/>
	<acme:input-url code="sponsor.sponsorship.form.label.link" path="link"/>
	<acme:input-select code="sponsor.sponsorship.form.label.project" path="project" choices="${projects}"/>
	
	<jstl:if test="${_command != 'create'}">
		<acme:input-checkbox code="sponsor.sponsorship.form.label.draftMode" path="draftMode" readonly="true"/>	
	</jstl:if>
	
	<jstl:choose>
        <jstl:when test="${acme:anyOf(_command, 'show|update|delete|publish') && draftMode}">
            <acme:submit code="sponsor.sponsorship.form.button.update" action="/sponsor/sponsorship/update"/>
            <acme:submit code="sponsor.sponsorship.form.button.delete" action="/sponsor/sponsorship/delete"/>
            <acme:submit code="sponsor.sponsorship.form.button.publish" action="/sponsor/sponsorship/publish"/>
    		<acme:button code="sponsor.sponsorship.form.button.list-invoices" action="/sponsor/invoice/list?sponsorshipId=${id}"/>
        </jstl:when>
        <jstl:when test="${_command == 'show' && draftMode == false}">
			<acme:button code="sponsor.sponsorship.form.button.list-invoices" action="/sponsor/invoice/list?sponsorshipId=${id}"/>			
		</jstl:when>
        <jstl:when test="${_command == 'create'}">
            <acme:submit code="sponsor.sponsorship.form.button.create" action="/sponsor/sponsorship/create"/>
        </jstl:when>
    </jstl:choose>
    
</acme:form>