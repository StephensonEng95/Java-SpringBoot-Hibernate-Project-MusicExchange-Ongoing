CREATE TABLE IF NOT EXISTS artists (
    artist_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    profile_picture VARCHAR(512)
);

CREATE TABLE IF NOT EXISTS fans (
    fan_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    profile_picture VARCHAR(512)
);

CREATE TABLE IF NOT EXISTS songs (
    song_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    song_title VARCHAR(255) NOT NULL,
    genre VARCHAR(50) NOT NULL,
    duration INT NOT NULL,
    released_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    artist_id BIGINT NOT NULL,
    cover_art_url VARCHAR(512),
    CONSTRAINT fk_songs_artist FOREIGN KEY (artist_id) REFERENCES artists(artist_id) ON DELETE CASCADE
);

CREATE TABLE suggested_artists (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    profile_pic VARCHAR(500),
    genre VARCHAR(100) NOT NULL
);

INSERT INTO suggested_artists (username, profile_pic, genre)
VALUES ('Billie Eilish', 'https://images.unsplash.com/photo-1534528741775-53994a69daeb?w=500&auto=format&fit=crop&q=60', 'Alternative Pop');

INSERT INTO suggested_artists (username, profile_pic, genre)
VALUES ('The Weeknd','https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?w=500&auto=format&fit=crop&q=60', 'R&B / Synth-Pop');

INSERT INTO suggested_artists (username, profile_pic, genre)
VALUES ('Olivia Rodrigo', 'https://images.unsplash.com/photo-1494790108377-be9c29b29330?w=500&auto=format&fit=crop&q=60', 'Pop Rock');

INSERT INTO suggested_artists (username, profile_pic, genre)
VALUES ('Kaytranada', 'https://images.unsplash.com/photo-1500648767791-00dcc994a43e?w=500&auto=format&fit=crop&q=60', 'Electronic / House');