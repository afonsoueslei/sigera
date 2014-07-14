CREATE TABLE Perfil (
  id INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  nome VARCHAR(45) NOT NULL,
  PRIMARY KEY(id)
);

CREATE TABLE unidade (
  id INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  nome VARCHAR(60) NULL,
  PRIMARY KEY(id)
);

CREATE TABLE Usuario (
  id INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  telefone VARCHAR(20) NULL,
  PRIMARY KEY(id)
);

CREATE TABLE Requerimento (
  id INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  usuario_id INTEGER UNSIGNED NULL,
  data_abertura DATE NULL,
  status_req VARCHAR(20) NULL,
  data_fechamento DATE NULL,
  parecer_id INTEGER UNSIGNED NULL,
  PRIMARY KEY(id),
  FOREIGN KEY(Usuario_id)
    REFERENCES Usuario(id)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION
);

CREATE TABLE Justificativa (
  Requerimento_id INTEGER UNSIGNED NOT NULL,
  justificativa TEXT NULL,
  INDEX Justificativa_FKIndex1(Requerimento_id),
  FOREIGN KEY(Requerimento_id)
    REFERENCES Requerimento(id)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION
);

CREATE TABLE Curso (
  id INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  cod_matriz_curricular VARCHAR(10) NULL,
  nome VARCHAR(45) NULL,
  unidade_id INTEGER UNSIGNED NULL,
  PRIMARY KEY(id),
  INDEX Curso_FKIndex1(unidade_id),
  FOREIGN KEY(unidade_id)
    REFERENCES unidade(id)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION
);

CREATE TABLE Professor (
  id INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  nome VARCHAR(60) NULL,
  usuario_id INTEGER UNSIGNED NULL,
  PRIMARY KEY(id),
  FOREIGN KEY(Usuario_id)
    REFERENCES Usuario(id)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION
);

CREATE TABLE Usuario_Perfil (
  Perfil_id INTEGER UNSIGNED NOT NULL,
  Usuario_id INTEGER UNSIGNED NOT NULL,
  PRIMARY KEY(Perfil_id, Usuario_id),
  INDEX Perfil_has_Usuario_FKIndex1(Perfil_id),
  INDEX Perfil_has_Usuario_FKIndex2(Usuario_id),
  FOREIGN KEY(Perfil_id)
    REFERENCES Perfil(id)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION,
  FOREIGN KEY(Usuario_id)
    REFERENCES Usuario(id)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION
);

CREATE TABLE Parecer (
  id INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  requerimento_id INTEGER UNSIGNED NOT NULL,
  usuario_id INTEGER UNSIGNED NULL,
  data_parecer DATE NULL,
  conteudo TEXT NULL,
  PRIMARY KEY(id),
  FOREIGN KEY(Requerimento_id)
    REFERENCES Requerimento(id)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION,
  FOREIGN KEY(Usuario_id)
    REFERENCES Usuario(id)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION
);

CREATE TABLE Req_Cancelamento_Disciplina (
  Requerimento_id INTEGER UNSIGNED NOT NULL,
  PRIMARY KEY(Requerimento_id),
  INDEX Req_Cancelamento_Disciplina_FKIndex1(Requerimento_id),
  FOREIGN KEY(Requerimento_id)
    REFERENCES Requerimento(id)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION
);

CREATE TABLE Req_Acrescimo_Disciplina (
  Requerimento_id INTEGER UNSIGNED NOT NULL,
  PRIMARY KEY(Requerimento_id),
  INDEX Req_Acrescimo_Disciplina_FKIndex1(Requerimento_id),
  FOREIGN KEY(Requerimento_id)
    REFERENCES Requerimento(id)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION
);

CREATE TABLE Req_Assinatura (
  Requerimento_id INTEGER UNSIGNED NOT NULL,
  PRIMARY KEY(Requerimento_id),
  INDEX Req_Assinatura_FKIndex1(Requerimento_id),
  FOREIGN KEY(Requerimento_id)
    REFERENCES Requerimento(id)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION
);

CREATE TABLE Req_ExtratoAcademico (
  Requerimento_id INTEGER UNSIGNED NOT NULL,
  PRIMARY KEY(Requerimento_id),
  INDEX Req_ExtratoAcademico_FKIndex1(Requerimento_id),
  FOREIGN KEY(Requerimento_id)
    REFERENCES Requerimento(id)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION
);

CREATE TABLE Req_Ementa (
  Requerimento_id INTEGER UNSIGNED NOT NULL,
  PRIMARY KEY(Requerimento_id),
  INDEX Req_Ementa_FKIndex1(Requerimento_id),
  FOREIGN KEY(Requerimento_id)
    REFERENCES Requerimento(id)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION
);

CREATE TABLE Req_Declaracao_Matricula (
  Requerimento_id INTEGER UNSIGNED NOT NULL,
  PRIMARY KEY(Requerimento_id),
  INDEX Req_Declaracao_Matricula_FKIndex1(Requerimento_id),
  FOREIGN KEY(Requerimento_id)
    REFERENCES Requerimento(id)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION
);

CREATE TABLE Disciplina (
  id INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  carga_horaria_pratica INTEGER UNSIGNED NULL,
  carga_horaria_teorica INTEGER UNSIGNED NULL,
  ementa TEXT NULL,
  nome VARCHAR(60) NULL,
  curso_id INTEGER UNSIGNED NOT NULL,
  bibliografia_basica TEXT NULL,
  bibliografia_complementar TEXT NULL,
  objetivo_geral TEXT NULL,
  PRIMARY KEY(id),
  FOREIGN KEY(Curso_id)
    REFERENCES Curso(id)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION
);

CREATE TABLE ReqCancelamentoDisciplina_Disciplina (
  Req_Cancelamento_Disciplina_Requerimento_id INTEGER UNSIGNED NOT NULL,
  Disciplina_id INTEGER UNSIGNED NOT NULL,
  status_disciplina VARCHAR(20) NULL,
  PRIMARY KEY(Req_Cancelamento_Disciplina_Requerimento_id, Disciplina_id),
  INDEX Req_Cancelamento_Disciplina_has_Disciplina_FKIndex1(Req_Cancelamento_Disciplina_Requerimento_id),
  INDEX Req_Cancelamento_Disciplina_has_Disciplina_FKIndex2(Disciplina_id),
  FOREIGN KEY(Req_Cancelamento_Disciplina_Requerimento_id)
    REFERENCES Req_Cancelamento_Disciplina(Requerimento_id)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION,
  FOREIGN KEY(Disciplina_id)
    REFERENCES Disciplina(id)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION
);

CREATE TABLE ReqEmenta_Disciplina (
  Disciplina_id INTEGER UNSIGNED NOT NULL,
  Req_Ementa_Requerimento_id INTEGER UNSIGNED NOT NULL,
  PRIMARY KEY(Disciplina_id, Req_Ementa_Requerimento_id),
  INDEX Disciplina_has_Req_Ementa_FKIndex1(Disciplina_id),
  INDEX Disciplina_has_Req_Ementa_FKIndex2(Req_Ementa_Requerimento_id),
  FOREIGN KEY(Disciplina_id)
    REFERENCES Disciplina(id)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION,
  FOREIGN KEY(Req_Ementa_Requerimento_id)
    REFERENCES Req_Ementa(Requerimento_id)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION
);

CREATE TABLE Turma (
  id INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  ano DATE NULL,
  nome VARCHAR(5) NULL,
  semestre VARCHAR(1) NULL,
  disciplina_id INTEGER UNSIGNED NULL,
  professor_id INTEGER UNSIGNED NULL,
  PRIMARY KEY(id),
  FOREIGN KEY(Professor_id)
    REFERENCES Professor(id)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION,
  FOREIGN KEY(Disciplina_id)
    REFERENCES Disciplina(id)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION
);

CREATE TABLE ReqAcrescimoDisciplina_Disciplina (
  Req_Acrescimo_Disciplina_Requerimento_id INTEGER UNSIGNED NOT NULL,
  Disciplina_id INTEGER UNSIGNED NOT NULL,
  status_disciplina VARCHAR(20) NULL,
  PRIMARY KEY(Req_Acrescimo_Disciplina_Requerimento_id, Disciplina_id),
  INDEX Req_Acrescimo_Disciplina_has_Disciplina_FKIndex1(Req_Acrescimo_Disciplina_Requerimento_id),
  INDEX Req_Acrescimo_Disciplina_has_Disciplina_FKIndex2(Disciplina_id),
  FOREIGN KEY(Req_Acrescimo_Disciplina_Requerimento_id)
    REFERENCES Req_Acrescimo_Disciplina(Requerimento_id)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION,
  FOREIGN KEY(Disciplina_id)
    REFERENCES Disciplina(id)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION
);

CREATE TABLE Req_Segunda_Chamada (
  Requerimento_id INTEGER UNSIGNED NOT NULL,
  professor_id INTEGER UNSIGNED NULL,
  disciplina_id INTEGER UNSIGNED NULL,
  data_prova DATE NULL,
  PRIMARY KEY(Requerimento_id),
  INDEX Req_Segunda_Chamada_FKIndex1(Requerimento_id),
  FOREIGN KEY(Requerimento_id)
    REFERENCES Requerimento(id)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION,
  FOREIGN KEY(Professor_id)
    REFERENCES Professor(id)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION,
  FOREIGN KEY(Disciplina_id)
    REFERENCES Disciplina(id)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION
);


