DROP TABLE IF EXISTS satellites;

CREATE TABLE satellites (
                      id INT AUTO_INCREMENT  PRIMARY KEY,
                      name VARCHAR(250),
		      distance DOUBLE,
                      message VARCHAR(250),
		      position VARCHAR(250)
);