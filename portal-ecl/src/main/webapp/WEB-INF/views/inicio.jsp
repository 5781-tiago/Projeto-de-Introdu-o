<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="titulo" value="Início" />
<c:set var="menu" value="inicio" />
<%@ include file="includes/cabecalho.jspf" %>

<section class="destaque">
    <h1>Portal de serviços da ECL</h1>
    <p>Consulta a oferta formativa, manifesta o teu interesse num curso ou reporta uma avaria numa sala de formação.</p>
</section>

<section class="cartoes">
    <a class="cartao" href="${pageContext.request.contextPath}/cursos">
        <h2>Oferta formativa</h2>
        <p>Cursos profissionais, de aprendizagem e de educação e formação, com cargas horárias e saídas profissionais.</p>
    </a>
    <a class="cartao" href="${pageContext.request.contextPath}/pre-inscricao">
        <h2>Pré-inscrição</h2>
        <p>Deixa os teus contactos e o curso que te interessa. A escola entra em contacto contigo.</p>
    </a>
    <a class="cartao" href="${pageContext.request.contextPath}/avarias">
        <h2>Reportar avaria</h2>
        <p>Comunica um problema numa sala e acompanha o pedido com o código que recebes.</p>
    </a>
</section>

<section class="consulta-rapida">
    <h2>Já reportaste uma avaria?</h2>
    <form class="linha-pesquisa" action="${pageContext.request.contextPath}/avarias/consultar" method="get">
        <input type="text" name="codigo" placeholder="Código do pedido, por exemplo AV-7K3P9Q" maxlength="9" required aria-label="Código do pedido">
        <button class="botao" type="submit">Consultar</button>
    </form>
</section>

<%@ include file="includes/rodape.jspf" %>
