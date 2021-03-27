CREATE TABLE IF NOT EXISTS migrations.pod (
    "id" serial,
    "owner" text NOT NULL,
    "member" text NOT NULL,
    "memberEmail" text NOT NULL,
    "spellTableUrl" text NOT NULL,
    "name" text NOT NULL,
    PRIMARY KEY ("id")
);