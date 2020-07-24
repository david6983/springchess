# Spring chess

# versions used for the project

- postgresql 12.2
- java 11

# Before launching the project

```
CREATE DATABASE springchess;
CREATE ROLE springchess WITH LOGIN PASSWORD 'springchess';
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO springchess;
```

or

```
sudo psql -f test.sql -U postgres
```

# launch project

- Import as maven project (Intellij or Eclipse)
- Launch the project using your IDE or maven command line

# Help

report any bug at dav6983@gmail.com

