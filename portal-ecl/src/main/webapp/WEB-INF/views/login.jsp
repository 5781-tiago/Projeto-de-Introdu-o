<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<c:set var="titulo" value="Área reservada" />
<c:set var="menu" value="gestao" />
<%@ include file="includes/cabecalho.jspf" %>

<div class="caixa-login">
    <h1>Área reservada</h1>
    <p class="subtitulo">Acesso para gestores e técnicos.</p>

    <c:if test="${not empty erro}">
        <div class="alerta alerta-erro" role="alert"><c:out value="${erro}" /></div>
    </c:if>

    <form class="formulario" action="${pageContext.request.contextPath}/login" method="post">
        <label for="utilizador">Utilizador</label>
        <input type="text" id="utilizador" name="utilizador" value="${fn:escapeXml(utilizador)}" maxlength="50" required autocomplete="username">

        <label for="password">Password</label>
        <input type="password" id="password" name="password" maxlength="100" required autocomplete="current-password">

        <button class="botao" type="submit">Entrar</button>
    </form>
</div>

<%@ include file="includes/rodape.jspf" %>
