-- Before running, create the schema named AlignPublic.
-- Or just create schema if necessary.
CREATE SCHEMA IF NOT EXISTS AlignPublic;
USE AlignPublic;

DROP TABLE IF EXISTS WorkExperiences;
DROP TABLE IF EXISTS Undergraduates;
DROP TABLE IF EXISTS Students;
DROP TABLE IF EXISTS SingleValueAggregatedData;
DROP TABLE IF EXISTS MultipleValueAggregatedData;

CREATE TABLE SingleValueAggregatedData(
	AnalyticKey VARCHAR(255),
    AnalyticValue INT DEFAULT 0,
    CONSTRAINT pk_SingleValueData_AnalyticKey
		PRIMARY KEY (AnalyticKey)
);

INSERT IGNORE INTO SingleValueAggregatedData (AnalyticKey) VALUE ("TotalGraduatedStudents");
INSERT IGNORE INTO SingleValueAggregatedData (AnalyticKey) VALUE ("TotalStudents");
INSERT IGNORE INTO SingleValueAggregatedData (AnalyticKey) VALUE ("TotalCurrentStudents");
INSERT IGNORE INTO SingleValueAggregatedData (AnalyticKey) VALUE ("TotalStudentsDroppedOut");
INSERT IGNORE INTO SingleValueAggregatedData (AnalyticKey) VALUE ("TotalStudentsGotJob");
INSERT IGNORE INTO SingleValueAggregatedData (AnalyticKey) VALUE ("TotalStudentsInBoston");
INSERT IGNORE INTO SingleValueAggregatedData (AnalyticKey) VALUE ("TotalStudentsInSeattle");
INSERT IGNORE INTO SingleValueAggregatedData (AnalyticKey) VALUE ("TotalStudentsInSiliconValley");
INSERT IGNORE INTO SingleValueAggregatedData (AnalyticKey) VALUE ("TotalStudentsInCharlotte");
INSERT IGNORE INTO SingleValueAggregatedData (AnalyticKey) VALUE ("TotalMaleStudents");
INSERT IGNORE INTO SingleValueAggregatedData (AnalyticKey) VALUE ("TotalFemaleStudents");
INSERT IGNORE INTO SingleValueAggregatedData (AnalyticKey) VALUE ("TotalFullTimeStudents");
INSERT IGNORE INTO SingleValueAggregatedData (AnalyticKey) VALUE ("TotalPartTimeStudents");
INSERT IGNORE INTO SingleValueAggregatedData (AnalyticKey) VALUE ("TotalStudentsWithScholarship");

CREATE TABLE MultipleValueAggregatedData(
    AnalyticTerm VARCHAR(255),
	AnalyticKey VARCHAR(255),
    AnalyticValue INT DEFAULT 0,
    CONSTRAINT pk_MultiValueData_Data
		PRIMARY KEY (AnalyticTerm, AnalyticKey)
);
 
CREATE TABLE Students(
	PublicId INT NOT NULL,
    GraduationYear INT,
    VisibleToPubilc BOOLEAN DEFAULT TRUE,
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
