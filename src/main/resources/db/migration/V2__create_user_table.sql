CREATE TABLE IF NOT EXISTS "user_info" (
    "id" serial,
    "username" text NOT NULL,
    "password" text NOT NULL,
    PRIMARY KEY ("id")
);
