INSERT INTO authors (id, first_name, last_name, birth_date, nationality, email, web_site, biography)
VALUES
    (1, 'John', 'Doe', '1980-05-15', 'American', 'john.doe@example.com', 'http://www.johndoe.com', 'Author biography.'),
    (2, 'Jane', 'Smith', '1975-08-22', 'British', 'jane.smith@example.com', 'http://www.janesmith.com', 'Another authorDocument biography.'),
    (3, 'Carlos', 'García', '1990-11-10', 'Spanish', 'carlos.garcia@example.com', 'http://www.carlosgarcia.com', 'Biografía del autor.'),
    (4, 'Laura', 'López', '1988-03-25', 'Mexican', 'laura.lopez@example.com', 'http://www.lauralopez.com', 'Biografía de Laura.');

INSERT INTO books (id, isbn, name, language, description, category, author_id)
VALUES
    (1, '978-0-13-468599-1', 'Introduction to Databases', 'English', 'Comprehensive guide to databases.', 'Technology', 1),
    (2, '978-1-23-456789-0', 'Data Science for Beginners', 'English', 'An introductory book on data science.', 'Science', 2),
    (3, '978-2-34-567890-1', 'Programming in Python', 'English', 'Learn Python programming language.', 'Programming', 3),
    (4, '978-3-45-678901-2', 'Historia de la Informática', 'Spanish', 'Una exploración de la historia de la informática.', 'Tecnología', 4),
    (5, '978-4-56-789012-3', 'Machine Learning Basics', 'English', 'Fundamentals of machine learning.', 'Science', 1),
    (6, '978-5-67-890123-4', 'Advanced SQL Techniques', 'English', 'Mastering advanced SQL queries.', 'Technology', 2),
    (7, '978-6-78-901234-5', 'Matemáticas para Programadores', 'Spanish', 'Conceptos matemáticos aplicados a la programación.', 'Ciencia', 3),
    (8, '978-7-89-012345-6', 'Web Development with JavaScript', 'English', 'Building dynamic web applications.', 'Programming', 4),
    (9, '978-8-90-123456-7', 'Introducción a la Inteligencia Artificial', 'Spanish', 'Principios fundamentales de la inteligencia artificial.', 'Ciencia', 1),
    (10, '978-9-01-234567-8', 'Cybersecurity Essentials', 'English', 'Protecting systems and networks from cyber threats.', 'Technology', 2),
    (11, '978-0-12-345678-9', 'Big Data Analytics', 'English', 'Analyzing large datasets for insights.', 'Science', 3),
    (12, '978-1-23-456789-0', 'Desarrollo Ágil de Software', 'Spanish', 'Prácticas ágiles en el desarrollo de software.', 'Tecnología', 4),
    (13, '978-2-34-567890-1', 'JavaScript Frameworks', 'English', 'Exploring popular JavaScript frameworks.', 'Programming', 1),
    (14, '978-3-45-678901-2', 'Estadísticas para Data Science', 'Spanish', 'Conceptos estadísticos aplicados a la ciencia de datos.', 'Ciencia', 1),
    (15, '978-4-56-789012-3', 'Cloud Computing Basics', 'English', 'Understanding cloud computing concepts.', 'Technology', 2),
    (16, '978-5-67-890123-4', 'Python Web Frameworks', 'English', 'Building web applications with Python frameworks.', 'Programming', 4),
    (17, '978-6-78-901234-5', 'Programación Orientada a Objetos', 'Spanish', 'Principios de programación orientada a objetos.', 'Ciencia', 1),
    (18, '978-7-89-012345-6', 'Artificial Neural Networks', 'English', 'Understanding artificial neural networks.', 'Science', 2),
    (19, '978-8-90-123456-7', 'Seguridad Informática Avanzada', 'Spanish', 'Principios avanzados de seguridad informática.', 'Tecnología', 3);

INSERT INTO clientDocuments (id, first_name, last_name, email, address, phone_number)
VALUES
    (1, 'Juan', 'Pérez', 'juan.perez@example.com', 'Calle 123, Ciudad', '123-456-7890'),
    (2, 'María', 'Gómez', 'maria.gomez@example.com', 'Avenida Principal, Pueblo', '987-654-3210'),
    (3, 'Carlos', 'Fernández', 'carlos.fernandez@example.com', 'Carrera 45, Barrio', '456-789-0123'),
    (4, 'Laura', 'Martínez', 'laura.martinez@example.com', 'Camino Real, Villa', '789-012-3456'),
    (5, 'Roberto', 'Hernández', 'roberto.hernandez@example.com', 'Pasaje 67, Colonia', '234-567-8901'),
    (6, 'Isabel', 'López', 'isabel.lopez@example.com', 'Plaza Central, Aldea', '567-890-1234'),
    (7, 'David', 'Ramírez', 'david.ramirez@example.com', 'Callejón 89, Caserío', '890-123-4567'),
    (8, 'Ana', 'García', 'ana.garcia@example.com', 'Ruta 101, Poblado', '012-345-6789'),
    (9, 'Eduardo', 'Díaz', 'eduardo.diaz@example.com', 'Avenida Sur, Hamlet', '345-678-9012'),
    (10, 'Sofía', 'Fuentes', 'sofia.fuentes@example.com', 'Bulevar 234, Ranchería', '678-901-2345');
