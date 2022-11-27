create table IF NOT EXISTS languages (
    id integer auto_increment,
    locale varchar(2),     // ru, kz, en
    messagekey varchar(255),    // home.welcome
    messagecontent varchar(255), // Добро пожаловать
    primary key (id)
    );
