<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<c:set var="titulo" value="Ocorrências" />
<c:set var="menu" value="gestao" />
<c:set var="separador" value="ocorrencias" />
<%@ include file="/WEB-INF/views/includes/cabecalho.jspf" %>
<%@ include file="/WEB-INF/views/gestao/menu.jspf" %>

<h1>Ocorrências <span class="contador">${fn:length(ocorrencias)}</span></h1>

<form class="filtros" action="${pageContext.request.contextPath}/gestao/ocorrencias" method="get">
    <div>
        <label for="sala">Sala</label>
        <select id="sala" name="sala">
            <option value="0">Todas</option>
            <c:forEach var="s" items="${salas}">
                <option value="${s.id}" ${s.id == filtroSala ? 'selected' : ''}><c:out value="${s.codigo}" /></option>
            </c:forEach>
        </select>
    </div>
    <div>
        <label for="estado">Estado</label>
        <select id="estado" name="estado">
            <option value="">Todos</option>
            <c:forEach var="e" items="${estados}"><option value="${e}" ${e eq filtroEstado ? 'selected' : ''}>${e}</option></c:forEach>
        </select>
    </div>
    <div>
        <label for="prioridade">Prioridade</label>
        <select id="prioridade" name="prioridade">
            <option value="">Todas</option>
            <c:forEach var="p" items="${prioridades}"><option value="${p}" ${p eq filtroPrioridade ? 'selected' : ''}>${p}</option></c:forEach>
        </select>
    </div>
    <div class="filtros-botoes">
        <button class="botao" type="submit">Filtrar</button>
        <a class="botao botao-secundario" href="${pageContext.request.contextPath}/gestao/ocorrencias">Limpar</a>
    </div>
</form>

<c:choose>
    <c:when test="${empty ocorrencias}">
        <p class="vazio">Não há ocorrências com estes filtros.</p>
    </c:when>
    <c:otherwise>
        <div class="tabela-scroll">
            <table class="tabela">
                <thead>
                <tr><th>Código</th><th>Data</th><th>Sala</th><th>Categoria</th><th>Descrição</th><th>Prioridade</th><th>Estado</th><th></th></tr>
                </thead>
                <tbody>
                <c:forEach var="o" items="${ocorrencias}">
                    <tr>
                        <td class="nowrap"><c:out value="${o.codigo}" /></td>
                        <td class="nowrap"><c:out value="${o.criadoEmFormatado}" /></td>
                        <td><c:out value="${o.sala}" /></td>
                        <td><c:out value="${o.categoria}" /></td>
                        <td><c:out value="${o.descricao}" /></td>
                        <td><span class="prioridade prioridade-${fn:toLowerCase(o.prioridade)}"><c:out value="${o.prioridade}" /></span></td>
                        <td class="nowrap"><c:out value="${o.estado}" /></td>
                        <td><a class="botao botao-pequeno" href="${pageContext.request.contextPath}/gestao/ocorrencia?id=${o.id}">Abrir</a></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </c:otherwise>
</c:choose>

<%@ include file="/WEB-INF/views/includes/rodape.jspf" %>
