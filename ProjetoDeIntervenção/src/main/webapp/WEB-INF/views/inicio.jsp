<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="titulo" value="Início" />
<%@ include file="includes/cabecalho.jspf" %>

<section class="destaque">
    <h1>Uma escola para a vida</h1>
    <p>Conhece a nossa oferta formativa, faz a tua pré-inscrição ou comunica uma avaria numa sala.</p>
</section>

<section class="cartoes">
    <a class="cartao" href="${pageContext.request.contextPath}/cursos">
        <h2>Oferta formativa</h2>
        <p>Cursos profissionais, cursos de aprendizagem e cursos de educação e formação.</p>
    </a>
    <a class="cartao" href="${pageContext.request.contextPath}/pre-inscricao">
        <h2>Pré-inscrição</h2>
        <p>Queres estudar na ECL? Deixa os teus dados e entramos em contacto contigo.</p>
    </a>
    <a class="cartao" href="${pageContext.request.contextPath}/avarias">
        <h2>Registo de avarias</h2>
        <p>Encontraste um problema numa sala? Comunica-o à equipa técnica.</p>
    </a>
</section>

<%@ include file="includes/rodape.jspf" %>
