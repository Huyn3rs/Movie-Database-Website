DELIMITER $$
CREATE FUNCTION  add_genre (movie INT, genre INT)
RETURNS CHAR(4)
BEGIN
  DECLARE genreid INT DEFAULT 0;
  DECLARE g INT DEFAULT 0;
  DECLARE EXIT HANDLER FOR SQLEXCEPTION
  BEGIN
    RETURN 'FAIL';
  END;
    SELECT id INTO g from genres where id = genre;
    IF (g = 0) THEN
      RETURN 'NA';
    END IF;


    SELECT genre_id INTO genreid from genres_in_movies where movie_id = movie and genre_id = genre limit 1;
    IF (genreid = 0) THEN
      INSERT INTO genres_in_movies VALUES(genre, movie);
      RETURN 'YES';
    ELSE
      RETURN 'NO';
    END IF;
END
$$
DELIMITER ;