CREATE TABLE IF NOT EXISTS token (
   id int PRIMARY KEY auto_increment,
   expires_in INT,
   access_token TEXT,
   refresh_token TEXT,
   token_type TEXT
);

CREATE TABLE IF NOT EXISTS transaction (
   id int PRIMARY KEY auto_increment,
   amount INT,
   public_id BIGINT,
   created_timestamp BIGINT,
   created TEXT,
   description TEXT,
   from_user TEXT
);

CREATE TABLE IF NOT EXISTS alert (
   id int PRIMARY KEY auto_increment,
   alert_id BIGINT,
   public_id BIGINT,
   created_timestamp BIGINT
);