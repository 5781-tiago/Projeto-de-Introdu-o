<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="titulo" value="Avaria registada" />
<%@ include file="includes/cabecalho.jspf" %>

<div class="alerta alerta-sucesso">Avaria registada com sucesso.</div>

<div class="resumo">
    <h1>Pedido enviado à equipa técnica</h1>
    <dl>
        <dt>Sala</dt><dd><c:out value="${avaria.sala}" /></dd>
        <dt>Tipo de avaria</dt><dd><c:out value="${avaria.tipo}" /></dd>
        <dt>Descrição</dt><dd><c:out value="${avaria.descricao}" /></dd>
        <dt>Reportado por</dt><dd><c:out value="${avaria.reportadoPor}" /></dd>
    </dl>
    <a class="botao" href="${pageContext.request.contextPath}/avarias">Registar outra avaria</a>
</div>

<%@ include file="includes/rodape.jspf" %>
