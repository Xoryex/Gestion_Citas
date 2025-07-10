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
    IF NOT EXISTS (SELECT 1 FROM Doctor WHERE DniDoc = @DniDoc)
    BEGIN
        RAISERROR('El Doctor no existe', 16, 1)
        RETURN
    END

    -- Obtener c�digo de especialidad
    SELECT @CodEspecialidad = CodEspecia 
    FROM Especialidad 
    WHERE Especialidad = @Especialidad

    IF @CodEspecialidad IS NULL
    BEGIN
        RAISERROR('Especialidad no existe', 16, 1)
        RETURN
    END

    -- Obtener c�digo de horario por hora inicio y fin
    SELECT @CodHorario = CodHorario 
    FROM Horario 
    WHERE HoraInicio = @HoraInicio AND HoraFin = @HoraFin

    IF @CodHorario IS NULL
    BEGIN
        RAISERROR('Horario no existe', 16, 1)
        RETURN
    END

    -- Verificar si el horario est� desactivado
    IF EXISTS (
        SELECT 1 FROM Horario 
        WHERE CodHorario = @CodHorario AND EstadoHorario = 0
    )
    BEGIN
        RAISERROR('El horario est� desactivado', 16, 1)
        RETURN
    END

    -- Verificar si ya tiene esa especialidad asignada
    IF EXISTS (
        SELECT 1 FROM Especialidad_Doctor 
        WHERE DniDoc = @DniDoc AND CodEspecia = @CodEspecialidad
    )
    BEGIN
        RAISERROR('Especialidad ya fue asignada al doctor', 16, 1)
        RETURN
    END

    -- Verificar si ya tiene ese horario asignado
    IF EXISTS (
        SELECT * FROM Doctor_Horario 
        WHERE DniDoc = @DniDoc AND CodHorario = @CodHorario
    )
    BEGIN
        RAISERROR('Horario ya fue asignado al doctor', 16, 1)
        RETURN
    END

    -- Inserci�n en tablas de relaci�n
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

