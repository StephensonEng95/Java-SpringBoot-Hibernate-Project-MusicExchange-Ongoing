-- Junior comment: Renaming column fields for the artists table to match updated Java Entity attributes
ALTER TABLE artists RENAME COLUMN release_date TO creation_date;
ALTER TABLE artists RENAME COLUMN release_time TO creation_time;

-- Junior comment: Renaming column fields for the fans table to match updated Java Entity attributes
ALTER TABLE fans RENAME COLUMN release_date TO creation_date;
ALTER TABLE fans RENAME COLUMN release_time TO creation_time;