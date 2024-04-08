<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form >
	<acme:input-textbox code="administrator.banner.form.label.slogan" path="slogan"/>
	<acme:input-url code="administrator.banner.form.label.pictureLink" path="pictureLink"/>
	<acme:input-url code="administrator.banner.form.label.link" path="link"/>
	<acme:input-moment code="administrator.banner.form.label.lastInstantiationMoment" path="lastInstantiationMoment"/>
	<acme:input-moment code="administrator.banner.form.label.endOfInstantiation" path="endOfInstantiation"/>	
</acme:form>