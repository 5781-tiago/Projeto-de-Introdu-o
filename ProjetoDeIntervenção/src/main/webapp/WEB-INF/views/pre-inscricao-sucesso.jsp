<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="titulo" value="Pré-inscrição registada" />
<%@ include file="includes/cabecalho.jspf" %>

<div class="alerta alerta-sucesso">Pré-inscrição registada com sucesso.</div>

<div class="resumo">
    <h1>Obrigado, <c:out value="${inscricao.nome}" />!</h1>
    <p>Recebemos a tua pré-inscrição. Vamos entrar em contacto contigo em breve.</p>
    <dl>
        <dt>Curso</dt><dd><c:out value="${inscricao.nomeCurso}" /></dd>
        <dt>Email</dt><dd><c:out value="${inscricao.email}" /></dd>
        <dt>Telefone</dt><dd><c:out value="${inscricao.telefone}" /></dd>
        <dt>Data de nascimento</dt><dd><c:out value="${inscricao.dataNascimentoFormatada}" /></dd>
    </dl>
    <a class="botao" href="${pageContext.request.contextPath}/cursos">Voltar à oferta formativa</a>
</div>

<%@ include file="includes/rodape.jspf" %>
