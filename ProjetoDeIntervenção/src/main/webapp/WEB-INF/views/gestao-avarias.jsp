<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<c:set var="titulo" value="Avarias" />
<%@ include file="includes/cabecalho.jspf" %>

<nav class="separadores">
    <a href="${pageContext.request.contextPath}/gestao/inscricoes">Pré-inscrições</a>
    <a class="ativo" href="${pageContext.request.contextPath}/gestao/avarias">Avarias</a>
</nav>

<h1>Pedidos de assistência técnica <span class="contador">${fn:length(avarias)}</span></h1>

<c:choose>
    <c:when test="${empty avarias}">
        <p class="vazio">Ainda não há avarias registadas.</p>
    </c:when>
    <c:otherwise>
        <div class="tabela-scroll">
            <table class="tabela">
                <thead>
                <tr>
                    <th>Data</th>
                    <th>Sala</th>
                    <th>Tipo</th>
                    <th>Descrição</th>
                    <th>Reportado por</th>
                    <th>Estado</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="a" items="${avarias}">
                    <tr>
                        <td><c:out value="${a.dataRegistoFormatada}" /></td>
                        <td><c:out value="${a.sala}" /></td>
                        <td><c:out value="${a.tipo}" /></td>
                        <td><c:out value="${a.descricao}" /></td>
                        <td><c:out value="${a.reportadoPor}" /></td>
                        <td>
                            <form class="form-estado" action="${pageContext.request.contextPath}/gestao/avarias" method="post">
                                <input type="hidden" name="id" value="${a.id}">
                                <select name="estado" onchange="this.form.submit()">
                                    <c:forEach var="e" items="${estados}">
                                        <option value="${e}" ${e eq a.estado ? 'selected' : ''}>${e}</option>
                                    </c:forEach>
                                </select>
                                <noscript><button type="submit">Guardar</button></noscript>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </c:otherwise>
</c:choose>

<%@ include file="includes/rodape.jspf" %>
