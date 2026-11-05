-- Idempotent - remember what that means? We wipe and reseed on every startup, so the demo always starts the same
DELETE FROM books;
DELETE FROM authors;

INSERT INTO authors (id, name) VALUES
  (1, 'Ursula K. Le Guin'),
  (2, 'Agatha Christie'),
  (3, 'Matt Dinniman');

INSERT INTO books (id, title, author_id, publication_year) VALUES
  (1, 'A Wizard of Earthsea',        1, 1968),
  (2, 'The Left Hand of Darkness',   1, 1969),
  (3, 'Murder on the Orient Express',2, 1934),
  (4, 'And Then There Were None',    2, 1939),
  (5, 'Dungeon Crawler Carl',        3, 2020);
