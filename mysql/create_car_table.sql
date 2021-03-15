DROP TABLE car;

CREATE TABLE car (
	car_name VARCHAR(60),
    car_pos POINT NOT NULL SRID 4326
);

CREATE SPATIAL INDEX car_pos ON car(car_pos);

SET @pos1 = 'POINT(57.6300 11.9200)';
SET @pos2 = 'POINT(57.6400 11.8800)';
SET @pos3 = 'POINT(57.6500 11.8835)';
SET @pos4 = 'POINT(57.6600 11.9340)';
SET @pos5 = 'POINT(57.6700 11.1000)';
SET @pos6 = 'POINT(57.7200 11.1033)';
SET @pos7 = 'POINT(57.7500 11.1099)';
SET @pos8 = 'POINT(57.7700 12.1100)';


INSERT INTO car VALUES('volvo', ST_GeomFromText(@pos1, 4326));
INSERT INTO car VALUES('saab', ST_PointFromText(@pos2, 4326));
INSERT INTO car VALUES('audi', ST_PointFromText(@pos3, 4326));
INSERT INTO car VALUES('fiat', ST_PointFromText(@pos4, 4326));
INSERT INTO car VALUES('porsche', ST_PointFromText(@pos5, 4326));
INSERT INTO car VALUES('volkswagen', ST_PointFromText(@pos6, 4326));
INSERT INTO car VALUES('ford', ST_PointFromText(@pos7, 4326));
INSERT INTO car VALUES('toyota', ST_PointFromText(@pos8, 4326));


-- For testing that the spatial indexing works:
SET @area = 'POLYGON((57.64 11.8820,
					  57.64 12.12,
                      57.73 12.12,
                      57.73 11.8820,
                      57.64 11.8820))';
                      
SELECT car_name, ST_AsText(car_pos) FROM car WHERE
MBRContains(ST_PolyFromText(@area, 4326), car_pos);