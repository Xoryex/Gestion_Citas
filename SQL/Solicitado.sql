create or alter procedure paAsignarEspecialidadHorario_Doctor
(
	@dni int,
	@idespecialidad numeric (8),
	@idhorario int
)
as
begin

	if not exists(select DniDoc from Doctor where DniDoc=@dni) begin
		raiserror('El Doctor no existe',16,1)
		return @@error
	end
	else if not exists(select CodEspecia from Especialidad where CodEspecia=@idespecialidad) begin
		raiserror('Especialidad no existe',16,1)
		return @@error
	end
	else if not exists(select CodHorario from Horario where CodHorario=@idhorario) begin
		raiserror('horario no existe',16,1)
		return @@error
	end
	else if exists(select CodEspecia from Especialidad_Doctor where CodEspecia=@idespecialidad and DniDoc=@dni) begin
		raiserror('especialidad ya fue asignada',16,1)
		return @@error
	end
	else if exists(select CodHorario from Doctor_Horario where CodHorario=@idhorario and DniDoc=@dni) begin
		raiserror('horario ya fue asignado',16,1)
		return @@error
	end
	else if (select EstadoHorario from Horario where CodHorario=@idhorario)=0 begin
		raiserror('horario esta desactivado',16,1)
		return @@error
	end
	else begin
		insert into Doctor_Horario (DniDoc,CodHorario) values (@dni,@idhorario)
		insert into Especialidad_Doctor (DniDoc,CodEspecia)values (@dni,@idespecialidad)
	end

end
go