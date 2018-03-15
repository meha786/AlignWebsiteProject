# Before running, create the schema named AlignPublic.
# Or just create schema if necessary.
CREATE SCHEMA IF NOT EXISTS AlignPublic;
USE AlignPublic;

DROP TABLE IF EXISTS WorkExperiences;
DROP TABLE IF EXISTS Undergraduates;
DROP TABLE IF EXISTS Students;
 
CREATE TABLE Students(
	PublicId INT NOT NULL,
	FirstName VARCHAR(50),
    Photo BLOB,
    GraduationYear INT,
    CONSTRAINT pk_Students_PublicId
		PRIMARY KEY (PublicId)
);

CREATE TABLE WorkExperiences(
	WorkExperienceId INT AUTO_INCREMENT,
    PublicId INT,
	Coop VARCHAR(255),
    CONSTRAINT pk_WorkExperiences_WorkExperienceId
		PRIMARY KEY (WorkExperienceId),
	CONSTRAINT fk_WorkExperiences_PublicId
		FOREIGN KEY (PublicId)
        REFERENCES Students(PublicId)
        ON UPDATE CASCADE ON DELETE CASCADE,
	CONSTRAINT uq_WorkExperiences_WorkExperience
		UNIQUE (PublicId, Coop)
);

CREATE TABLE Undergraduates(
	UndergraduateId INT AUTO_INCREMENT,
    PublicId INT,
    UndergradDegree VARCHAR(255),
    UndergradSchool VARCHAR(255), 
    CONSTRAINT pk_Undergraduates_UndergraduateId
		PRIMARY KEY (UndergraduateId),
	CONSTRAINT fk_Undergraduates_PublicId
		FOREIGN KEY (PublicId)
        REFERENCES Students(PublicId)
        ON UPDATE CASCADE ON DELETE CASCADE,
	CONSTRAINT uq_Undergraduates_Undergraduate
		UNIQUE (PublicId, UndergradDegree, UndergradSchool)
);
