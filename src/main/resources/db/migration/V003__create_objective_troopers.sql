create table if not exists objectivetroopers(
    TrooperID int,
    FOREIGN KEY (TrooperID) REFERENCES Troopers (ID),
    ObjectiveID int,
    FOREIGN KEY (ObjectiveID) REFERENCES Objectives (ID),
    PRIMARY KEY (TrooperID, ObjectiveID)
)