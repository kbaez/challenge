DROP TABLE IF EXISTS satellites;

CREATE TABLE satellites (
		      id BIGINT AUTO_INCREMENT  PRIMARY KEY,
                      name VARCHAR(250) UNIQUE,
		      distance DOUBLE,
                      message VARCHAR(250),
		      x DOUBLE,
		      y DOUBLE
);

INSERT INTO satellites (name, distance, message, x, y) values ('kenobi', 0,'este,,,mensaje,', -500, -200);
INSERT INTO satellites (name, distance, message, x, y) values ('skywalker', 0,',es,,,secreto', 100, -100);
INSERT INTO satellites (name, distance, message, x, y) values ('sato', 0,'este,,un,,', 500, 100);