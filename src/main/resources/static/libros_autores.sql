CREATE TABLE libros(
    id serial primary key,
    titulo varchar(100) not null,
    descripcion varchar(255)

);

CREATE TABLE autores(
    id serial primary key,
    nombre varchar(100) not null,
    apellido varchar(100),
    notas varchar(255)
);

CREATE TABLE libroautor(
    libro_id int not null,
    autor_id int not null,
    primary key(libro_id,autor_id),
    foreign key(libro_id) references libros(id),
    foreign key (autor_id) references autores(id)
);

insert into libros (titulo, descripcion) values 
('La niña polaca', 'Es una niña que vive en Polonia'),
('Orgullo y Prejuicio', 'Romance del weno'),
('El Silmarillion', 'El inicio del señor de los anillos');

select * from libros;

-- libro_id=5
-- Autores SI relacionados
select * from autores
join libroautor on autores.id = libroautor.libro_id
where id = 5
 -- Autores no relacionados
 select * from autores
 where id not in 
 (select id from autores
join libroautor on autores.id = libroautor.libro_id
where id = 5)