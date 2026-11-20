<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<c:set var="titulo" value="Pré-inscrição" />
<c:set var="menu" value="pre-inscricao" />
<%@ include file="includes/cabecalho.jspf" %>

<h1>Pré-inscrição</h1>
<p class="subtitulo">Manifesta o teu interesse num curso. Os campos marcados com * são obrigatórios.</p>

<c:if test="${not empty erros}">
    <div class="alerta alerta-erro" role="alert">Há campos por corrigir no formulário.</div>
</c:if>

<form class="formulario" action="${pageContext.request.contextPath}/pre-inscricao" method="post">

    <label for="nome">Nome completo *</label>
    <input type="text" id="nome" name="nome" value="${fn:escapeXml(nome)}" minlength="3" maxlength="120" required
           class="${not empty erros.nome ? 'invalido' : ''}">
    <c:if test="${not empty erros.nome}"><span class="erro"><c:out value="${erros.nome}" /></span></c:if>

    <div class="linha">
        <div>
            <label for="email">Email de contacto *</label>
            <input type="email" id="email" name="email" value="${fn:escapeXml(email)}" maxlength="120" required
                   class="${not empty erros.email ? 'invalido' : ''}">
            <c:if test="${not empty erros.email}"><span class="erro"><c:out value="${erros.email}" /></span></c:if>
        </div>
        <div>
            <label for="telefone">Contacto telefónico <span class="opcional">(opcional)</span></label>
            <input type="tel" id="telefone" name="telefone" value="${fn:escapeXml(telefone)}" maxlength="9" pattern="[29][0-9]{8}"
                   title="9 dígitos, a começar por 9 ou 2" class="${not empty erros.telefone ? 'invalido' : ''}">
            <c:if test="${not empty erros.telefone}"><span class="erro"><c:out value="${erros.telefone}" /></span></c:if>
        </div>
    </div>

    <label for="curso">Curso pretendido *</label>
    <select id="curso" name="curso" required class="${not empty erros.curso ? 'invalido' : ''}">
        <option value="">Escolhe um curso</option>
        <c:forEach var="c" items="${cursos}">
            <option value="${c.id}" ${c.id.toString() eq cursoId ? 'selected' : ''}><c:out value="${c.nome}" /> — <c:out value="${c.modalidade}" /></option>
        </c:forEach>
    </select>
    <c:if test="${not empty erros.curso}"><span class="erro"><c:out value="${erros.curso}" /></span></c:if>

    <label for="observacoes">Observações <span class="opcional">(opcional, até 500 caracteres)</span></label>
    <textarea id="observacoes" name="observacoes" rows="4" maxlength="500"
              class="${not empty erros.observacoes ? 'invalido' : ''}"><c:out value="${observacoes}" /></textarea>
    <c:if test="${not empty erros.observacoes}"><span class="erro"><c:out value="${erros.observacoes}" /></span></c:if>

    <div class="privacidade">
        <h2>Informação de privacidade</h2>
        <p>Este portal é um protótipo escolar. Os dados introduzidos servem apenas para demonstração do projeto e são guardados numa base de dados de laboratório, acessível só à equipa de gestão. Não introduzas dados reais. Podes pedir a eliminação dos dados de teste à equipa do projeto.</p>
        <label class="caixa">
            <input type="checkbox" name="privacidade" value="sim" required ${privacidade ? 'checked' : ''}>
            Li e tomei conhecimento da informação de privacidade. *
        </label>
        <c:if test="${not empty erros.privacidade}"><span class="erro"><c:out value="${erros.privacidade}" /></span></c:if>
    </div>

    <button class="botao" type="submit">Enviar pré-inscrição</button>
</form>

<%@ include file="includes/rodape.jspf" %>
