<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<c:set var="titulo" value="Ocorrência ${ocorrencia.codigo}" />
<c:set var="menu" value="gestao" />
<c:set var="separador" value="ocorrencias" />
<%@ include file="/WEB-INF/views/includes/cabecalho.jspf" %>
<%@ include file="/WEB-INF/views/gestao/menu.jspf" %>

<p class="migalhas"><a href="${pageContext.request.contextPath}/gestao/ocorrencias">Ocorrências</a> › <c:out value="${ocorrencia.codigo}" /></p>

<c:if test="${guardado}">
    <div class="alerta alerta-sucesso" role="status">Alterações guardadas.</div>
</c:if>

<div class="detalhe">
    <div class="detalhe-principal">
        <h1>Ocorrência <c:out value="${ocorrencia.codigo}" /></h1>
        <dl class="ficha">
            <dt>Reportada por</dt><dd><c:out value="${ocorrencia.nomeReportador}" /> (<c:out value="${ocorrencia.perfilReportador}" />)</dd>
            <dt>Sala</dt><dd><c:out value="${ocorrencia.sala}" /> · <c:out value="${ocorrencia.localizacao}" /></dd>
            <dt>Equipamento ou serviço</dt><dd><c:out value="${ocorrencia.categoria}" /></dd>
            <dt>Prioridade</dt><dd><span class="prioridade prioridade-${fn:toLowerCase(ocorrencia.prioridade)}"><c:out value="${ocorrencia.prioridade}" /></span></dd>
            <dt>Descrição</dt><dd><c:out value="${ocorrencia.descricao}" /></dd>
            <dt>Registada em</dt><dd><c:out value="${ocorrencia.criadoEmFormatado}" /></dd>
            <c:if test="${not empty ocorrencia.atualizadoEm}">
                <dt>Última atualização</dt><dd><c:out value="${ocorrencia.atualizadoEmFormatado}" /><c:if test="${not empty ocorrencia.tecnico}"> por <c:out value="${ocorrencia.tecnico}" /></c:if></dd>
            </c:if>
        </dl>

        <h2>Histórico de estados</h2>
        <c:choose>
            <c:when test="${empty historico}"><p class="vazio">Ainda sem alterações de estado.</p></c:when>
            <c:otherwise>
                <ul class="historico">
                    <c:forEach var="h" items="${historico}">
                        <li><c:out value="${h.alteradoEmFormatado}" /> — <c:out value="${h.estadoAnterior}" /> → <strong><c:out value="${h.estadoNovo}" /></strong> (<c:out value="${h.utilizador}" />)</li>
                    </c:forEach>
                </ul>
            </c:otherwise>
        </c:choose>
    </div>

    <aside class="detalhe-lateral">
        <form class="formulario caixa-info" action="${pageContext.request.contextPath}/gestao/ocorrencia" method="post">
            <h2>Acompanhamento</h2>
            <input type="hidden" name="csrf" value="${sessionScope.csrf}">
            <input type="hidden" name="id" value="${ocorrencia.id}">
            <label for="estado">Estado</label>
            <select id="estado" name="estado" required>
                <c:forEach var="e" items="${estados}"><option value="${e}" ${e eq ocorrencia.estado ? 'selected' : ''}>${e}</option></c:forEach>
            </select>
            <label for="resolucao">Observações técnicas / resolução</label>
            <textarea id="resolucao" name="resolucao" rows="6" maxlength="1000"><c:out value="${ocorrencia.resolucao}" /></textarea>
            <button class="botao botao-largo" type="submit">Guardar</button>
        </form>
    </aside>
</div>

<%@ include file="/WEB-INF/views/includes/rodape.jspf" %>
