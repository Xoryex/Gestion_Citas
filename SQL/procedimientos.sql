create procedure paLogin
(
	@dni numeric(8),
	@contrasena varchar(20)
)
as 
begin
	if not exists(select DniRecep from Recepcionista where DniRecep = @dni)begin
		raiserror('Usuario no existe',16,1)
		return @@ERROR
	end
	
	if not exists (select DniRecep from Recepcionista where DniRecep = @dni and Contrasena=@contrasena)begin
		raiserror('Constrase�a incorrecta',16,1)
		return @@ERROR
	end
	select * from Recepcionista where DniRecep=@dni
end



create view vwConsultaHorario
as
select dh.CodHorario,h.HoraInicio,h.HoraFin,h.LimitPct as LimitePacientes,
			case h.EstadoHorario
				when 1 then 'Activo'
				when 0 then 'Desactivado'
			end as EstadoHorario,
			d.DniDoc,d.NomDoc+' '+d.ApellDoc as NombreDoctor
			from Doctor_Horario as dh
			inner join Horario as h on(dh.CodHorario=h.CodHorario)
			inner join Doctor as d on(dh.DniDoc=d.DniDoc)
go

create view vwConsultaCita
as
select c.CodCita,c.DniPct,p.NomPct+' '+p.ApellPct as NombrePaciente,cs.NomConst,d.NomDoc+' '+d.ApellDoc as NombreDoctor,c.HoraInicio,c.HoraFin,ta.TipoAtencion,

,r.NomRecep
from Cita as c
inner join Paciente as p on (c.DniPct=p.DniPct)
inner join Recepcionista as r on (c.DniRecep=r.DniRecep)
inner join TipoAtencion as ta on (c.IdTipoAtencion=ta.IdTipoAtencion)
inner join EstadoCita as ec on (c.IdEstadoCita=ec.IdEstadoCita)
inner join Doctor as d on (c.DniDoc=d.DniDoc)
inner join Consultorio as cs on (d.CodConst=cs.CodConst)

go

create procedure paConsultas
(
	@tabla int,
	@fechahorainicio datetime,
	@fechahorafin	datetime,
	@buscar varchar,
	@orden bit
)
as
begin

	if @tabla=1 
		begin
			select * from vwConsultaHorario
			where HoraInicio >= CAST(@fechahorainicio as time)
				and HoraFin <= CAST(@fechahorafin as time)
				and ((CAST(CodHorario as varchar)+CAST(LimitePacientes as varchar)+EstadoHorario+CAST(DniDoc as varchar)+NombreDoctor like '%'+@buscar+'%'  and  @buscar <> '')
					or @buscar ='');
		end
	else if @tabla=2
		begin
		;
		end
	else if @tabla=3
		begin
		;
		end
	else if @tabla=4
		begin
		;
		end
	else
		begin
		;
		end
end
go



