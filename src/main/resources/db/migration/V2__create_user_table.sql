CREATE TABLE IF NOT EXISTS "users" (
    "id" serial,
    "username" text NOT NULL,
    "password" text NOT NULL,
    PRIMARY KEY ("id")
);
