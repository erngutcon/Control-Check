<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="urn:jsptagdir:/WEB-INF/tags"%>

<acme:form>
	<acme:input-textbox readonly="true" code="inventor.comema.form.label.code" path="code"/>
	<jstl:if test="${command != 'create'}">
		<acme:input-textbox code="inventor.comema.form.label.creationMoment" path="creationMoment" readonly="true"/>
	</jstl:if>
	<acme:input-textbox code="inventor.comema.form.label.startPeriod" path="startPeriod" />
	<acme:input-textbox code="inventor.comema.form.label.finishPeriod" path="finishPeriod" />
	<acme:input-textbox code="inventor.comema.form.label.subject" path="subject"/>
	<acme:input-textbox code="inventor.comema.form.label.explanation" path="explanation"/>
	<acme:input-money code="inventor.comema.form.label.income" path="income"/>
	<acme:input-url code="inventor.comema.form.label.moreInfo" path="moreInfo"/>
	
	<jstl:choose>
	    <jstl:when test="${acme:anyOf(command, 'show, update, delete')}">
	        <acme:submit code="inventor.comema.form.button.update" action="/inventor/comema/update"/>
	        <acme:submit code="inventor.comema.form.button.delete" action="/inventor/comema/delete"/>
	    </jstl:when>
	    <jstl:when test="${command == 'create'}">
	    	<acme:hidden-data path="itemId"/>
	        <acme:submit code="inventor.comema.form.button.create" action="/inventor/comema/create"/>
	    </jstl:when>
	</jstl:choose>
</acme:form>