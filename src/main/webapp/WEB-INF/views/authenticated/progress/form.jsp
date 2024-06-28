<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>	

	<acme:input-textbox code="client.progress.form.label.record" path="record"/>
	<acme:input-textbox code="client.progress.form.label.completeness" path="completeness"/>
	<acme:input-textbox code="client.progress.form.label.comment" path="comment"/>
	<acme:input-textbox code="client.progress.form.label.responsable" path="responsable"/>
			
</acme:form>