<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<c:set var="titulo" value="Consultar avaria" />
<c:set var="menu" value="avarias" />
<%@ include file="includes/cabecalho.jspf" %>

<h1>Acompanhar avaria</h1>
<p class="subtitulo">Indica o código que recebeste ao reportar a avaria.</p>

<form class="linha-pesquisa" action="${pageContext.request.contextPath}/avarias/consultar" method="get">
    <input type="text" name="codigo" value="${fn:escapeXml(codigo)}" placeholder="AV-XXXXXX" maxlength="9" required aria-label="Código do pedido">
    <button class="botao" type="submit">Consultar</button>
</form>

<c:if test="${naoEncontrada}">
    <div class="alerta alerta-erro" role="alert">Não existe nenhum pedido com esse código.</div>
</c:if>

<c:if test="${not empty ocorrencia}">
    <div class="resumo">
        <p class="codigo-pedido">Pedido <strong><c:out value="${ocorrencia.codigo}" /></strong></p>
        <dl>
            <dt>Estado</dt><dd><span class="estado estado-${fn:toLowerCase(fn:replace(ocorrencia.estado, ' ', '-'))}"><c:out value="${ocorrencia.estado}" /></span></dd>
            <dt>Sala</dt><dd><c:out value="${ocorrencia.sala}" /> · <c:out value="${ocorrencia.localizacao}" /></dd>
            <dt>Equipamento ou serviço</dt><dd><c:out value="${ocorrencia.categoria}" /></dd>
            <dt>Prioridade</dt><dd><c:out value="${ocorrencia.prioridade}" /></dd>
            <dt>Registado em</dt><dd><c:out value="${ocorrencia.criadoEmFormatado}" /></dd>
            <c:if test="${not empty ocorrencia.atualizadoEm}">
                <dt>Última atualização</dt><dd><c:out value="${ocorrencia.atualizadoEmFormatado}" /></dd>
            </c:if>
            <c:if test="${not empty ocorrencia.resolucao}">
                <dt>Resolução</dt><dd><c:out value="${ocorrencia.resolucao}" /></dd>
            </c:if>
        </dl>
    </div>
</c:if>

<%@ include file="includes/rodape.jspf" %>
