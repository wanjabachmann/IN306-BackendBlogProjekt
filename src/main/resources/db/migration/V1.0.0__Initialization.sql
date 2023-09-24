-- Create the Author table
CREATE TABLE Author (
    id INT AUTO_INCREMENT PRIMARY KEY,
    firstname VARCHAR(255) NOT NULL,
    lastname VARCHAR(255) NOT NULL,
    creationDate DATE
);

-- Create the Blog table
CREATE TABLE Blog (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(30) NOT NULL,
    content TEXT,
    creationDate DATE,
    author_id INT,
    CONSTRAINT FK_Author_Blog FOREIGN KEY (author_id) REFERENCES Author(id)
);

-- Create the Comment table
CREATE TABLE Comment (
    id INT AUTO_INCREMENT PRIMARY KEY,
    content VARCHAR(100) NOT NULL,
    creator VARCHAR(255) NOT NULL,
    creationDate DATE,
    blog_id INT,
    CONSTRAINT FK_Blog_Comment FOREIGN KEY (blog_id) REFERENCES Blog(id)
);

-- Create the join table for the many-to-many relationship between Blog and Author
CREATE TABLE Blog_Author (
    blog_id INT,
    author_id INT,
    PRIMARY KEY (blog_id, author_id),
    CONSTRAINT FK_Blog_Author_Blog FOREIGN KEY (blog_id) REFERENCES Blog(id),
    CONSTRAINT FK_Blog_Author_Author FOREIGN KEY (author_id) REFERENCES Author(id)
);