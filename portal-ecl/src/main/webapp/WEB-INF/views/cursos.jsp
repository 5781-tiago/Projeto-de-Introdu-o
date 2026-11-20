<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="titulo" value="Oferta formativa" />
<%@ include file="includes/cabecalho.jspf" %>

<h1>Oferta formativa</h1>
<p class="subtitulo">Cursos disponíveis no ano letivo 2026/2027.</p>

<c:set var="tipoAtual" value="" />
<c:forEach var="curso" items="${cursos}">
    <c:if test="${curso.tipo ne tipoAtual}">
        <c:if test="${not empty tipoAtual}"></div></c:if>
        <h2 class="grupo"><c:out value="${curso.tipo}" /></h2>
        <div class="lista-cursos">
        <c:set var="tipoAtual" value="${curso.tipo}" />
    </c:if>
    <article class="curso">
        <h3><c:out value="${curso.nome}" /></h3>
        <p class="etiquetas">
            <span class="etiqueta">Nível <c:out value="${curso.nivel}" /></span>
            <span class="etiqueta"><c:out value="${curso.duracao}" /></span>
        </p>
        <p><c:out value="${curso.descricao}" /></p>
        <a class="botao botao-pequeno" href="${pageContext.request.contextPath}/pre-inscricao?curso=${curso.id}">Fazer pré-inscrição</a>
    </article>
</c:forEach>
<c:if test="${not empty tipoAtual}"></div></c:if>

<%@ include file="includes/rodape.jspf" %>
