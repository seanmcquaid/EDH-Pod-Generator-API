CREATE TABLE IF NOT EXISTS "pods" (
    "id" serial,
    "owner" text NOT NULL,
    "member" text NOT NULL,
    "member_email" text NOT NULL,
    "spell_table_url" text NOT NULL,
    "name" text NOT NULL,
    PRIMARY KEY ("id")
);