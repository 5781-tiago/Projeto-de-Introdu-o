<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="titulo" value="Resumo" />
<c:set var="menu" value="gestao" />
<c:set var="separador" value="resumo" />
<%@ include file="/WEB-INF/views/includes/cabecalho.jspf" %>
<%@ include file="/WEB-INF/views/gestao/menu.jspf" %>

<h1>Resumo</h1>

<c:if test="${elevadasAbertas > 0}">
    <div class="alerta alerta-aviso" role="status">
        Há <strong>${elevadasAbertas}</strong> ocorrência(s) de prioridade elevada por resolver.
        <a href="${pageContext.request.contextPath}/gestao/ocorrencias?prioridade=Elevada">Ver</a>
    </div>
</c:if>

<div class="paineis">
    <c:if test="${sessionScope.utilizador.perfil eq 'Gestor'}">
        <section class="painel">
            <h2>Pré-inscrições por estado</h2>
            <c:set var="totalInscricoes" value="0" />
            <c:forEach var="e" items="${contagemInscricoes}"><c:set var="totalInscricoes" value="${totalInscricoes + e.value}" /></c:forEach>
            <c:forEach var="e" items="${contagemInscricoes}">
                <c:url var="ligacao" value="/gestao/inscricoes"><c:param name="estado" value="${e.key}" /></c:url>
                <a class="barra-linha" href="${ligacao}">
                    <span class="barra-rotulo"><c:out value="${e.key}" /></span>
                    <span class="barra-fundo"><span class="barra-valor" style="width: ${totalInscricoes > 0 ? e.value * 100 / totalInscricoes : 0}%"></span></span>
                    <span class="barra-numero">${e.value}</span>
                </a>
            </c:forEach>
            <p class="total">Total: <strong>${totalInscricoes}</strong></p>
        </section>
    </c:if>

    <section class="painel">
        <h2>Ocorrências por estado</h2>
        <c:set var="totalOcorrencias" value="0" />
        <c:forEach var="e" items="${contagemOcorrencias}"><c:set var="totalOcorrencias" value="${totalOcorrencias + e.value}" /></c:forEach>
        <c:forEach var="e" items="${contagemOcorrencias}">
            <c:url var="ligacao" value="/gestao/ocorrencias"><c:param name="estado" value="${e.key}" /></c:url>
            <a class="barra-linha" href="${ligacao}">
                <span class="barra-rotulo"><c:out value="${e.key}" /></span>
                <span class="barra-fundo"><span class="barra-valor" style="width: ${totalOcorrencias > 0 ? e.value * 100 / totalOcorrencias : 0}%"></span></span>
                <span class="barra-numero">${e.value}</span>
            </a>
        </c:forEach>
        <p class="total">Total: <strong>${totalOcorrencias}</strong></p>
    </section>
</div>

<%@ include file="/WEB-INF/views/includes/rodape.jspf" %>
