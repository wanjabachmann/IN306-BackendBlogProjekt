-- Insert data into the Author table
INSERT INTO Author (firstname, lastname, creationDate)
VALUES
    ('John', 'Doe', '2023-09-22'),
    ('Jane', 'Smith', '2023-09-23');

-- Insert data into the Blog table
INSERT INTO Blog (title, content, creationDate, author_id)
VALUES
    ('Introduction to Java', 'This is a blog post about Java programming.', '2023-09-20', 1),
    ('Web Development Basics', 'A beginner-friendly guide to web development.', '2023-09-21', 2);

-- Insert data into the Comment table
INSERT INTO Comment (content, creator, creationDate, blog_id)
VALUES
    ('Great article!', 'Alice', '2023-09-21', 1),
    ('I learned a lot!', 'Bob', '2023-09-22', 1),
    ('Nice explanation!', 'Eve', '2023-09-21', 2);
