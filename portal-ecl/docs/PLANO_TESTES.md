# Plano de testes e evidências

Para cada teste, preencher a data, o resultado, a evidência (nome do ficheiro da captura de ecrã), o responsável e a correção feita, se houve.

| Teste | Passos | Resultado esperado | Data | Resultado | Evidência | Responsável | Correção |
|---|---|---|---|---|---|---|---|
| **T01** Acesso por outro computador | Num segundo PC da rede, abrir `http://IP_DA_VM:8080/ecl/` | O portal abre pelo IP do servidor | | | | | |
| **T02** Catálogo | Abrir Oferta formativa; filtrar por modalidade «Curso de Aprendizagem»; pesquisar «turismo»; abrir o detalhe de um curso | Cursos e páginas de detalhe acessíveis; horas, componentes e fonte visíveis; cursos sem dados mostram «A confirmar» | | | | | |
| **T03** Pré-inscrição válida | No detalhe de um curso, carregar em «Tenho interesse»; preencher com dados fictícios e enviar | O curso vem já escolhido; aparece o código PI-XXXXXX e o estado «Recebida»; o pedido surge no painel de gestão | | | | | |
| **T04** Pré-inscrição inválida | Enviar com email `teste@` e sem curso (desativar a validação do navegador nas ferramentas de programador para testar o servidor) | O registo não é feito; aparecem erros junto ao email e ao curso; os outros valores mantêm-se | | | | | |
| **T05** Reporte de avaria válido | Reportar uma avaria fictícia na sala LAB1, categoria Projetor, prioridade Elevada | Aparece o código AV-XXXXXX com sala, categoria, data e estado «Aberta»; a consulta pelo código funciona | | | | | |
| **T06** Reporte inválido | Enviar sem sala e com a descrição «Falha» | Erros junto à sala e à descrição; nada é gravado | | | | | |
| **T07** Gestão de estados | Entrar como gestor; mudar uma pré-inscrição para «Em análise»; abrir uma ocorrência, mudar para «Em resolução» e escrever uma observação | A tabela mostra o novo estado; o histórico regista a alteração; `SELECT estado FROM ocorrencias` na BD reflete a mudança | | | | | |
| **T08** Acesso não autorizado | Sem sessão iniciada, abrir `/ecl/gestao/ocorrencias`; entrar como técnico e abrir `/ecl/gestao/inscricoes` | Sem sessão: redireciona para o login. Técnico: página «Acesso não autorizado» | | | | | |
| **T09** Reinício do servidor | `sudo reboot` na VM; esperar; abrir o portal no segundo PC | O serviço volta sozinho e os registos anteriores continuam lá | | | | | |
| **T10** Backup / recuperação | Executar `backup.sh`; criar uma pré-inscrição nova; executar `repor.sh` com a cópia | A cópia é criada; depois da reposição a pré-inscrição nova desaparece (a BD voltou ao estado da cópia) | | | | | |

## Testes adicionais de segurança

| Teste | Passos | Resultado esperado | Resultado |
|---|---|---|---|
| Escape de HTML | Escrever `<script>alert(1)</script>` na descrição de uma avaria | Aparece como texto, sem executar | |
| SQL injection | Pesquisar `' OR '1'='1` no catálogo | Não devolve todos os cursos nem dá erro de SQL | |
| Password errada | Tentar entrar com uma password errada | Mensagem genérica «Utilizador ou password incorretos» | |
| Página inexistente | Abrir `/ecl/nao-existe` | Página de erro própria, sem detalhes técnicos | |
