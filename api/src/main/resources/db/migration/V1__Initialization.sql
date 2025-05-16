CREATE TABLE users_table (
id UUID primary key,
username VARCHAR(30) not null,
email VARCHAR(30) not null,
password VARCHAR(100) not null,
role VARCHAR(30) not null
);

CREATE TABLE surveycreators_table (
id BIGSERIAL primary key,
user_id UUID not null,
survey_id UUID not null
);

CREATE TABLE surveys_table (
id UUID primary key,
survey_title TEXT not null,
survey_description TEXT not null
);

CREATE TABLE questions_table (
id UUID primary key,
survey_id UUID not null,
question_text TEXT not null
);

CREATE TABLE answers_table (
id UUID primary key,
question_id UUID not null,
answer_text TEXT not null,
answer_quantity Decimal(10, 0) not null
);

CREATE TABLE token_table (
id BIGSERIAL primary key,
access_token TEXT not null,
refresh_token TEXT not null,
is_logged_out BOOLEAN not null,
user_id UUID not null
);

ALTER TABLE "surveycreators_table" ADD FOREIGN KEY ("user_id") REFERENCES "users_table" ("id") on delete cascade on update cascade;
ALTER TABLE "surveycreators_table" ADD FOREIGN KEY ("survey_id") REFERENCES "surveys_table" ("id") on delete cascade on update cascade;
ALTER TABLE "questions_table" ADD FOREIGN KEY ("survey_id") REFERENCES "surveys_table" ("id") on delete cascade on update cascade;
ALTER TABLE "answers_table" ADD FOREIGN KEY ("question_id") REFERENCES "questions_table" ("id") on delete cascade on update cascade;
ALTER TABLE "token_table" ADD FOREIGN KEY ("user_id") REFERENCES "users_table" ("id") on delete cascade on update cascade;
