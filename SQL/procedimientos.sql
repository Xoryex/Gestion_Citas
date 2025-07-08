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
		raiserror('Constraseña incorrecta',16,1)
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
ec.EstadoCita,r.NomRecep+' '+r.ApellRecep as NombreRecep
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
	@buscar varchar(100),
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
			select * from vwConsultaCita
			where HoraInicio >= @fechahorainicio
				and HoraFin <= @fechahorafin
				and ((CAST(CodCita as varchar)+NombrePaciente+NombreDoctor+NombreRecep+TipoAtencion+EstadoCita like '%'+@buscar+'%'  and  @buscar <> '')
					or @buscar ='');
		end
	else if @tabla=3
		begin
			select * from Doctor
			where (CAST(DniDoc as varchar)+NomDoc+ApellDoc like '%'+@buscar+'%'  and  @buscar <> '')
				or @buscar ='';
		end
	else if @tabla=4
		begin
			select * from Paciente
			where (CAST(DniPct as varchar)+NomPct+ApellPct like '%'+@buscar+'%'  and  @buscar <> '')
				or @buscar ='';
		end
	else if @tabla=5
		begin
			select * from Recepcionista
			where (CAST(DniRecep as varchar)+NomRecep+ApellRecep like '%'+@buscar+'%'  and  @buscar <> '')
				or @buscar ='';
		end
end
go

create procedure paConsultaCita
(
	@fechahorainicio datetime,
	@fechahorafin	datetime,
	@buscar varchar(100),
	@orden bit
)
as
begin
	if @orden = 1
		begin
			select * from vwConsultaCita
			where HoraInicio >= @fechahorainicio
				and HoraFin <= @fechahorafin
				and ((CodCita like '%'+@buscar+'%' or NombrePaciente like '%'+@buscar+'%' or NombreDoctor like '%'+@buscar+'%' or NombreRecep like '%'+@buscar+'%' or TipoAtencion like '%'+@buscar+'%' or EstadoCita like '%'+@buscar+'%') and @buscar <> '')
			order by HoraInicio;
		end
	else if @orden = 0
		begin
			select * from vwConsultaCita
			where HoraInicio >= @fechahorainicio
				and HoraFin <= @fechahorafin
				and ((CodCita like '%'+@buscar+'%' or NombrePaciente like '%'+@buscar+'%' or NombreDoctor like '%'+@buscar+'%' or NombreRecep like '%'+@buscar+'%' or TipoAtencion like '%'+@buscar+'%' or EstadoCita like '%'+@buscar+'%') and @buscar <> '')
			order by HoraFin;
		end
end
go

create procedure paConsultaHorario
(
	@fechahorainicio datetime,
	@fechahorafin	datetime,
	@buscar varchar(100),
	@orden bit
)
as
begin
	if @orden = 1
		begin
			select * from vwConsultaHorario
			where HoraInicio >= CAST(@fechahorainicio as time)
				and HoraFin <= CAST(@fechahorafin as time)
				and ((CodHorario like '%'+@buscar+'%' or NombreDoctor like '%'+@buscar+'%' or EstadoHorario like '%'+@buscar+'%') and @buscar <> '')
			order by HoraInicio;
		end
	else if @orden = 0
		begin
			select * from vwConsultaHorario
			where HoraInicio >= CAST(@fechahorainicio as time)
				and HoraFin <= CAST(@fechahorafin as time)
				and ((CodHorario like '%'+@buscar+'%' or NombreDoctor like '%'+@buscar+'%' or EstadoHorario like '%'+@buscar+'%') and @buscar <> '')
			order by HoraFin;
		end
end
go

create procedure paConsultaDoctor
(
	@fechahorainicio datetime,
	@fechahorafin	datetime,
	@buscar varchar(100),
	@orden bit
)
as
begin
	if @orden = 1
		begin
			select * from Doctor
			where (DniDoc like '%'+@buscar+'%' or NomDoc like '%'+@buscar+'%' or ApellDoc like '%'+@buscar+'%') and @buscar <> ''
			order by DniDoc;
		end
	else if @orden = 0
		begin
			select * from Doctor
			where (DniDoc like '%'+@buscar+'%' or NomDoc like '%'+@buscar+'%' or ApellDoc like '%'+@buscar+'%') and @buscar <> ''
			order by NomDoc;
		end
end
go

create procedure paConsultaPaciente
(
	@fechahorainicio datetime,
	@fechahorafin	datetime,
	@buscar varchar(100),
	@orden bit
)
as
begin
	if @orden = 1
		begin
			select * from Paciente
			where (DniPct like '%'+@buscar+'%' or NomPct like '%'+@buscar+'%' or ApellPct like '%'+@buscar+'%') and @buscar <> ''
			order by DniPct;
		end
	else if @orden = 0
		begin
			select * from Paciente
			where (DniPct like '%'+@buscar+'%' or NomPct like '%'+@buscar+'%' or ApellPct like '%'+@buscar+'%') and @buscar <> ''
			order by NomPct;
		end
end
go

create procedure paConsultaRecepcionista
(
	@fechahorainicio datetime,
	@fechahorafin	datetime,
	@buscar varchar(100),
	@orden bit
)
as
begin
	if @orden = 1
		begin
			select * from Recepcionista
			where (DniRecep like '%'+@buscar+'%' or NomRecep like '%'+@buscar+'%' or ApellRecep like '%'+@buscar+'%') and @buscar <> ''
			order by DniRecep;
		end
	else if @orden = 0
		begin
			select * from Recepcionista
			where (DniRecep like '%'+@buscar+'%' or NomRecep like '%'+@buscar+'%' or ApellRecep like '%'+@buscar+'%') and @buscar <> ''
			order by NomRecep;
		end
end
go

create procedure paConsultaConsultorio
(
	@fechahorainicio datetime,
	@fechahorafin	datetime,
	@buscar varchar(100),
	@orden bit
)
as
begin
	if @orden = 1
		begin
			select * from Consultorio
			where (CodConst like '%'+@buscar+'%' or NomConst like '%'+@buscar+'%') and @buscar <> ''
			order by CodConst;
		end
	else if @orden = 0
		begin
			select * from Consultorio
			where (CodConst like '%'+@buscar+'%' or NomConst like '%'+@buscar+'%') and @buscar <> ''
			order by NomConst;
		end
end
go

create procedure paConsultaTipoAtencion
(
	@fechahorainicio datetime,
	@fechahorafin	datetime,
	@buscar varchar(100),
	@orden bit
)
as
begin
	if @orden = 1
		begin
			select * from TipoAtencion
			where (IdTipoAtencion like '%'+@buscar+'%' or TipoAtencion like '%'+@buscar+'%') and @buscar <> ''
			order by IdTipoAtencion;
		end
	else if @orden = 0
		begin
			select * from TipoAtencion
			where (IdTipoAtencion like '%'+@buscar+'%' or TipoAtencion like '%'+@buscar+'%') and @buscar <> ''
			order by TipoAtencion;
		end
end
go

create procedure paConsultaEstadoCita
(
	@fechahorainicio datetime,
	@fechahorafin	datetime,
	@buscar varchar(100),
	@orden bit
)
as
begin
	if @orden = 1
		begin
			select * from EstadoCita
			where (IdEstadoCita like '%'+@buscar+'%' or EstadoCita like '%'+@buscar+'%') and @buscar <> ''
			order by IdEstadoCita;
		end
	else if @orden = 0
		begin
			select * from EstadoCita
			where (IdEstadoCita like '%'+@buscar+'%' or EstadoCita like '%'+@buscar+'%') and @buscar <> ''
			order by EstadoCita;
		end
end
go

create procedure paConsultaDoctorHorario
(
	@fechahorainicio datetime,
	@fechahorafin	datetime,
	@buscar varchar(100),
	@orden bit
)
as
begin
	if @orden = 1
		begin
			select * from Doctor_Horario
			where (DniDoc like '%'+@buscar+'%' or CodHorario like '%'+@buscar+'%') and @buscar <> ''
			order by DniDoc;
		end
	else if @orden = 0
		begin
			select * from Doctor_Horario
			where (DniDoc like '%'+@buscar+'%' or CodHorario like '%'+@buscar+'%') and @buscar <> ''
			order by CodHorario;
		end
end
go

create procedure paConsultaDoctorConsultorio
(
	@fechahorainicio datetime,
	@fechahorafin	datetime,
	@buscar varchar(100),
	@orden bit
)
as
begin
	if @orden = 1
		begin
			select * from Doctor_Consultorio
			where (DniDoc like '%'+@buscar+'%' or CodConst like '%'+@buscar+'%') and @buscar <> ''
			order by DniDoc;
		end
	else if @orden = 0
		begin
			select * from Doctor_Consultorio
			where (DniDoc like '%'+@buscar+'%' or CodConst like '%'+@buscar+'%') and @buscar <> ''
			order by CodConst;
		end
end
go

create procedure paConsultaDoctorEspecialidad
(
	@fechahorainicio datetime,
	@fechahorafin	datetime,
	@buscar varchar(100),
	@orden bit
)
as
begin
	if @orden = 1
		begin
			select * from Doctor_Especialidad
			where (DniDoc like '%'+@buscar+'%' or CodEspecialidad like '%'+@buscar+'%') and @buscar <> ''
			order by DniDoc;
		end
	else if @orden = 0
		begin
			select * from Doctor_Especialidad
			where (DniDoc like '%'+@buscar+'%' or CodEspecialidad like '%'+@buscar+'%') and @buscar <> ''
			order by CodEspecialidad;
		end
end
go

create procedure paConsultaDoctorEspecialidadHorario
(
	@fechahorainicio datetime,
	@fechahorafin	datetime,
	@buscar varchar(100),
	@orden bit
)
as
begin
	if @orden = 1
		begin
			select * from Doctor_Especialidad_Horario
			where (DniDoc like '%'+@buscar+'%' or CodEspecialidad like '%'+@buscar+'%' or CodHorario like '%'+@buscar+'%') and @buscar <> ''
			order by DniDoc;
		end
	else if @orden = 0
		begin
			select * from Doctor_Especialidad_Horario
			where (DniDoc like '%'+@buscar+'%' or CodEspecialidad like '%'+@buscar+'%' or CodHorario like '%'+@buscar+'%') and @buscar <> ''
			order by CodHorario;
		end
end
go

create procedure paConsultaDoctorEspecialidadConsultorio
(
	@fechahorainicio datetime,
	@fechahorafin	datetime,
	@buscar varchar(100),
	@orden bit
)
as
begin
	if @orden = 1
		begin
			select * from Doctor_Especialidad_Consultorio
			where (DniDoc like '%'+@buscar+'%' or CodEspecialidad like '%'+@buscar+'%' or CodConst like '%'+@buscar+'%') and @buscar <> ''
			order by DniDoc;
		end
	else if @orden = 0
		begin
			select * from Doctor_Especialidad_Consultorio
			where (DniDoc like '%'+@buscar+'%' or CodEspecialidad like '%'+@buscar+'%' or CodConst like '%'+@buscar+'%') and @buscar <> ''
			order by CodConst;
		end
end
go

create procedure paConsultaDoctorEspecialidadConsultorioHorario
(
	@fechahorainicio datetime,
	@fechahorafin	datetime,
	@buscar varchar(100),
	@orden bit
)
as
begin
	if @orden = 1
		begin
			select * from Doctor_Especialidad_Consultorio_Horario
			where (DniDoc like '%'+@buscar+'%' or CodEspecialidad like '%'+@buscar+'%' or CodConst like '%'+@buscar+'%' or CodHorario like '%'+@buscar+'%') and @buscar <> ''
			order by DniDoc;
		end
	else if @orden = 0
		begin
			select * from Doctor_Especialidad_Consultorio_Horario
			where (DniDoc like '%'+@buscar+'%' or CodEspecialidad like '%'+@buscar+'%' or CodConst like '%'+@buscar+'%' or CodHorario like '%'+@buscar+'%') and @buscar <> ''
			order by CodHorario;
		end
end
go

create procedure paConsultaDoctorEspecialidadConsultorioHorarioRecepcionista
(
	@fechahorainicio datetime,
	@fechahorafin	datetime,
	@buscar varchar(100),
	@orden bit
)
as
begin
	if @orden = 1
		begin
			select dech.*, r.NomRecep+' '+r.ApellRecep as NombreRecep 
			from Doctor_Especialidad_Consultorio_Horario dech
			cross join Recepcionista r
			where (CAST(dech.DniDoc as varchar) like '%'+@buscar+'%' 
				or CAST(dech.CodEspecialidad as varchar) like '%'+@buscar+'%' 
				or CAST(dech.CodConst as varchar) like '%'+@buscar+'%' 
				or CAST(dech.CodHorario as varchar) like '%'+@buscar+'%'
				or CAST(r.DniRecep as varchar) like '%'+@buscar+'%'
				or r.NomRecep like '%'+@buscar+'%'
				or r.ApellRecep like '%'+@buscar+'%') 
				and @buscar <> ''
			order by dech.DniDoc;
		end
	else if @orden = 0
		begin
			select dech.*, r.NomRecep+' '+r.ApellRecep as NombreRecep 
			from Doctor_Especialidad_Consultorio_Horario dech
			cross join Recepcionista r
			where (CAST(dech.DniDoc as varchar) like '%'+@buscar+'%' 
				or CAST(dech.CodEspecialidad as varchar) like '%'+@buscar+'%' 
				or CAST(dech.CodConst as varchar) like '%'+@buscar+'%' 
				or CAST(dech.CodHorario as varchar) like '%'+@buscar+'%'
				or CAST(r.DniRecep as varchar) like '%'+@buscar+'%'
				or r.NomRecep like '%'+@buscar+'%'
				or r.ApellRecep like '%'+@buscar+'%') 
				and @buscar <> ''
			order by dech.CodHorario;
		end
end
go



-- =============================================
-- PROCEDIMIENTOS DE REPORTES
-- =============================================

create procedure paReporteCitasPorDoctor
(
	@fechainicio date,
	@fechafin date,
	@dniDoctor numeric(8) = null
)
as
begin
	select d.DniDoc, d.NomDoc+' '+d.ApellDoc as NombreDoctor,
		   count(c.CodCita) as TotalCitas,
		   sum(case when ec.IdEstadoCita = 1 then 1 else 0 end) as CitasCompletadas,
		   sum(case when ec.IdEstadoCita = 2 then 1 else 0 end) as CitasPendientes,
		   sum(case when ec.IdEstadoCita = 3 then 1 else 0 end) as CitasCanceladas
	from Doctor d
	left join Cita c on d.DniDoc = c.DniDoc 
		and cast(c.HoraInicio as date) between @fechainicio and @fechafin
	left join EstadoCita ec on c.IdEstadoCita = ec.IdEstadoCita
	where (@dniDoctor is null or d.DniDoc = @dniDoctor)
	group by d.DniDoc, d.NomDoc, d.ApellDoc
	order by TotalCitas desc
end
go

create procedure paReporteCitasPorFecha
(
	@fechainicio date,
	@fechafin date
)
as
begin
	select cast(c.HoraInicio as date) as Fecha,
		   count(c.CodCita) as TotalCitas,
		   sum(case when ec.IdEstadoCita = 1 then 1 else 0 end) as CitasCompletadas,
		   sum(case when ec.IdEstadoCita = 2 then 1 else 0 end) as CitasPendientes,
		   sum(case when ec.IdEstadoCita = 3 then 1 else 0 end) as CitasCanceladas
	from Cita c
	inner join EstadoCita ec on c.IdEstadoCita = ec.IdEstadoCita
	where cast(c.HoraInicio as date) between @fechainicio and @fechafin
	group by cast(c.HoraInicio as date)
	order by Fecha
end
go

-- =============================================
-- PROCEDIMIENTOS ADICIONALES
-- =============================================

create procedure paValidarDisponibilidadDoctor
(
	@dniDoctor numeric(8),
	@fechaCita date,
	@horaInicio time,
	@horaFin time
)
as
begin
	declare @citasConflicto int
	
	select @citasConflicto = count(*)
	from Cita
	where DniDoc = @dniDoctor
		and cast(HoraInicio as date) = @fechaCita
		and ((cast(HoraInicio as time) <= @horaInicio and cast(HoraFin as time) > @horaInicio)
			 or (cast(HoraInicio as time) < @horaFin and cast(HoraFin as time) >= @horaFin)
			 or (cast(HoraInicio as time) >= @horaInicio and cast(HoraFin as time) <= @horaFin))
	
	if @citasConflicto > 0
		select 0 as Disponible, 'El doctor ya tiene una cita en ese horario' as Mensaje
	else
		select 1 as Disponible, 'Horario disponible' as Mensaje
end
go

create procedure paObtenerHorariosDisponiblesDoctor
(
	@dniDoctor numeric(8),
	@fecha date
)
as
begin
	select h.CodHorario, h.HoraInicio, h.HoraFin, h.LimitPct,
		   count(c.CodCita) as CitasOcupadas,
		   (h.LimitPct - count(c.CodCita)) as EspaciosDisponibles,
		   case 
			   when count(c.CodCita) < h.LimitPct then 'Disponible'
			   else 'Completo'
		   end as Estado
	from Doctor_Horario dh
	inner join Horario h on dh.CodHorario = h.CodHorario
	left join Cita c on dh.DniDoc = c.DniDoc 
		and cast(c.HoraInicio as date) = @fecha
		and cast(c.HoraInicio as time) >= h.HoraInicio
		and cast(c.HoraFin as time) <= h.HoraFin
	where dh.DniDoc = @dniDoctor 
		and h.EstadoHorario = 1
	group by h.CodHorario, h.HoraInicio, h.HoraFin, h.LimitPct
	order by h.HoraInicio
end
go

create procedure paObtenerDoctoresPorEspecialidad
(
	@codEspecialidad varchar(10),
	@fecha date = null
)
as
begin
	select d.DniDoc, d.NomDoc+' '+d.ApellDoc as NombreDoctor,
		   e.NomEspecialidad, c.NomConst as Consultorio,
		   case 
			   when @fecha is not null then
				   (select count(*) 
					from Cita ci 
					where ci.DniDoc = d.DniDoc 
					and cast(ci.HoraInicio as date) = @fecha)
			   else 0
		   end as CitasDelDia
	from Doctor d
	inner join Doctor_Especialidad de on d.DniDoc = de.DniDoc
	inner join Especialidad e on de.CodEspecialidad = e.CodEspecialidad
	inner join Consultorio c on d.CodConst = c.CodConst
	where e.CodEspecialidad = @codEspecialidad
	order by NombreDoctor
end
go

create procedure paObtenerResumenCitasDia
(
	@fecha date
)
as
begin
	select 
		count(*) as TotalCitas,
		sum(case when ec.IdEstadoCita = 1 then 1 else 0 end) as CitasCompletadas,
		sum(case when ec.IdEstadoCita = 2 then 1 else 0 end) as CitasPendientes,
		sum(case when ec.IdEstadoCita = 3 then 1 else 0 end) as CitasCanceladas,
		count(distinct c.DniDoc) as DoctoresConCitas,
		count(distinct c.DniPct) as PacientesAtendidos
	from Cita c
	inner join EstadoCita ec on c.IdEstadoCita = ec.IdEstadoCita
	where cast(c.HoraInicio as date) = @fecha
end
go

create procedure paObtenerProximasCitasPaciente
(
	@dniPaciente numeric(8),
	@cantidadDias int = 30
)
as
begin
	select c.CodCita, 
		   d.NomDoc+' '+d.ApellDoc as NombreDoctor,
		   e.NomEspecialidad,
		   cs.NomConst,
		   c.HoraInicio,
		   c.HoraFin,
		   ta.TipoAtencion,
		   ec.EstadoCita
	from Cita c
	inner join Doctor d on c.DniDoc = d.DniDoc
	inner join Doctor_Especialidad de on d.DniDoc = de.DniDoc
	inner join Especialidad e on de.CodEspecialidad = e.CodEspecialidad
	inner join Consultorio cs on d.CodConst = cs.CodConst
	inner join TipoAtencion ta on c.IdTipoAtencion = ta.IdTipoAtencion
	inner join EstadoCita ec on c.IdEstadoCita = ec.IdEstadoCita
	where c.DniPct = @dniPaciente
		and c.HoraInicio >= getdate()
		and c.HoraInicio <= dateadd(day, @cantidadDias, getdate())
	order by c.HoraInicio
end
go

create procedure paObtenerAgendaDoctor
(
	@dniDoctor numeric(8),
	@fechaInicio date,
	@fechaFin date
)
as
begin
	select c.CodCita,
		   c.HoraInicio,
		   c.HoraFin,
		   p.NomPct+' '+p.ApellPct as NombrePaciente,
		   p.TelfPct as TelefonoPaciente,
		   ta.TipoAtencion,
		   ec.EstadoCita,
		   c.Observaciones
	from Cita c
	inner join Paciente p on c.DniPct = p.DniPct
	inner join TipoAtencion ta on c.IdTipoAtencion = ta.IdTipoAtencion
	inner join EstadoCita ec on c.IdEstadoCita = ec.IdEstadoCita
	where c.DniDoc = @dniDoctor
		and cast(c.HoraInicio as date) between @fechaInicio and @fechaFin
	order by c.HoraInicio
end
go

create procedure paObtenerEspecialidadesDisponibles
as
begin
	select e.CodEspecialidad, 
		   e.NomEspecialidad, 
		   e.DescripcionEsp,
		   count(de.DniDoc) as CantidadDoctores
	from Especialidad e
	left join Doctor_Especialidad de on e.CodEspecialidad = de.CodEspecialidad
	group by e.CodEspecialidad, e.NomEspecialidad, e.DescripcionEsp
	having count(de.DniDoc) > 0
	order by e.NomEspecialidad
end
go

create procedure paObtenerConsultoriosDisponibles
(
	@fecha date = null,
	@horaInicio time = null,
	@horaFin time = null
)
as
begin
	if @fecha is null or @horaInicio is null or @horaFin is null
	begin
		-- Si no se proporcionan parámetros, mostrar todos los consultorios activos
		select c.CodConst, c.NomConst, c.UbicacionConst,
			   count(d.DniDoc) as CantidadDoctores
		from Consultorio c
		left join Doctor d on c.CodConst = d.CodConst
		where c.EstadoConst = 1
		group by c.CodConst, c.NomConst, c.UbicacionConst
		order by c.NomConst
	end
	else
	begin
		-- Mostrar consultorios disponibles en el horario especificado
		select c.CodConst, c.NomConst, c.UbicacionConst,
			   case 
				   when exists(
					   select 1 from Cita ci
					   inner join Doctor d on ci.DniDoc = d.DniDoc
					   where d.CodConst = c.CodConst
					   and cast(ci.HoraInicio as date) = @fecha
					   and ((cast(ci.HoraInicio as time) <= @horaInicio and cast(ci.HoraFin as time) > @horaInicio)
							or (cast(ci.HoraInicio as time) < @horaFin and cast(ci.HoraFin as time) >= @horaFin)
							or (cast(ci.HoraInicio as time) >= @horaInicio and cast(ci.HoraFin as time) <= @horaFin))
				   ) then 'Ocupado'
				   else 'Disponible'
			   end as Estado
		from Consultorio c
		where c.EstadoConst = 1
		order by c.NomConst
	end
end
go

create procedure paValidarCredencialesRecepcionista
(
	@dni numeric(8),
	@contrasena varchar(20)
)
as
begin
	declare @existe bit = 0
	declare @esAdmin bit = 0
	declare @nombre varchar(101)
	
	if exists(select DniRecep from Recepcionista where DniRecep = @dni and Contrasena = @contrasena)
	begin
		set @existe = 1
		select @esAdmin = EsAdmin, @nombre = NomRecep+' '+ApellRecep
		from Recepcionista 
		where DniRecep = @dni
	end
	
	select @existe as CredencialesValidas, 
		   @esAdmin as EsAdministrador,
		   @nombre as NombreCompleto
end
go

