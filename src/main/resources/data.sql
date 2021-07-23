DROP TABLE IF EXISTS satellites;

CREATE TABLE satellites (
		      id BIGINT AUTO_INCREMENT  PRIMARY KEY,
                      name VARCHAR(250) UNIQUE,
		      distance DOUBLE,
                      message ARRAY,
		      x DOUBLE,
		      y DOUBLE
);

INSERT INTO satellites (name, distance, message, x, y) values ('kenobi', 0, null, -500, -200);
INSERT INTO satellites (name, distance, message, x, y) values ('skywalker', 0, null, 100, -100);
INSERT INTO satellites (name, distance, message, x, y) values ('sato', 0, null, 500, 100);