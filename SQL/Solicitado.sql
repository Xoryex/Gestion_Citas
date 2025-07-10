-----------------------------------------------------------------
/*5*/

CREATE OR ALTER PROCEDURE paAsignarEspecialidadHorario_Doctor
(
    @DniDoc VARCHAR(20),
    @Especialidad VARCHAR(100),
    @HoraInicio TIME(7),
    @HoraFin TIME(7)
)
AS
BEGIN
    DECLARE @CodEspecialidad NUMERIC(8,0)
    DECLARE @CodHorario INT

    -- Validar existencia del doctor
    IF NOT EXISTS (SELECT * FROM Doctor WHERE DniDoc = @DniDoc)
    BEGIN
        RAISERROR('El Doctor no existe', 16, 1)
        return @@error;
    END

    -- Obtener codigo de especialidad
    SELECT @CodEspecialidad = CodEspecia FROM Especialidad WHERE Especialidad = @Especialidad

    IF @CodEspecialidad IS NULL
    BEGIN
        RAISERROR('Especialidad no existe', 16, 1)
        return @@error;
    END

    -- Obtener codigo de horario por hora inicio y fin
    SELECT @CodHorario = CodHorario 
    FROM Horario 
    WHERE HoraInicio = @HoraInicio AND HoraFin = @HoraFin

    IF @CodHorario IS NULL
    BEGIN
        RAISERROR('Horario no existe', 16, 1)
        return @@error;
    END

    -- Verificar si el horario este desactivado
    IF EXISTS (SELECT * FROM Horario WHERE CodHorario = @CodHorario AND EstadoHorario = 0)
    BEGIN
        RAISERROR('El horario est� desactivado', 16, 1)
        return @@error;
    END

    -- Verificar si ya tiene esa especialidad asignada
    IF EXISTS (SELECT * FROM Especialidad_Doctor WHERE DniDoc = @DniDoc AND CodEspecia = @CodEspecialidad)
    BEGIN
        RAISERROR('Especialidad ya fue asignada al doctor', 16, 1)
        return @@error;
    END

    -- Verificar si ya tiene ese horario asignado
    IF EXISTS (SELECT * FROM Doctor_Horarion WHERE DniDoc = @DniDoc AND CodHorario = @CodHorario)
    BEGIN
        RAISERROR('Horario ya fue asignado al doctor', 16, 1)
        return @@error;
    END

    INSERT INTO Doctor_Horario (DniDoc, CodHorario)
    VALUES (@DniDoc, @CodHorario)

    INSERT INTO Especialidad_Doctor (DniDoc, CodEspecia)
    VALUES (@DniDoc, @CodEspecialidad)
END
GO

EXEC paAsignarEspecialidadHorario_Doctor
    @DniDoc = '28374659',
    @Especialidad = 'Cardiolog�a',
    @HoraInicio = '08:00:00',
    @HoraFin = '09:00:00';

--------------------------------------------------
/*1*/
CREATE OR ALTER PROCEDURE PA_RegistrarCitaMedica
(@CodCita INT,
 @DniPct INT,
 @DniDoc INT,
 @DniRecep INT,
 @IdTipoAtencion NUMERIC(8,0),
 @CodHorario INT,
 @FechaCita DATE,
 @HoraInicio TIME,
 @HoraFin TIME
)
AS
BEGIN
	IF EXISTS (SELECT * FROM Cita WHERE CodCita = @CodCita)
    BEGIN
        RAISERROR('Este codigo ya fue asignado a una cita', 16, 1);
        return @@error; @@ERROR;
    END

    IF NOT EXISTS (SELECT * FROM Horario WHERE CodHorario = @CodHorario AND EstadoHorario = 1)
    BEGIN
        RAISERROR('El horario seleccionado no está activo.', 16, 1);
        return @@error; @@ERROR;
    END

    -- Validar que el doctor tenga asignado ese horario
    IF NOT EXISTS (SELECT * FROM Doctor_Horario WHERE DniDoc = @DniDoc AND CodHorario = @CodHorario)
    BEGIN
        RAISERROR('El doctor no tiene asignado ese horario.', 16, 1);
        return @@error; @@ERROR;
    END
	--varificar que exista el consultorio
    IF NOT EXISTS (SELECT * FROM Doctor_Horario WHERE DniDoc = @DniDoc AND CodHorario = @CodHorario)
    BEGIN
        RAISERROR('El doctor no tiene asignado ese horario.', 16, 1);
        return @@error; @@ERROR;
    END

    -- Validar que no se haya excedido el límite de pacientes
    DECLARE @LimitPct INT;
    SELECT @LimitPct = LimitPct FROM Horario WHERE CodHorario = @CodHorario;

    DECLARE @TotalAsignados INT;
    SELECT @TotalAsignados = COUNT(*) FROM Cita WHERE DniDoc = @DniDoc AND FechaCita = @FechaCita AND HoraInicio = @HoraInicio;

    IF @TotalAsignados >= @LimitPct
    BEGIN
        RAISERROR('Se ha alcanzado el límite de pacientes para ese horario.', 16, 1);
        return @@error; @@ERROR;
    END

    -- Insertar cita con CodCita manual
    INSERT INTO Cita (
        CodCita, DniPct, DniDoc, DniRecep, IdTipoAtencion, 
        CodHorario, FechaCita, HoraInicio, HoraFin, IdEstadoCita
    )
    VALUES (
        @CodCita, @DniPct, @DniDoc, @DniRecep, @IdTipoAtencion,
        @CodHorario, @FechaCita, @HoraInicio, @HoraFin, 1 );
END;

/*2*/
create or alter procedure PA_ModificarHorarioCita
(@CodCita int,
@NuevaFecha date,
@NuevaHoraInicio time,
@NuevaHoraFin time)
as
begin
    if not exists (select * from Cita where CodCita = @CodCita and IdEstadoCita != 3)
    begin
        raiserror('La cita no existe o ya está anulada.', 16, 1)
        return @@error;
    end

    -- Obtener DNI del doctor y paciente
    declare @DniDoc varchar(8), @DniPct varchar(8)
	--buscarmos en la tabla
    select @DniDoc = DniDoc, @DniPct = DniPct from Cita where CodCita = @CodCita

    -- Validar disponibilidad en nueva fecha/hora
    if exists (select * from Cita where DniDoc = @DniDoc and FechaCita = @NuevaFecha and HoraInicio = @NuevaHoraInicio
    and IdEstadoCita != 3)
    begin
        raiserror('El nuevo horario ya está ocupado por otra cita.', 16, 1)
        return @@error;
    end

    -- Actualizar cita
    update Cita
    set FechaReprogra = GETDATE(),
        FechaCita = @NuevaFecha,
        HoraInicio = @NuevaHoraInicio,
        HoraFin = @NuevaHoraFin,
        IdEstadoCita = 2 -- 2 = Reprogramada
    where CodCita = @CodCita
end
/*3*/
create or alter procedure PA_AnularCitaMedica
(@CodCita int,
@DniRecep int)
as
begin
    if not exists (
        select * from Cita where CodCita = @CodCita and IdEstadoCita != 3)
    begin
        raiserror('La cita no existe o ya fue anulada.', 16, 1)
        return
    end

    if not exists (select * from Recepcionista where DniRecep = @DniRecep)
    begin
        raiserror('Recepcionista no está registrado en la tabla', 16, 1)
        return
    end

    update Cita
    set IdEstadoCita = 3, 
        FechaReprogra = GETDATE()
    where CodCita = @CodCita
end
/*4*/
create or alter procedure PA_ObtenerAgendaDoctor
(@DniDoc as int,
@FechaInicio date,
@FechaFin date
)
as
begin
    select 
        c.CodCita,
        p.DniPct,
        p.NomPct + ' ' + p.ApellPct as NombrePaciente,
        c.FechaCita,
        c.HoraInicio,
        c.HoraFin,
        ta.TipoAtencion,
        ec.EstadoCita
    from Cita c
    join Paciente p on c.DniPct = p.DniPct
    join TipoAtencion ta on c.IdTipoAtencion = ta.IdTipoAtencion
    join EstadoCita ec on c.IdEstadoCita = ec.IdEstadoCita
    where c.DniDoc = @DniDoc
      and c.FechaCita between @FechaInicio and @FechaFin
      and c.IdEstadoCita in (1, 2)
    order by c.FechaCita, c.HoraInicio
end
select * from Cita