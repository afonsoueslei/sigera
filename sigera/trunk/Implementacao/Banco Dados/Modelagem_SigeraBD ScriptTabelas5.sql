CREATE TABLE unidade (
  id INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  nome VARCHAR(60) NULL,
  PRIMARY KEY(id)
);

CREATE TABLE Perfil (
  id INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  nome VARCHAR(45) NOT NULL,
  PRIMARY KEY(id)
);

CREATE TABLE Usuario (
  id INTEGER UNSIGNED NOT NULL,
  telefone VARCHAR(20) NULL,
  PRIMARY KEY(id)
);

CREATE TABLE Configuracao (
  id INTEGER UNSIGNED NOT NULL,
  data_final_ajustes_matricula DATE NULL,
  data_inicial_ajustes_matricula DATE NULL,
  data_inicial_semestre DATE NULL,
  data_final_semestre DATE NULL,
  PRIMARY KEY(id)
);

CREATE TABLE Professor (
  id INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  usuario_id INTEGER UNSIGNED NULL,
  PRIMARY KEY(id),
  FOREIGN KEY(Usuario_id)
    REFERENCES Usuario(id)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION
);

CREATE TABLE Curso (
  id INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  prefixo VARCHAR(15) NOT NULL,
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

CREATE TABLE Requerimento (
  id INTEGER UNSIGNED NOT NULL,
  usuario_id INTEGER UNSIGNED NULL,
  data_abertura DATETIME NULL,
  status_req INTEGER UNSIGNED NULL,
  data_fechamento DATETIME NULL,
  justificativa TEXT NULL,
  tipo INTEGER UNSIGNED NOT NULL,
  PRIMARY KEY(id),
  FOREIGN KEY(Usuario_id)
    REFERENCES Usuario(id)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION
);

CREATE TABLE Parecer (
  id INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  requerimento_id INTEGER UNSIGNED NOT NULL,
  usuario_id INTEGER UNSIGNED NULL,
  data_parecer DATETIME NULL,
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

CREATE TABLE Usuario_Perfil (
  id INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  Perfil_id INTEGER UNSIGNED NOT NULL,
  Usuario_id INTEGER UNSIGNED NOT NULL,
  Curso_id INTEGER UNSIGNED NOT NULL,
  PRIMARY KEY(id, Perfil_id, Usuario_id),
  INDEX Perfil_has_Usuario_FKIndex1(Perfil_id),
  INDEX Perfil_has_Usuario_FKIndex2(Usuario_id),
  INDEX Usuario_Perfil_FKIndex3(Curso_id),
  FOREIGN KEY(Perfil_id)
    REFERENCES Perfil(id)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION,
  FOREIGN KEY(Usuario_id)
    REFERENCES Usuario(id)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION,
  FOREIGN KEY(Curso_id)
    REFERENCES Curso(id)
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

CREATE TABLE Turma (
  id INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  ano INT NULL,
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

CREATE TABLE Req_CancelamentoDisciplina_Turma (
  id INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  Req_Cancelamento_Disciplina_Requerimento_id INTEGER UNSIGNED NOT NULL,
  Turma_id INTEGER UNSIGNED NOT NULL,
  status_disciplina INTEGER UNSIGNED NULL,
  PRIMARY KEY(id, Req_Cancelamento_Disciplina_Requerimento_id, Turma_id),
  INDEX Req_Cancelamento_Disciplina_has_Turma_FKIndex1(Req_Cancelamento_Disciplina_Requerimento_id),
  INDEX Req_Cancelamento_Disciplina_has_Turma_FKIndex2(Turma_id),
  FOREIGN KEY(Req_Cancelamento_Disciplina_Requerimento_id)
    REFERENCES Req_Cancelamento_Disciplina(Requerimento_id)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION,
  FOREIGN KEY(Turma_id)
    REFERENCES Turma(id)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION
);

CREATE TABLE Req_AcrescimoDisciplina_Turma (
  id INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  Req_Acrescimo_Disciplina_Requerimento_id INTEGER UNSIGNED NOT NULL,
  Turma_id INTEGER UNSIGNED NOT NULL,
  status_disciplina INTEGER UNSIGNED NULL,
  PRIMARY KEY(id, Req_Acrescimo_Disciplina_Requerimento_id, Turma_id),
  INDEX Req_Acrescimo_Disciplina_has_Turma_FKIndex1(Req_Acrescimo_Disciplina_Requerimento_id),
  INDEX Req_AcrescimoDisciplina_Turma_FKIndex2(Turma_id),
  FOREIGN KEY(Req_Acrescimo_Disciplina_Requerimento_id)
    REFERENCES Req_Acrescimo_Disciplina(Requerimento_id)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION,
  FOREIGN KEY(Turma_id)
    REFERENCES Turma(id)
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

CREATE TABLE Req_Segunda_Chamada (
  Requerimento_id INTEGER UNSIGNED NOT NULL,
  Turma_id INTEGER UNSIGNED NOT NULL,
  data_prova DATE NULL,
  PRIMARY KEY(Requerimento_id),
  INDEX Req_Segunda_Chamada_FKIndex1(Requerimento_id),
  INDEX Req_Segunda_Chamada_FKIndex2(Turma_id),
  FOREIGN KEY(Requerimento_id)
    REFERENCES Requerimento(id)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION,
  FOREIGN KEY(Turma_id)
    REFERENCES Turma(id)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION
);

CREATE TABLE Anexos (
  idAnexos INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  Req_Segunda_Chamada_Requerimento_id INTEGER UNSIGNED NOT NULL,
  nome TEXT NULL,
  conteudo BLOB NULL,
  PRIMARY KEY(idAnexos),
  INDEX Anexos_FKIndex1(Req_Segunda_Chamada_Requerimento_id),
  FOREIGN KEY(Req_Segunda_Chamada_Requerimento_id)
    REFERENCES Req_Segunda_Chamada(Requerimento_id)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION
);


