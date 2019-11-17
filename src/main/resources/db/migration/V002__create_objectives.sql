create table if not exists objectives(
    ID SERIAL PRIMARY KEY,
    Name varchar(100),
    Description varchar(1000),
    Status varchar(100)
)