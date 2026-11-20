<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<c:set var="titulo" value="Cursos" />
<c:set var="menu" value="gestao" />
<c:set var="separador" value="cursos" />
<%@ include file="/WEB-INF/views/includes/cabecalho.jspf" %>
<%@ include file="/WEB-INF/views/gestao/menu.jspf" %>

<h1>Cursos <span class="contador">${fn:length(cursos)}</span></h1>

<div class="tabela-scroll">
    <table class="tabela">
        <thead>
        <tr><th>Curso</th><th>Modalidade</th><th>Área</th><th>Nível</th><th>Horas</th><th>Verificado em</th></tr>
        </thead>
        <tbody>
        <c:forEach var="c" items="${cursos}">
            <tr>
                <td><a href="${pageContext.request.contextPath}/cursos/detalhe?id=${c.id}"><c:out value="${c.nome}" /></a></td>
                <td><c:out value="${c.modalidade}" /></td>
                <td><c:out value="${c.area}" /></td>
                <td><c:out value="${c.nivel}" /></td>
                <td><c:choose><c:when test="${not empty c.horasTotal}">${c.horasTotal}</c:when><c:otherwise><span class="confirmar">A confirmar</span></c:otherwise></c:choose></td>
                <td><c:out value="${c.dataVerificacaoFormatada}" /></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

<%@ include file="/WEB-INF/views/includes/rodape.jspf" %>
