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
	<jstl:choose>
		<jstl:when test="${acme:anyOf(_command, 'show|delete') && draftMode == true}">
			<acme:input-select code="manager.link.form.label.project" path="project" choices="${projects }" readonly="true"/>
			<acme:input-select code="manager.link.form.label.userStory" path="userStory" choices="${userStories }" readonly="true"/>
			<acme:submit code="manager.link.form.button.delete" action="/manager/project-user-story-link/delete"/>
		</jstl:when>
		<jstl:when test="${_command == 'show' && draftMode == false}">
			<acme:input-select code="manager.link.form.label.project" path="project" choices="${projects }" readonly="true"/>
			<acme:input-select code="manager.link.form.label.userStory" path="userStory" choices="${userStories }" readonly="true"/>
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:input-select code="manager.link.form.label.project" path="project" choices="${projects }"/>
			<acme:input-select code="manager.link.form.label.userStory" path="userStory" choices="${userStories }"/>
			<acme:submit code="manager.link.form.button.create" action="/manager/project-user-story-link/create"/>
		</jstl:when>
	</jstl:choose>
</acme:form>