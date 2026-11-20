<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<c:set var="titulo" value="Reportar avaria" />
<c:set var="menu" value="avarias" />
<%@ include file="includes/cabecalho.jspf" %>

<h1>Reportar avaria</h1>
<p class="subtitulo">Comunica um problema numa sala de formação. Todos os campos são obrigatórios.</p>

<c:if test="${not empty erros}">
    <div class="alerta alerta-erro" role="alert">Há campos por corrigir no formulário.</div>
</c:if>

<form class="formulario" action="${pageContext.request.contextPath}/avarias" method="post">

    <div class="linha">
        <div>
            <label for="nome">O teu nome</label>
            <input type="text" id="nome" name="nome" value="${fn:escapeXml(nome)}" minlength="3" maxlength="120" required
                   class="${not empty erros.nome ? 'invalido' : ''}">
            <c:if test="${not empty erros.nome}"><span class="erro"><c:out value="${erros.nome}" /></span></c:if>
        </div>
        <div>
            <label for="perfil">Perfil</label>
            <select id="perfil" name="perfil" required class="${not empty erros.perfil ? 'invalido' : ''}">
                <option value="">Escolhe</option>
                <c:forEach var="p" items="${perfis}">
                    <option value="${p}" ${p eq perfil ? 'selected' : ''}>${p}</option>
                </c:forEach>
            </select>
            <c:if test="${not empty erros.perfil}"><span class="erro"><c:out value="${erros.perfil}" /></span></c:if>
        </div>
    </div>

    <div class="linha">
        <div>
            <label for="sala">Sala</label>
            <select id="sala" name="sala" required class="${not empty erros.sala ? 'invalido' : ''}">
                <option value="">Escolhe a sala</option>
                <c:forEach var="s" items="${salas}">
                    <option value="${s.id}" ${s.id.toString() eq salaId ? 'selected' : ''}><c:out value="${s.descricao}" /></option>
                </c:forEach>
            </select>
            <c:if test="${not empty erros.sala}"><span class="erro"><c:out value="${erros.sala}" /></span></c:if>
        </div>
        <div>
            <label for="categoria">Equipamento ou serviço</label>
            <select id="categoria" name="categoria" required class="${not empty erros.categoria ? 'invalido' : ''}">
                <option value="">Escolhe</option>
                <c:forEach var="cat" items="${categorias}">
                    <option value="${cat}" ${cat eq categoria ? 'selected' : ''}>${cat}</option>
                </c:forEach>
            </select>
            <c:if test="${not empty erros.categoria}"><span class="erro"><c:out value="${erros.categoria}" /></span></c:if>
        </div>
    </div>

    <label for="descricao">Descrição do problema</label>
    <textarea id="descricao" name="descricao" rows="4" minlength="10" maxlength="1000" required
              placeholder="O que acontece, desde quando e em que posto ou equipamento."
              class="${not empty erros.descricao ? 'invalido' : ''}"><c:out value="${descricao}" /></textarea>
    <c:if test="${not empty erros.descricao}"><span class="erro"><c:out value="${erros.descricao}" /></span></c:if>

    <fieldset class="prioridades ${not empty erros.prioridade ? 'invalido' : ''}">
        <legend>Prioridade</legend>
        <label class="opcao"><input type="radio" name="prioridade" value="Elevada" required ${prioridade eq 'Elevada' ? 'checked' : ''}>
            <span><strong>Elevada</strong> — impede a aula ou afeta a sala inteira (projetor, rede da sala).</span></label>
        <label class="opcao"><input type="radio" name="prioridade" value="Normal" ${prioridade eq 'Normal' ? 'checked' : ''}>
            <span><strong>Normal</strong> — afeta um posto ou equipamento, mas a aula continua.</span></label>
        <label class="opcao"><input type="radio" name="prioridade" value="Baixa" ${prioridade eq 'Baixa' ? 'checked' : ''}>
            <span><strong>Baixa</strong> — incómodo sem impacto na aula (mobiliário, estética).</span></label>
    </fieldset>
    <c:if test="${not empty erros.prioridade}"><span class="erro"><c:out value="${erros.prioridade}" /></span></c:if>

    <button class="botao" type="submit">Registar avaria</button>
</form>

<%@ include file="includes/rodape.jspf" %>
