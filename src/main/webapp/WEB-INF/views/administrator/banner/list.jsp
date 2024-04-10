<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="administrator.banner.list.label.slogan" path="slogan"/>	
	<acme:list-column code="administrator.banner.list.label.pictureLink" path="pictureLink"/>
	<acme:list-column code="administrator.banner.list.label.link" path="link"/>
	<acme:list-column code="administrator.banner.list.label.lastInstantiationMoment" path="lastInstantiationMoment"/>
	<acme:list-column code="administrator.banner.list.label.endOfInstantiation" path="endOfInstantiation"/>
</acme:list>