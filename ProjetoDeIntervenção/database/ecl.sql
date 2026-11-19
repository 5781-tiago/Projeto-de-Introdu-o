DROP DATABASE IF EXISTS ecl;
CREATE DATABASE ecl CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE ecl;

CREATE TABLE cursos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(120) NOT NULL,
    tipo VARCHAR(60) NOT NULL,
    nivel INT NOT NULL,
    duracao VARCHAR(30) NOT NULL,
    descricao TEXT NOT NULL
);

CREATE TABLE pre_inscricoes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(120) NOT NULL,
    email VARCHAR(120) NOT NULL,
    telefone VARCHAR(9) NOT NULL,
    data_nascimento DATE NOT NULL,
    curso_id INT NOT NULL,
    observacoes TEXT,
    data_registo TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (curso_id) REFERENCES cursos(id)
);

CREATE TABLE avarias (
    id INT AUTO_INCREMENT PRIMARY KEY,
    sala VARCHAR(20) NOT NULL,
    tipo ENUM('Equipamento', 'Software', 'Rede') NOT NULL,
    descricao TEXT NOT NULL,
    reportado_por VARCHAR(120) NOT NULL,
    estado ENUM('Pendente', 'Em resolução', 'Resolvida') NOT NULL DEFAULT 'Pendente',
    data_registo TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO cursos (nome, tipo, nivel, duracao, descricao) VALUES
('Técnico de Comércio', 'Curso Profissional', 4, '3 anos', 'Gestão de lojas e pontos de venda, atendimento ao cliente, gestão de stocks e técnicas de venda.'),
('Técnico de Vendas e Marketing', 'Curso Profissional', 4, '3 anos', 'Planeamento de ações comerciais, estudos de mercado, marketing digital e negociação.'),
('Técnico de Organização de Eventos', 'Curso Profissional', 4, '3 anos', 'Planeamento, produção e avaliação de eventos culturais, empresariais e sociais.'),
('Técnico de Turismo', 'Curso Profissional', 4, '3 anos', 'Informação e animação turística, agências de viagens e promoção de destinos.'),
('Técnico de Comunicação, Marketing, Relações Públicas e Publicidade', 'Curso Profissional', 4, '3 anos', 'Comunicação institucional, campanhas publicitárias, relações públicas e redes sociais.'),
('Técnico de Cozinha-Pastelaria', 'Curso Profissional', 4, '3 anos', 'Confeção de cozinha e pastelaria, higiene e segurança alimentar e gestão de cozinha.'),
('Técnico de Restaurante-Bar', 'Curso Profissional', 4, '3 anos', 'Serviço de mesa e bar, enologia, atendimento e gestão de restauração.'),
('Técnico de Informática - Sistemas', 'Curso de Aprendizagem', 4, '3 anos', 'Instalação e manutenção de computadores, sistemas operativos, servidores e apoio ao utilizador.'),
('Técnico de Informática - Instalação e Gestão de Redes', 'Curso de Aprendizagem', 4, '3 anos', 'Montagem e configuração de redes locais, equipamentos ativos, segurança e serviços de rede.'),
('Técnico de Programador de Informática', 'Curso de Aprendizagem', 4, '3 anos', 'Programação, bases de dados, desenvolvimento web e aplicações.'),
('Técnico de Vitrinismo', 'Curso de Aprendizagem', 4, '3 anos', 'Conceção e montagem de montras, merchandising visual e decoração de espaços comerciais.'),
('Assistente Administrativo', 'Curso de Educação e Formação (CEF)', 2, '1 ano', 'Tarefas administrativas, atendimento, arquivo e utilização de ferramentas de escritório.'),
('Empregado de Restaurante-Bar', 'Curso de Educação e Formação (CEF)', 2, '1 ano', 'Preparação de sala, serviço de mesa e bar e atendimento ao cliente.');

CREATE USER IF NOT EXISTS 'ecl_app'@'localhost' IDENTIFIED BY 'ecl2026';
GRANT SELECT, INSERT, UPDATE ON ecl.* TO 'ecl_app'@'localhost';
FLUSH PRIVILEGES;
