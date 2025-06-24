CREATE TABLE admin_users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL
);

INSERT INTO admin_users (name, email, password)
VALUES ('Super Admin', 'admin@portal.com', 'admin123');

-----------------------------------------------------------------------------------------------------
CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL,
    role VARCHAR(20) DEFAULT 'USER'
);

INSERT INTO users (name, email, password, role)
VALUES 
('Admin', 'admin@portal.com', 'admin123', 'ADMIN')
('Alice', 'alice@example.com', 'alice123', 'USER');


-----------------------------------------------------------------------------------------------------
CREATE TABLE job_application (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    description TEXT,
    applicant_email VARCHAR(100) NOT NULL,
    status VARCHAR(20) DEFAULT 'PENDING'
);


INSERT INTO job_application (title, description, applicant_email, status)
VALUES
('Java Backend Intern', 'Work with Spring Boot and REST APIs.', 'alice@example.com', 'PENDING'),
('React Frontend Intern', 'Build UI using React.js', 'alice@example.com', 'APPROVED'),
('Python Data Analyst', 'Work with Pandas and Matplotlib', 'alice@example.com', 'REJECTED'),
('Java Backend Intern', 'Work with Spring Boot and REST APIs.', 'alice@example.com', 'PENDING'),
('React Frontend Intern', 'Build UI using React.js', 'alice@example.com', 'APPROVED'),
('Python Data Analyst', 'Work with Pandas and Matplotlib', 'alice@example.com', 'REJECTED');

-----------------------------------------------------------------------------------------------------
CREATE TABLE internships (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    description TEXT NOT NULL
);


INSERT INTO internships (title, description) VALUES
('Java Developer Intern', 'Build REST APIs with Spring Boot.'),
('Frontend Developer Intern', 'React.js-based UI design.'),
('Data Science Intern', 'Work with Python and Machine Learning.')
('Java Developer Intern', 'Build REST APIs using Spring Boot.'),
('Frontend Developer Intern', 'Create user interfaces with React and CSS.'),
('Data Analyst Intern', 'Analyze datasets using Python, Excel, and Power BI.'),
('Machine Learning Intern', 'Train and deploy ML models using Scikit-learn.'),
('Mobile App Developer Intern', 'Develop Android apps using Kotlin or Flutter.');
