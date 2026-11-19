<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<c:set var="titulo" value="Pré-inscrição" />
<%@ include file="includes/cabecalho.jspf" %>

<h1>Pré-inscrição</h1>
<p class="subtitulo">Preenche os teus dados e a escola entra em contacto contigo.</p>

<c:if test="${not empty erros}">
    <div class="alerta alerta-erro">Há campos por corrigir no formulário.</div>
</c:if>

<form class="formulario" action="${pageContext.request.contextPath}/pre-inscricao" method="post" novalidate>

    <label for="nome">Nome completo</label>
    <input type="text" id="nome" name="nome" value="${fn:escapeXml(nome)}" maxlength="120" required
           class="${not empty erros.nome ? 'invalido' : ''}">
    <c:if test="${not empty erros.nome}"><span class="erro"><c:out value="${erros.nome}" /></span></c:if>

    <div class="linha">
        <div>
            <label for="email">Email</label>
            <input type="email" id="email" name="email" value="${fn:escapeXml(email)}" maxlength="120" required
                   class="${not empty erros.email ? 'invalido' : ''}">
            <c:if test="${not empty erros.email}"><span class="erro"><c:out value="${erros.email}" /></span></c:if>
        </div>
        <div>
            <label for="telefone">Telefone</label>
            <input type="tel" id="telefone" name="telefone" value="${fn:escapeXml(telefone)}" maxlength="12" required
                   class="${not empty erros.telefone ? 'invalido' : ''}">
            <c:if test="${not empty erros.telefone}"><span class="erro"><c:out value="${erros.telefone}" /></span></c:if>
        </div>
    </div>

    <label for="dataNascimento">Data de nascimento</label>
    <input type="date" id="dataNascimento" name="dataNascimento" value="${fn:escapeXml(dataNascimento)}" required
           class="${not empty erros.dataNascimento ? 'invalido' : ''}">
    <c:if test="${not empty erros.dataNascimento}"><span class="erro"><c:out value="${erros.dataNascimento}" /></span></c:if>

    <label for="curso">Curso pretendido</label>
    <select id="curso" name="curso" required class="${not empty erros.curso ? 'invalido' : ''}">
        <option value="">Escolhe um curso</option>
        <c:forEach var="c" items="${cursos}">
            <option value="${c.id}" ${c.id.toString() eq cursoId ? 'selected' : ''}>
                <c:out value="${c.nome}" /> (<c:out value="${c.tipo}" />)
            </option>
        </c:forEach>
    </select>
    <c:if test="${not empty erros.curso}"><span class="erro"><c:out value="${erros.curso}" /></span></c:if>

    <label for="observacoes">Observações <span class="opcional">(opcional)</span></label>
    <textarea id="observacoes" name="observacoes" rows="4" maxlength="500"
              class="${not empty erros.observacoes ? 'invalido' : ''}"><c:out value="${observacoes}" /></textarea>
    <c:if test="${not empty erros.observacoes}"><span class="erro"><c:out value="${erros.observacoes}" /></span></c:if>

    <label class="caixa">
        <input type="checkbox" name="rgpd" value="sim" ${rgpd ? 'checked' : ''}>
        Autorizo a Escola de Comércio de Lisboa a usar estes dados para me contactar sobre a pré-inscrição.
    </label>
    <c:if test="${not empty erros.rgpd}"><span class="erro"><c:out value="${erros.rgpd}" /></span></c:if>

    <button class="botao" type="submit">Enviar pré-inscrição</button>
</form>

<%@ include file="includes/rodape.jspf" %>
