<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<c:set var="titulo" value="${curso.nome}" />
<c:set var="menu" value="cursos" />
<%@ include file="includes/cabecalho.jspf" %>

<p class="migalhas"><a href="${pageContext.request.contextPath}/cursos">Oferta formativa</a> › <c:out value="${curso.area}" /></p>

<div class="detalhe">
    <div class="detalhe-principal">
        <h1><c:out value="${curso.nome}" /></h1>
        <p class="etiquetas">
            <span class="etiqueta"><c:out value="${curso.modalidade}" /></span>
            <span class="etiqueta"><c:out value="${curso.nivel}" /></span>
            <span class="etiqueta"><c:out value="${curso.area}" /></span>
        </p>

        <h2>Perfil de saída</h2>
        <c:choose>
            <c:when test="${not empty curso.perfil}"><p><c:out value="${curso.perfil}" /></p></c:when>
            <c:otherwise><p class="confirmar">A confirmar junto da Escola de Comércio de Lisboa.</p></c:otherwise>
        </c:choose>

        <h2>Principais saídas profissionais</h2>
        <c:choose>
            <c:when test="${not empty curso.listaSaidas}">
                <ul>
                    <c:forEach var="s" items="${curso.listaSaidas}"><li><c:out value="${s}" /></li></c:forEach>
                </ul>
            </c:when>
            <c:otherwise><p class="confirmar">A confirmar junto da Escola de Comércio de Lisboa.</p></c:otherwise>
        </c:choose>
    </div>

    <aside class="detalhe-lateral">
        <div class="caixa-info">
            <h2>Duração total</h2>
            <c:choose>
                <c:when test="${not empty curso.horasTotal}"><p class="numero">${curso.horasTotal} horas</p></c:when>
                <c:otherwise><p class="confirmar">A confirmar</p></c:otherwise>
            </c:choose>

            <h2>Carga horária por componente</h2>
            <c:choose>
                <c:when test="${not empty curso.listaComponentes}">
                    <ul class="componentes">
                        <c:forEach var="comp" items="${curso.listaComponentes}"><li><c:out value="${comp}" /></li></c:forEach>
                    </ul>
                </c:when>
                <c:otherwise><p class="confirmar">A confirmar</p></c:otherwise>
            </c:choose>

            <a class="botao botao-largo" href="${pageContext.request.contextPath}/pre-inscricao?curso=${curso.id}">Tenho interesse</a>
        </div>

        <div class="fonte">
            <strong>Fonte:</strong>
            <c:choose>
                <c:when test="${curso.fonteLink}">
                    <a href="${fn:escapeXml(curso.fonte)}" target="_blank" rel="noopener noreferrer">página oficial do curso</a>
                </c:when>
                <c:otherwise><c:out value="${curso.fonte}" /></c:otherwise>
            </c:choose>
            <br><strong>Verificado em:</strong> <c:out value="${curso.dataVerificacaoFormatada}" />
        </div>
    </aside>
</div>

<%@ include file="includes/rodape.jspf" %>
