DROP TABLE users;
DROP TABLE projecttype;
DROP TABLE hall;
DROP TABLE movies;
DROP TABLE projections;
DROP TABLE seats;
DROP TABLE tickets;


CREATE TABLE "users" (

    id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, 
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(50) NOT NULL,
    registrationDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    role VARCHAR(20) NOT NULL,
    deleted BOOLEAN(1) NOT NULL DEFAULT 0
);


SELECT * FROM users;

INSERT INTO users (username, password, role) VALUES ("vai", "pass", "ADMINISTRATOR");
INSERT INTO users (username, password, role) VALUES ("nemza", "pass", "USER");
INSERT INTO users (username, password, role) VALUES ("ema", "pass", "USER");


CREATE TABLE "projecttype"(
    id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
    name VARCHAR(3) NOT NULL UNIQUE
);

INSERT INTO projecttype (name) VALUES ("2D");
INSERT INTO projecttype (name) VALUES ("3D");
INSERT INTO projecttype (name) VALUES ("4D");
INSERT INTO projecttype (name) VALUES ("5D");

SELECT * FROM projecttype;

CREATE TABLE "hall"(
    id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
    name VARCHAR(5) NOT NULL,
    projectType INTEGER NOT NULL, 
    FOREIGN KEY (projectType) REFERENCES projecttype(id)
);

INSERT INTO hall (name, projecttype) VALUES ("A1", 1);
INSERT INTO hall (name, projecttype) VALUES ("A2", 2);
INSERT INTO hall (name, projecttype) VALUES ("A3", 3);

SELECT * FROM hall;

CREATE TABLE "movies"(
    id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
    name VARCHAR(100) NOT NULL, 
    director VARCHAR(100) NOT NULL, 
    actors VARCHAR(400) NOT NULL,
    style VARCHAR(50) NOT NULL,
    duration INTEGER NOT NULL,
    distributor VARCHAR(100) NOT NULL,
    country VARCHAR(50) NOT NULL,
    year INTEGER NOT NULL,
    description TEXT,
    deleted INT NOT NULL
);

INSERT INTO movies (name, director, actors, style, duration, distributor, country, year, description, deleted) 
            VALUES ("Gladiator", "Ridley Scott", "Russell Crowe, Joaquin Phoenix, Connie Nielsen, Oliver Reed, Derek Jacobi",
                    "historical drama", 155, "DreamWorks Pictures, Universal Pictures", "United Kingdom, United States", 2000, 
                    "Maximus is a powerful Roman general, loved by the people and the aging Emperor, Marcus Aurelius. Before his death, the Emperor chooses Maximus to be his heir over his own son, Commodus, and a power struggle leaves Maximus and his family condemned to death. The powerful general is unable to save his family, and his loss of will allows him to get captured and put into the Gladiator games until he dies. The only desire that fuels him now is the chance to rise to the top so that he will be able to look into the eyes of the man who will feel his revenge.", 0);
                    
INSERT INTO movies (name, director, actors, style, duration, distributor, country, year, description, deleted)
            VALUES ("Schindler's List", "Steven Spielberg", "Liam Neeson, Ben Kingsley, Ralph Fiennes, Caroline Goodall, Jonathan Sagall",
                    "drama", 195, "Universal Pictures", "United States", 1993, "Oskar Schindler is a vain and greedy German businessman who becomes an unlikely humanitarian amid the barbaric German Nazi reign when he feels compelled to turn his factory into a refuge for Jews. Based on the true story of Oskar Schindler who managed to save about 1100 Jews from being gassed at the Auschwitz concentration camp, it is a testament to the good in all of us.", 0);
                    
INSERT INTO movies (name, director, actors, style, duration, distributor, country, year, description, deleted)
            VALUES ("The Pianist", "Roman Polanski", "Adrien Brody, Thomas Kretschmann, Frank Finlay, Maureen Lipman, Ed Stoppard",
                    "drama", 150, "Focus Features, StudioCanal", "France, Germany, Poland, United Kingdom", 2002, 
                    "In this adaptation of the autobiography The Pianist: The Extraordinary True Story of One Man's Survival in Warsaw, 1939-1945, Wladyslaw Szpilman, a Polish Jewish radio station pianist, sees Warsaw change gradually as World War II begins. Szpilman is forced into the Warsaw Ghetto, but is later separated from his family during Operation Reinhard. From this time until the concentration camp prisoners are released, Szpilman hides in various locations among the ruins of Warsaw. ", 0);
                        
SELECT * FROM movies;
                    

CREATE TABLE "projections" (
    id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
    movie INTEGER NOT NULL,
    type INTEGER NOT NULL,
    hall INTEGER NOT NULL,
    date DATE NOT NULL,
    price INTEGER NOT NULL,
    adminsName INTEGER NOT NULL,
    FOREIGN KEY (movie) REFERENCES movies (id),
    FOREIGN KEY (type) REFERENCES projecttype(id),
    FOREIGN KEY (hall) REFERENCES hall(id),
    FOREIGN KEY (adminsName) REFERENCES users(id)
);

INSERT INTO projections (movie, type, hall, date, price, adminsName) VALUES (1, 1, 1, "2020-02-20 20:00:00", 990, 1);
INSERT INTO projections (movie, type, hall, date, price, adminsName) VALUES (2, 2, 1, "2020-02-15 19:00:00", 1200, 1);
INSERT INTO projections (movie, type, hall, date, price, adminsName) VALUES (3, 3, 1, "2020-02-23 21:00:00", 880, 1);

SELECT * FROM projections;

CREATE TABLE "seats"(
    id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
    number INTEGER NOT NULL,
    hallId INTEGER NOT NULL,
    FOREIGN KEY (hallId) REFERENCES hall(id)
);

INSERT INTO seats (number, hallId) VALUES (1, 1);
INSERT INTO seats (number, hallId) VALUES (2, 1);
INSERT INTO seats (number, hallId) VALUES (3, 1);
INSERT INTO seats (number, hallId) VALUES (4, 1);
INSERT INTO seats (number, hallId) VALUES (5, 1);
INSERT INTO seats (number, hallId) VALUES (6, 1);
INSERT INTO seats (number, hallId) VALUES (7, 1);
INSERT INTO seats (number, hallId) VALUES (8, 1);


INSERT INTO seats (number, hallId) VALUES (1, 2);
INSERT INTO seats (number, hallId) VALUES (2, 2);
INSERT INTO seats (number, hallId) VALUES (3, 2);
INSERT INTO seats (number, hallId) VALUES (4, 2);
INSERT INTO seats (number, hallId) VALUES (5, 2);
INSERT INTO seats (number, hallId) VALUES (6, 2);
INSERT INTO seats (number, hallId) VALUES (7, 2);
INSERT INTO seats (number, hallId) VALUES (8, 2);

INSERT INTO seats (number, hallId) VALUES (1, 3);
INSERT INTO seats (number, hallId) VALUES (2, 3);
INSERT INTO seats (number, hallId) VALUES (3, 3);
INSERT INTO seats (number, hallId) VALUES (4, 3);

SELECT * FROM seats;
     

SELECT p.id, p.movie, p.type, p.hall, p.date, p.price, p.adminsName FROM projections AS p JOIN movies AS m ON p.movie = m.id  WHERE p.date >= '2020-02-20 20:30'

SELECT date FROM projections 

 AND m.deleted = 0;
 ORDER BY m.name, p.date;;