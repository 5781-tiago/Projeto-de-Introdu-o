<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<c:set var="titulo" value="Registo de avarias" />
<%@ include file="includes/cabecalho.jspf" %>

<h1>Registo de avarias</h1>
<p class="subtitulo">Comunica um problema numa sala à equipa técnica.</p>

<c:if test="${not empty erros}">
    <div class="alerta alerta-erro">Há campos por corrigir no formulário.</div>
</c:if>

<form class="formulario" action="${pageContext.request.contextPath}/avarias" method="post" novalidate>

    <div class="linha">
        <div>
            <label for="sala">Sala</label>
            <input type="text" id="sala" name="sala" value="${fn:escapeXml(sala)}" maxlength="20" required
                   class="${not empty erros.sala ? 'invalido' : ''}">
            <c:if test="${not empty erros.sala}"><span class="erro"><c:out value="${erros.sala}" /></span></c:if>
        </div>
        <div>
            <label for="tipo">Tipo de avaria</label>
            <select id="tipo" name="tipo" required class="${not empty erros.tipo ? 'invalido' : ''}">
                <option value="">Escolhe uma opção</option>
                <c:forEach var="t" items="${tipos}">
                    <option value="${t}" ${t eq tipo ? 'selected' : ''}>${t}</option>
                </c:forEach>
            </select>
            <c:if test="${not empty erros.tipo}"><span class="erro"><c:out value="${erros.tipo}" /></span></c:if>
        </div>
    </div>

    <label for="descricao">Descrição</label>
    <textarea id="descricao" name="descricao" rows="4" required minlength="10"
              class="${not empty erros.descricao ? 'invalido' : ''}"><c:out value="${descricao}" /></textarea>
    <c:if test="${not empty erros.descricao}"><span class="erro"><c:out value="${erros.descricao}" /></span></c:if>

    <label for="reportadoPor">O teu nome</label>
    <input type="text" id="reportadoPor" name="reportadoPor" value="${fn:escapeXml(reportadoPor)}" maxlength="120" required
           class="${not empty erros.reportadoPor ? 'invalido' : ''}">
    <c:if test="${not empty erros.reportadoPor}"><span class="erro"><c:out value="${erros.reportadoPor}" /></span></c:if>

    <button class="botao" type="submit">Registar avaria</button>
</form>

<%@ include file="includes/rodape.jspf" %>
