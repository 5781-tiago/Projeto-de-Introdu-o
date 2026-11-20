<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<c:set var="titulo" value="Entrar na gestão" />
<%@ include file="includes/cabecalho.jspf" %>

<div class="caixa-login">
    <h1>Área de gestão</h1>
    <p class="subtitulo">Acesso reservado aos serviços da escola.</p>

    <c:if test="${not empty erro}">
        <div class="alerta alerta-erro"><c:out value="${erro}" /></div>
    </c:if>

    <form class="formulario" action="${pageContext.request.contextPath}/login" method="post">
        <label for="utilizador">Utilizador</label>
        <input type="text" id="utilizador" name="utilizador" value="${fn:escapeXml(utilizador)}" required>

        <label for="password">Password</label>
        <input type="password" id="password" name="password" required>

        <button class="botao" type="submit">Entrar</button>
    </form>
</div>

<%@ include file="includes/rodape.jspf" %>
