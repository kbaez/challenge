DROP TABLE IF EXISTS satellites;

CREATE TABLE satellites (
                      name VARCHAR(250) PRIMARY KEY,
		      distance DOUBLE,
                      message VARCHAR(250),
		      position VARCHAR(250)
);

INSERT INTO satellites (name, distance, message, position) values ('Kenobi', null, null, '{-500, -200}');
INSERT INTO satellites (name, distance, message, position) values ('Skywalker', null, null, '{100, -100}');
INSERT INTO satellites (name, distance, message, position) values ('Sato', null, null, '{500, 100}');