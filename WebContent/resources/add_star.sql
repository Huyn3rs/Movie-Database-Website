DELIMITER $$
CREATE FUNCTION  add_star (movie INT, star INT)
RETURNS CHAR(4)
BEGIN
  DECLARE starid INT DEFAULT 0;
  DECLARE s INT DEFAULT 0;
	DECLARE EXIT HANDLER FOR SQLEXCEPTION
	BEGIN
		RETURN 'FAIL';
	END;
    SELECT id INTO s from stars where id = star;
    IF (s = 0) THEN
      RETURN 'NA';
    END IF;


    SELECT star_id INTO starid from stars_in_movies where movie_id = movie and star_id = star limit 1;
    IF (starid = 0) THEN
      INSERT INTO stars_in_movies VALUES(star, movie);
      RETURN 'YES';
    ELSE
      RETURN 'NO';
    END IF;
END
$$
DELIMITER ;