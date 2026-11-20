<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<c:set var="titulo" value="Oferta formativa" />
<c:set var="menu" value="cursos" />
<%@ include file="includes/cabecalho.jspf" %>

<h1>Oferta formativa</h1>
<p class="subtitulo">Informação consultada no portal oficial da Escola de Comércio de Lisboa. Cada curso indica a fonte e a data de verificação.</p>

<form class="filtros" action="${pageContext.request.contextPath}/cursos" method="get">
    <div>
        <label for="q">Pesquisar</label>
        <input type="search" id="q" name="q" value="${fn:escapeXml(q)}" placeholder="Nome do curso ou área" maxlength="60">
    </div>
    <div>
        <label for="modalidade">Modalidade</label>
        <select id="modalidade" name="modalidade">
            <option value="">Todas</option>
            <c:forEach var="m" items="${modalidades}">
                <option value="${fn:escapeXml(m)}" ${m eq modalidadeEscolhida ? 'selected' : ''}><c:out value="${m}" /></option>
            </c:forEach>
        </select>
    </div>
    <div>
        <label for="area">Área</label>
        <select id="area" name="area">
            <option value="">Todas</option>
            <c:forEach var="a" items="${areas}">
                <option value="${fn:escapeXml(a)}" ${a eq areaEscolhida ? 'selected' : ''}><c:out value="${a}" /></option>
            </c:forEach>
        </select>
    </div>
    <div class="filtros-botoes">
        <button class="botao" type="submit">Filtrar</button>
        <a class="botao botao-secundario" href="${pageContext.request.contextPath}/cursos">Limpar</a>
    </div>
</form>

<p class="contagem">${fn:length(cursos)} curso(s) encontrado(s)</p>

<c:if test="${empty cursos}">
    <p class="vazio">Nenhum curso corresponde à pesquisa.</p>
</c:if>

<c:set var="areaAtual" value="" />
<c:forEach var="curso" items="${cursos}">
    <c:if test="${curso.area ne areaAtual}">
        <c:if test="${not empty areaAtual}"></div></c:if>
        <h2 class="grupo"><c:out value="${curso.area}" /></h2>
        <div class="lista-cursos">
        <c:set var="areaAtual" value="${curso.area}" />
    </c:if>
    <article class="curso">
        <h3><a href="${pageContext.request.contextPath}/cursos/detalhe?id=${curso.id}"><c:out value="${curso.nome}" /></a></h3>
        <p class="etiquetas">
            <span class="etiqueta"><c:out value="${curso.modalidade}" /></span>
            <span class="etiqueta"><c:out value="${curso.nivel}" /></span>
        </p>
        <p class="horas">
            <c:choose>
                <c:when test="${not empty curso.horasTotal}"><strong>${curso.horasTotal} horas</strong></c:when>
                <c:otherwise><span class="confirmar">Carga horária a confirmar</span></c:otherwise>
            </c:choose>
        </p>
        <div class="curso-acoes">
            <a class="botao botao-pequeno botao-secundario" href="${pageContext.request.contextPath}/cursos/detalhe?id=${curso.id}">Ver detalhe</a>
            <a class="botao botao-pequeno" href="${pageContext.request.contextPath}/pre-inscricao?curso=${curso.id}">Tenho interesse</a>
        </div>
    </article>
</c:forEach>
<c:if test="${not empty areaAtual}"></div></c:if>

<%@ include file="includes/rodape.jspf" %>
