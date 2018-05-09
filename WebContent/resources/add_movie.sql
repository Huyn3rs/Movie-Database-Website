DELIMITER $$
CREATE FUNCTION  add_movie (t VARCHAR(100), y INT, d VARCHAR(100), burl VARCHAR(200), turl VARCHAR(200))
RETURNS CHAR(4)
BEGIN
	DECLARE mid INT DEFAULT 0;
	DECLARE EXIT HANDLER FOR SQLEXCEPTION
	BEGIN
		RETURN 'FAIL';
	END;
	SELECT m.id INTO mid from movies as m where m.title = t and m.year = y and m.director = d;
   	IF (mid = 0) THEN
   		INSERT INTO movies(title, year, director, banner_url, trailer_url) VALUES(t, y, d, burl, turl);
      RETURN 'YES';
    ELSE
      RETURN 'NO';

    END IF;
END
$$
DELIMITER ;