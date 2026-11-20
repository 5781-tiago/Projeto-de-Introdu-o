<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="titulo" value="Pré-inscrição recebida" />
<c:set var="menu" value="pre-inscricao" />
<%@ include file="includes/cabecalho.jspf" %>

<div class="alerta alerta-sucesso" role="status">Pré-inscrição recebida com sucesso.</div>

<div class="resumo">
    <p class="codigo-pedido">Código da pré-inscrição: <strong><c:out value="${inscricao.codigo}" /></strong></p>
    <dl>
        <dt>Curso</dt><dd><c:out value="${inscricao.nomeCurso}" /></dd>
        <dt>Estado</dt><dd><span class="estado estado-recebida"><c:out value="${inscricao.estado}" /></span></dd>
    </dl>
    <p>A equipa da escola vai analisar o pedido. Guarda o código para referência.</p>
    <a class="botao" href="${pageContext.request.contextPath}/cursos">Voltar à oferta formativa</a>
</div>

<%@ include file="includes/rodape.jspf" %>
