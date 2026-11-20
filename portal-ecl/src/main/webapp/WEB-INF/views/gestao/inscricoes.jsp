<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<c:set var="titulo" value="Pré-inscrições" />
<c:set var="menu" value="gestao" />
<c:set var="separador" value="inscricoes" />
<%@ include file="/WEB-INF/views/includes/cabecalho.jspf" %>
<%@ include file="/WEB-INF/views/gestao/menu.jspf" %>

<h1>Pré-inscrições <span class="contador">${fn:length(inscricoes)}</span></h1>

<form class="filtros" action="${pageContext.request.contextPath}/gestao/inscricoes" method="get">
    <div>
        <label for="curso">Curso</label>
        <select id="curso" name="curso">
            <option value="0">Todos</option>
            <c:forEach var="c" items="${cursos}">
                <option value="${c.id}" ${c.id == filtroCurso ? 'selected' : ''}><c:out value="${c.nome}" /></option>
            </c:forEach>
        </select>
    </div>
    <div>
        <label for="estado">Estado</label>
        <select id="estado" name="estado">
            <option value="">Todos</option>
            <c:forEach var="e" items="${estados}">
                <option value="${e}" ${e eq filtroEstado ? 'selected' : ''}>${e}</option>
            </c:forEach>
        </select>
    </div>
    <div class="filtros-botoes">
        <button class="botao" type="submit">Filtrar</button>
        <a class="botao botao-secundario" href="${pageContext.request.contextPath}/gestao/inscricoes">Limpar</a>
    </div>
</form>

<c:choose>
    <c:when test="${empty inscricoes}">
        <p class="vazio">Não há pré-inscrições com estes filtros.</p>
    </c:when>
    <c:otherwise>
        <div class="tabela-scroll">
            <table class="tabela">
                <thead>
                <tr><th>Código</th><th>Data</th><th>Nome</th><th>Curso</th><th>Contactos</th><th>Observações</th><th>Estado</th></tr>
                </thead>
                <tbody>
                <c:forEach var="p" items="${inscricoes}">
                    <tr>
                        <td class="nowrap"><c:out value="${p.codigo}" /></td>
                        <td class="nowrap"><c:out value="${p.criadoEmFormatado}" /></td>
                        <td><c:out value="${p.nome}" /></td>
                        <td><c:out value="${p.nomeCurso}" /></td>
                        <td><c:out value="${p.email}" /><c:if test="${not empty p.telefone}"><br><c:out value="${p.telefone}" /></c:if></td>
                        <td><c:out value="${p.observacoes}" /></td>
                        <td>
                            <form class="form-estado" action="${pageContext.request.contextPath}/gestao/inscricoes" method="post">
                                <input type="hidden" name="csrf" value="${sessionScope.csrf}">
                                <input type="hidden" name="id" value="${p.id}">
                                <input type="hidden" name="filtroCurso" value="${filtroCurso}">
                                <input type="hidden" name="filtroEstado" value="${fn:escapeXml(filtroEstado)}">
                                <select name="novoEstado" aria-label="Estado de ${fn:escapeXml(p.codigo)}">
                                    <c:forEach var="e" items="${estados}">
                                        <option value="${e}" ${e eq p.estado ? 'selected' : ''}>${e}</option>
                                    </c:forEach>
                                </select>
                                <button class="botao botao-pequeno" type="submit">Guardar</button>
                            </form>
                            <c:if test="${not empty p.atualizadoEm}"><small class="nota">Atualizado <c:out value="${p.atualizadoEmFormatado}" /></small></c:if>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </c:otherwise>
</c:choose>

<%@ include file="/WEB-INF/views/includes/rodape.jspf" %>
