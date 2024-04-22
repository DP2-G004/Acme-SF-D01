<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:input-moment code="authenticated.notice.list.label.instantiationMoment" path="instantiationMoment"/>
	<acme:input-textbox code="authenticated.notice.list.label.title" path="title"/>		
	<acme:input-textbox code="authenticated.notice.list.label.author" path="author"/>	
	<acme:input-textbox code="authenticated.notice.list.label.message" path="message"/>	
	<acme:input-textbox code="authenticated.notice.list.label.email" path="email"/>
	<acme:input-url code="authenticated.notice.list.label.link" path="link"/>	
	
	<jstl:choose>		
        <jstl:when test="${_command == 'create'}">
            <acme:submit code="authenticated.notice.form.button.create" action="/authenticated/notice/create"/>
        </jstl:when>
    </jstl:choose>
</acme:form>

