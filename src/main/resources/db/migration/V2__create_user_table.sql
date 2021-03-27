CREATE TABLE IF NOT EXISTS migrations.user (
    "id" serial,
    "username" text NOT NULL,
    "password" text NOT NULL,
    PRIMARY KEY ("id")
);
