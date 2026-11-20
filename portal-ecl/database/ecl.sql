-- Portal ECL — Serviços
-- Projeto académico demonstrativo. Todos os dados de pré-inscrições e ocorrências são fictícios.

DROP DATABASE IF EXISTS ecl;
CREATE DATABASE ecl CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE ecl;

CREATE TABLE cursos (
    id_curso INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(150) NOT NULL,
    modalidade VARCHAR(60) NOT NULL,
    area VARCHAR(60) NOT NULL,
    nivel VARCHAR(80) NOT NULL,
    horas_total INT NULL,
    componentes TEXT NULL,
    perfil TEXT NULL,
    saidas_profissionais TEXT NULL,
    fonte VARCHAR(255) NOT NULL,
    data_verificacao DATE NOT NULL,
    ativo BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE utilizadores (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(120) NOT NULL,
    identificador VARCHAR(50) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    perfil ENUM('Gestor', 'Técnico') NOT NULL,
    ativo BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE salas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    codigo VARCHAR(20) NOT NULL UNIQUE,
    edificio VARCHAR(40) NOT NULL,
    piso VARCHAR(20) NOT NULL
);

CREATE TABLE pre_inscricoes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    codigo VARCHAR(12) NOT NULL UNIQUE,
    id_curso INT NOT NULL,
    nome VARCHAR(120) NOT NULL,
    email VARCHAR(120) NOT NULL,
    telefone VARCHAR(9) NULL,
    observacoes VARCHAR(500) NULL,
    estado ENUM('Recebida', 'Em análise', 'Contactada') NOT NULL DEFAULT 'Recebida',
    criado_em TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    atualizado_em TIMESTAMP NULL,
    FOREIGN KEY (id_curso) REFERENCES cursos(id_curso)
);

CREATE TABLE ocorrencias (
    id INT AUTO_INCREMENT PRIMARY KEY,
    codigo VARCHAR(12) NOT NULL UNIQUE,
    nome_reportador VARCHAR(120) NOT NULL,
    perfil_reportador ENUM('Formando', 'Formador') NOT NULL,
    id_sala INT NOT NULL,
    categoria ENUM('PC', 'Projetor', 'Rede', 'Wi-Fi', 'Impressora', 'Software', 'Mobiliário', 'Outro') NOT NULL,
    descricao VARCHAR(1000) NOT NULL,
    prioridade ENUM('Baixa', 'Normal', 'Elevada') NOT NULL,
    estado ENUM('Aberta', 'Em análise', 'Em resolução', 'Resolvida') NOT NULL DEFAULT 'Aberta',
    resolucao VARCHAR(1000) NULL,
    id_utilizador INT NULL,
    criado_em TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    atualizado_em TIMESTAMP NULL,
    FOREIGN KEY (id_sala) REFERENCES salas(id),
    FOREIGN KEY (id_utilizador) REFERENCES utilizadores(id)
);

CREATE TABLE historico_estados (
    id INT AUTO_INCREMENT PRIMARY KEY,
    tipo ENUM('Pré-inscrição', 'Ocorrência') NOT NULL,
    id_registo INT NOT NULL,
    estado_anterior VARCHAR(30) NOT NULL,
    estado_novo VARCHAR(30) NOT NULL,
    id_utilizador INT NOT NULL,
    alterado_em TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_utilizador) REFERENCES utilizadores(id)
);

INSERT INTO cursos (nome, modalidade, area, nivel, horas_total, componentes, perfil, saidas_profissionais, fonte, data_verificacao) VALUES
('Técnico de Comércio', 'Curso Profissional', 'Comércio e Marketing', 'Nível 4 · 12.º ano', 3230,
 'Sociocultural: 1000 h;Científica: 500 h;Tecnológica: 1730 h (UFCD 1100 h + Formação em Contexto de Trabalho 630 h)',
 'Profissional de comércio versátil, capaz de organizar e realizar a venda de produtos e serviços, em loja e online, garantindo a satisfação do cliente.',
 'Venda a retalho de bens e serviços;Empresas de diferentes áreas de atividade',
 'https://escolacomerciolisboa.pt/escola-profissional/cursos-profissionais/tecnico-de-comercio/', '2026-11-20'),

('Técnico de Vendas e Marketing', 'Curso Profissional', 'Comércio e Marketing', 'Nível 4 · 12.º ano', 3480,
 'Sociocultural: 1000 h;Científica: 500 h;Tecnológica: 1980 h (UFCD 1350 h + Formação em Contexto de Trabalho 630 h)',
 'Profissional autónomo e bom comunicador, que planeia, coordena e executa atividades de promoção, comercialização e venda de produtos e serviços, em articulação com o responsável de marketing e vendas.',
 'Departamentos de vendas e marketing de empresas de vários setores;Empresas de marketing e comunicação',
 'https://escolacomerciolisboa.pt/escola-profissional/cursos-profissionais/tecnico-de-vendas-marketing/', '2026-11-20'),

('Técnico de Vitrinismo (Visual Merchandising)', 'Curso Profissional', 'Comércio e Marketing', 'Nível 4 · 12.º ano', 3480,
 'Sociocultural: 1000 h;Científica: 500 h;Tecnológica: 1980 h (UFCD 1350 h + Formação em Contexto de Trabalho 630 h)',
 'Profissional que junta sentido estético e criativo à dinâmica comercial, concebendo e montando a exposição e a decoração de espaços de venda para valorizar produtos e serviços.',
 'Departamentos de visual merchandising;Empresas especializadas na área;Trabalho por conta própria',
 'https://escolacomerciolisboa.pt/escola-profissional/cursos-profissionais/tecnico-de-vitrinismo/', '2026-11-20'),

('Técnico de Comunicação – Marketing, Relações Públicas e Publicidade', 'Curso Profissional', 'Comunicação e Eventos', 'Nível 4 · 12.º ano', 3230,
 'Sociocultural: 1000 h;Científica: 500 h;Tecnológica: 1730 h (UFCD 1100 h + Formação em Contexto de Trabalho 630 h)',
 'Profissional polivalente que analisa situações de comunicação e propõe soluções para diferentes públicos, colaborando em campanhas, gestão de marca, eventos e comunicação interna e externa.',
 'Técnico/a de Comunicação;Assistente de Marketing;Assistente de Relações Públicas;Assistente de Publicidade;Gestor/a de Conteúdos Digitais;Assistente de Marketing Digital;Community Manager (nível inicial);Agências de comunicação e publicidade',
 'https://escolacomerciolisboa.pt/escola-profissional/cursos-profissionais/tecnico-de-comunicacao-2/', '2026-11-20'),

('Técnico de Organização de Eventos', 'Curso Profissional', 'Comunicação e Eventos', 'Nível 4 · 12.º ano', 3480,
 'Sociocultural: 1000 h;Científica: 500 h;Tecnológica: 1980 h (UFCD 1350 h + Formação em Contexto de Trabalho 630 h)',
 'Profissional que cria e gere eventos, coordenando programação, planeamento, contratação, orçamento, equipas, relações públicas e avaliação.',
 'Event Manager em empresas de organização de eventos;Comunicação e marketing;Design e produção televisiva',
 'https://escolacomerciolisboa.pt/escola-profissional/cursos-profissionais/tecnico-de-organizacao-de-eventos/', '2026-11-20'),

('Técnico de Multimédia', 'Curso Profissional', 'Comunicação e Eventos', 'Nível 4 · 12.º ano', NULL,
 NULL, NULL, NULL,
 'A confirmar junto da ECL (curso listado em escolacomerciolisboa.pt/escola-profissional/cursos-profissionalizantes-ecl/ sem página pública)', '2026-11-20'),

('Técnico de Turismo', 'Curso Profissional', 'Turismo e Restauração', 'Nível 4 · 12.º ano', 3310,
 'Sociocultural: 1000 h;Científica: 500 h;Tecnológica: 1810 h (UFCD 1180 h + Formação em Contexto de Trabalho 630 h)',
 'Profissional polivalente com competências de informação, receção, animação e organização de eventos em agências de viagens, hotelaria, autarquias, museus, companhias aéreas e operadores turísticos.',
 'Técnico/a de Turismo;Companhias aéreas;Postos de turismo, museus e monumentos;Agências de viagens e operadores turísticos;Empresas de animação turística;Alojamentos turísticos;Empresas de transporte turístico',
 'https://escolacomerciolisboa.pt/escola-profissional/cursos-profissionais/tecnico-de-turismo/', '2026-11-20'),

('Técnico de Alojamento Hoteleiro', 'Curso Profissional', 'Turismo e Restauração', 'Nível 4 · 12.º ano', NULL,
 NULL, NULL, NULL,
 'A confirmar junto da ECL (curso listado em escolacomerciolisboa.pt/escola-profissional/cursos-profissionalizantes-ecl/ sem página pública)', '2026-11-20'),

('Técnico de Cozinha-Pastelaria', 'Curso Profissional', 'Turismo e Restauração', 'Nível 4 · 12.º ano', 3310,
 'Sociocultural: 1000 h (Português 320, Inglês 220, Área de Integração 220, TIC 100, Educação Física 140);Científica: 500 h (Economia 200, Matemática 200, Psicologia 100);Tecnológica: 1810 h (UFCD 1180 h + Formação em Contexto de Trabalho 630 h)',
 'Profissional que planeia, prepara e confeciona produtos alimentares cumprindo normas de higiene, segurança e qualidade, colaborando na gestão da restauração, ementas, custos e stocks.',
 'Cozinheiro/a;Pasteleiro/a;Ajudante de Cozinha;Técnico/a de Restauração;Assistente de Catering;Hotéis, restaurantes, pastelarias e empresas de eventos;Empreendedor/a na restauração',
 'https://escolacomerciolisboa.pt/escola-profissional/cursos-profissionais/tecnico-de-cozinha-pastelaria/', '2026-11-20'),

('Técnico de Desenvolvimento de Software', 'Curso Profissional', 'Informática', 'Nível 4 · 12.º ano', 3310,
 'Sociocultural: 1000 h (Português 320, Inglês 220, Área de Integração 220, TIC 100, Educação Física 140);Científica: 500 h (Matemática 300, Física e Química 200);Tecnológica: 1810 h (UFCD 1180 h + Formação em Contexto de Trabalho 630 h)',
 'Profissional analítico que levanta requisitos, desenha algoritmos e desenvolve, testa e mantém aplicações informáticas em equipa.',
 'Programador/a;Técnico/a de Desenvolvimento de Software;Desenvolvedor/a Web;Desenvolvedor/a de Aplicações Móveis;Técnico/a de Testes de Software;Empresas tecnológicas e startups',
 'https://escolacomerciolisboa.pt/escola-profissional/cursos-profissionais/tecnico-de-informatica-sistemas-2/', '2026-11-20'),

('Técnico de Informática de Gestão', 'Curso de Aprendizagem', 'Informática', 'Nível 4 · 12.º ano', NULL,
 NULL,
 'Profissional que integra soluções informáticas na gestão das organizações, analisa necessidades, apoia decisões e colabora na implementação e manutenção de sistemas de informação.',
 'Técnico/a de Informática de Gestão;Técnico/a de Sistemas de Informação;Assistente de Gestão Administrativa;Operador/a de Sistemas de Gestão (ERP);Técnico/a de Análise de Dados (nível inicial)',
 'https://escolacomerciolisboa.pt/escola-profissional/cursos-profissionais/tecnico-de-informatica-sistemas-3/', '2026-11-20'),

('Técnico de Sistemas de Computação e Redes', 'Curso de Aprendizagem', 'Informática', 'Nível 4 · 12.º ano', NULL,
 NULL,
 'Técnico que instala, configura e administra sistemas informáticos e redes, diagnosticando e resolvendo problemas e garantindo segurança e desempenho.',
 'Técnico/a de Redes;Técnico/a de Sistemas Informáticos;Administrador/a de Redes (nível inicial);Técnico/a de Suporte Informático / Helpdesk;Instalador/a de Redes e Equipamentos',
 'https://escolacomerciolisboa.pt/escola-profissional/cursos-profissionais/tecnico-de-informatica-redes-2/', '2026-11-20'),

('Empregado de Restaurante-Bar', 'Curso de Educação e Formação (CEF) Tipo 3', 'Turismo e Restauração', 'Nível 2 · 9.º ano', 1218,
 'Sociocultural: 192 h;Científica: 66 h;Tecnológica: 750 h (UFCD);Formação em Contexto de Trabalho: 210 h',
 'Profissional que organiza, prepara e executa o serviço de restaurante e bar, cumprindo normas de higiene e segurança e trabalhando em equipa.',
 'Estabelecimentos de restauração e bebidas;Unidades hoteleiras',
 'https://escolacomerciolisboa.pt/escola-profissional/cursos-profissionais/cef-empregado-restaurante-bar/', '2026-11-20'),

('Assistente Administrativo', 'Curso de Educação e Formação (CEF) Tipo 3', 'Comércio e Marketing', 'Nível 2 · 9.º ano', NULL,
 NULL, NULL, NULL,
 'A confirmar junto da ECL (curso listado em escolacomerciolisboa.pt/escola-profissional/cursos-profissionalizantes-ecl/ sem página própria)', '2026-11-20');

INSERT INTO utilizadores (nome, identificador, password_hash, perfil) VALUES
('Gestor de demonstração', 'gestor', 'pbkdf2$210000$nj+ub24a94XtV3UI8HD6+g==$esN1RJYR1rXpld1MjizINwxnH5lC/b19ew9dfuwG6RE=', 'Gestor'),
('Técnico de demonstração', 'tecnico', 'pbkdf2$210000$LWSxaQF89CLAn8HrCt8qXA==$xRfbFGQwR09OczP2dwHnQ3gpH50gShcpUl9OrmVdteo=', 'Técnico');

INSERT INTO salas (codigo, edificio, piso) VALUES
('A0.01', 'Edifício A', 'Piso 0'), ('A0.02', 'Edifício A', 'Piso 0'),
('A1.01', 'Edifício A', 'Piso 1'), ('A1.02', 'Edifício A', 'Piso 1'), ('A1.03', 'Edifício A', 'Piso 1'),
('B0.01', 'Edifício B', 'Piso 0'), ('B1.01', 'Edifício B', 'Piso 1'), ('B1.02', 'Edifício B', 'Piso 1'),
('LAB1', 'Edifício B', 'Piso 2'), ('LAB2', 'Edifício B', 'Piso 2'),
('Auditório', 'Edifício A', 'Piso 0'), ('Biblioteca', 'Edifício A', 'Piso 1');

INSERT INTO pre_inscricoes (codigo, id_curso, nome, email, telefone, observacoes, estado, criado_em) VALUES
('PI-TESTE1', 10, 'Rui Exemplo', 'rui.exemplo@exemplo.pt', '910000001', 'Gostava de saber o horário.', 'Recebida', '2026-11-18 10:15:00'),
('PI-TESTE2', 7, 'Ana Fictícia', 'ana.ficticia@exemplo.pt', NULL, NULL, 'Em análise', '2026-11-18 14:40:00'),
('PI-TESTE3', 12, 'Marta Demo', 'marta.demo@exemplo.pt', '210000002', 'Já tenho o 9.º ano.', 'Contactada', '2026-11-19 09:05:00');

INSERT INTO ocorrencias (codigo, nome_reportador, perfil_reportador, id_sala, categoria, descricao, prioridade, estado, resolucao, criado_em) VALUES
('AV-TESTE1', 'Formador Exemplo', 'Formador', 9, 'Projetor', 'O projetor não liga e não é possível dar a aula.', 'Elevada', 'Aberta', NULL, '2026-11-18 08:50:00'),
('AV-TESTE2', 'Formando Exemplo', 'Formando', 10, 'PC', 'O computador do posto 7 não arranca o Windows.', 'Normal', 'Em resolução', NULL, '2026-11-18 11:20:00'),
('AV-TESTE3', 'Formanda Demo', 'Formando', 3, 'Mobiliário', 'Uma cadeira está partida junto à janela.', 'Baixa', 'Resolvida', 'Cadeira substituída.', '2026-11-17 15:30:00');

CREATE USER IF NOT EXISTS 'ecl_app'@'localhost' IDENTIFIED BY 'MUDAR_ESTA_PASSWORD';
GRANT SELECT, INSERT, UPDATE ON ecl.* TO 'ecl_app'@'localhost';
FLUSH PRIVILEGES;
