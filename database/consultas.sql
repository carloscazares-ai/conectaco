use conectaco;

show tables;

select * from Usuarios;
select * from Turistas;
select * from Negocios;

-- Dónde quedó guardado cada usuario: todos están en Usuarios y,
-- según su tipo, también en Turistas o en Negocios (unidos por fk_id_usuario).
select u.id_usuario, u.nombre, u.tipo_usuario, t.id_turista, n.id_negocio
from Usuarios u
left join Turistas t on t.fk_id_usuario = u.id_usuario
left join Negocios n on n.fk_id_usuario = u.id_usuario
order by u.id_usuario;
