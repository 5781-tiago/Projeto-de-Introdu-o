<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<c:set var="titulo" value="Pré-inscrições" />
<%@ include file="includes/cabecalho.jspf" %>

<nav class="separadores">
    <a class="ativo" href="${pageContext.request.contextPath}/gestao/inscricoes">Pré-inscrições</a>
    <a href="${pageContext.request.contextPath}/gestao/avarias">Avarias</a>
</nav>

<h1>Pré-inscrições <span class="contador">${fn:length(inscricoes)}</span></h1>

<c:choose>
    <c:when test="${empty inscricoes}">
        <p class="vazio">Ainda não há pré-inscrições.</p>
    </c:when>
    <c:otherwise>
        <div class="tabela-scroll">
            <table class="tabela">
                <thead>
                <tr>
                    <th>Data</th>
                    <th>Nome</th>
                    <th>Curso</th>
                    <th>Email</th>
                    <th>Telefone</th>
                    <th>Nascimento</th>
                    <th>Observações</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="p" items="${inscricoes}">
                    <tr>
                        <td><c:out value="${p.dataRegistoFormatada}" /></td>
                        <td><c:out value="${p.nome}" /></td>
                        <td><c:out value="${p.nomeCurso}" /></td>
                        <td><c:out value="${p.email}" /></td>
                        <td><c:out value="${p.telefone}" /></td>
                        <td><c:out value="${p.dataNascimentoFormatada}" /></td>
                        <td><c:out value="${p.observacoes}" /></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </c:otherwise>
</c:choose>

<%@ include file="includes/rodape.jspf" %>
