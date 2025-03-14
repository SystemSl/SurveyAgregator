CREATE TABLE admins (
AdminID BIGSERIAL primary key,
Name VARCHAR(30) not null,
Email VARCHAR(30) not null,
Password VARCHAR(100) not null
);

CREATE TABLE surveycreators (
AdminRelID BIGSERIAL primary key,
AdminID BIGSERIAL not null,
SurveyID BIGSERIAL not null
);

CREATE TABLE surveys (
SurveyID BIGSERIAL primary key,
SurveyTitle TEXT not null,
SurveyDescription TEXT not null
);

CREATE TABLE questions (
QuestionID BIGSERIAL primary key,
SurveyID BIGSERIAL not null,
QuestionText TEXT not null
);

CREATE TABLE answers (
AnswerID BIGSERIAL primary key,
QuestionID BIGSERIAL not null,
AnswerText TEXT not null,
AnswerQuantity Decimal(10, 0) not null
);

ALTER TABLE "surveycreators" ADD FOREIGN KEY ("adminid") REFERENCES "admins" ("adminid") on delete cascade on update cascade;
ALTER TABLE "surveycreators" ADD FOREIGN KEY ("surveyid") REFERENCES "surveys" ("surveyid") on delete cascade on update cascade;
ALTER TABLE "questions" ADD FOREIGN KEY ("surveyid") REFERENCES "surveys" ("surveyid") on delete cascade on update cascade;
ALTER TABLE "answers" ADD FOREIGN KEY ("questionid") REFERENCES "questions" ("questionid") on delete cascade on update cascade;
