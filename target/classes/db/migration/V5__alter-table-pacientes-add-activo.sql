ALTER TABLE pacientes ADD activo tinyint;
UPDATE pacientes SET activo = 1;
alter table pacientes modify activo tinyint not null;