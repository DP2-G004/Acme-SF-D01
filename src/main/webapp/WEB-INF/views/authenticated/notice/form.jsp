<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="authenticated.notice.form.label.title" path="title"/>
	<acme:input-textbox code="authenticated.notice.form.label.message" path="message"/>
	<acme:input-textbox code="authenticated.notice.form.label.email" path="email"/>
	<acme:input-textbox code="authenticated.notice.form.label.link" path="link"/>
	<jstl:choose>
		<jstl:when test="${_command == 'create'}">
			<acme:input-checkbox code="authenticated.notice.form.label.confirmation" path="confirmation"/>
		</jstl:when>
	</jstl:choose>
	<jstl:choose>
		<jstl:when test="${_command == 'show'}">
			<acme:input-textbox code="authenticated.notice.form.label.lastInstantiation" path="lastInstantiationMoment" readonly="true" />
			<acme:input-textbox code="authenticated.notice.form.label.author" path="author" readonly="true" />
		</jstl:when>
	</jstl:choose>
	
	<jstl:choose>
	<jstl:when test="${_command == 'create'}">
			<acme:submit code="authenticated.notice.form.button.create" action ="/authenticated/notice/create"/>
		</jstl:when>
	</jstl:choose>
</acme:form>