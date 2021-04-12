CREATE TABLE IF NOT EXISTS "pod_members" (
    "id" serial,
    "owner" text NOT NULL,
    "member" text NOT NULL,
    "member_email" text NOT NULL,
    "name" text NOT NULL,
    PRIMARY KEY ("id")
);