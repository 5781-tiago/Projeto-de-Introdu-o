<%@ page isErrorPage="true" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="titulo" value="Erro" />
<%@ include file="includes/cabecalho.jspf" %>

<div class="resumo">
    <c:choose>
        <c:when test="${pageContext.errorData.statusCode == 404}">
            <h1>Página não encontrada</h1>
            <p>O endereço que procuras não existe.</p>
        </c:when>
        <c:otherwise>
            <h1>Ocorreu um erro</h1>
            <p>Não foi possível concluir o pedido. Tenta novamente daqui a pouco.</p>
        </c:otherwise>
    </c:choose>
    <a class="botao" href="${pageContext.request.contextPath}/inicio">Voltar ao início</a>
</div>

<%@ include file="includes/rodape.jspf" %>
