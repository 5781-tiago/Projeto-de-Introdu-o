<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<c:set var="titulo" value="Avaria registada" />
<c:set var="menu" value="avarias" />
<%@ include file="includes/cabecalho.jspf" %>

<div class="alerta alerta-sucesso" role="status">Avaria registada com sucesso.</div>

<div class="resumo">
    <p class="codigo-pedido">Código do pedido: <strong><c:out value="${ocorrencia.codigo}" /></strong></p>
    <dl>
        <dt>Sala</dt><dd><c:out value="${ocorrencia.sala}" /> · <c:out value="${ocorrencia.localizacao}" /></dd>
        <dt>Equipamento ou serviço</dt><dd><c:out value="${ocorrencia.categoria}" /></dd>
        <dt>Prioridade</dt><dd><c:out value="${ocorrencia.prioridade}" /></dd>
        <dt>Descrição</dt><dd><c:out value="${ocorrencia.descricao}" /></dd>
        <dt>Estado</dt><dd><span class="estado estado-aberta"><c:out value="${ocorrencia.estado}" /></span></dd>
    </dl>
    <p>Guarda o código para acompanhares o pedido.</p>
    <a class="botao" href="${pageContext.request.contextPath}/avarias/consultar?codigo=${fn:escapeXml(ocorrencia.codigo)}">Acompanhar pedido</a>
    <a class="botao botao-secundario" href="${pageContext.request.contextPath}/avarias">Reportar outra avaria</a>
</div>

<%@ include file="includes/rodape.jspf" %>
