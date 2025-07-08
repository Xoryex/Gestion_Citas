
----------------------------------------------------------------------
-- CRUD: TABLA CONSULTORIO
----------------------------------------------------------------------
--INSERTAR
CREATE OR ALTER PROCEDURE PA_CRUD_InsertarConsultorio
(
    @CodConst     AS NUMERIC(8,0), 
    @NomConst     AS VARCHAR(50),
    @Especialidad   AS VARCHAR(100)
)
AS
BEGIN
    DECLARE @CodEspecia NUMERIC(8,0)

    -- Verificar si la especialidad existe y obtener su código
    SELECT @CodEspecia = CodEspecia
    FROM Especialidad
    WHERE Especialidad = @Especialidad

    IF @CodEspecia IS NULL
    BEGIN
        RAISERROR('¡Esta especialidad no existe!', 16, 1)
        RETURN
    END

    -- Verificar si el consultorio ya existe
    IF EXISTS(SELECT 1 FROM Consultorio WHERE CodConst = @CodConst)
    BEGIN
        RAISERROR('¡Este consultorio ya existe!', 16, 1)
        RETURN
    END

    -- Insertar el nuevo consultorio
    INSERT INTO dbo.Consultorio (CodConst, NomConst, CodEspecia)
    VALUES (@CodConst, @NomConst, @CodEspecia)
END
GO


--MODIFICAR
CREATE OR ALTER PROCEDURE PA_CRUD_ModificarConsultorio
(
    @CodConst    AS NUMERIC(8,0),
    @NomConst    AS VARCHAR(50),
    @Especialidad  AS VARCHAR(100)
)
AS
BEGIN
    DECLARE @CodEspecia NUMERIC(8,0)

    -- Verificar si el consultorio existe
    IF NOT EXISTS(SELECT 1 FROM Consultorio WHERE CodConst = @CodConst)
    BEGIN
        RAISERROR('¡Este consultorio no existe!', 16, 1)
        RETURN @@ERROR
    END

    -- Obtener código de la especialidad a partir del nombre
    SELECT @CodEspecia = CodEspecia
    FROM Especialidad
    WHERE Especialidad = @Especialidad

    IF @CodEspecia IS NULL
    BEGIN
        RAISERROR('¡La especialidad indicada no existe!', 16, 1)
        RETURN @@ERROR
    END

    -- Modificar el consultorio
    UPDATE dbo.Consultorio
    SET NomConst = @NomConst,
        CodEspecia = @CodEspecia
    WHERE CodConst = @CodConst
END
GO

--ELIMINAR
CREATE OR ALTER PROCEDURE PA_CRUD_EliminarConsultorio
(
    @CodConst AS NUMERIC(8,0)
)
AS
BEGIN
    -- Verificar si el consultorio existe
    IF NOT EXISTS (SELECT 1 FROM Consultorio WHERE CodConst = @CodConst)
    BEGIN
        RAISERROR('Este consultorio no existe.', 16, 1)
        RETURN
    END

    -- Verificar si hay relación con doctores
    IF EXISTS (SELECT 1 FROM Doctor WHERE CodConst = @CodConst)
    BEGIN
        RAISERROR('Este consultorio no puede ser eliminado porque está relacionado con doctores.', 16, 1)
        RETURN
    END

    -- Eliminar el consultorio
    DELETE FROM Consultorio
    WHERE CodConst = @CodConst
END
GO


--LISTAR con filtro
CREATE OR ALTER PROCEDURE PA_CRUD_ListarConsultaConFiltro
(
    @Filtro VARCHAR(150)
)
AS
BEGIN
    SELECT *
    FROM Vw_ListarConsulta
    WHERE 
        Codigo LIKE '%' + @Filtro + '%' OR
        NombreConsultorio LIKE '%' + @Filtro + '%'
        
END
GO


--filtrar sin filtro
CREATE OR ALTER PROCEDURE PA_CRUD_ListarConsulta
AS
BEGIN
    SELECT * 
    FROM Vw_ListarConsulta
END
GO


--CREAR LA VISTA
CREATE OR ALTER VIEW Vw_ListarConsulta
AS
SELECT 
    CodConst AS Codigo, 
    NomConst AS NombreConsultorio
FROM Consultorio
GO


----------------------------------------------------------------------------------------
----------------------------------------------------------------------
-- CRUD: TABLA HORARIO
----------------------------------------------------------------------

-- INSERTAR
CREATE OR ALTER PROCEDURE PA_CRUD_InsertarHorario
(
    @CodHorario     int,
    @Dia            VARCHAR(10),
    @HoraInicio     TIME,
    @HoraFin        TIME,
    @LimitPct       INT,
    @EstadoHorario  BIT
)
AS
BEGIN
    DECLARE @TurnoNombre VARCHAR(10)
    DECLARE @IdTurnoHorario INT

    -- Validar si el código ya existe
    IF EXISTS(SELECT 1 FROM Horario WHERE CodHorario = @CodHorario)
    BEGIN
        RAISERROR('Código ya existe en la tabla Horario', 16, 1)
        RETURN
    END

    -- Validar que la hora de inicio sea menor que la hora de fin
    IF @HoraInicio >= @HoraFin
    BEGIN
        RAISERROR('La hora de inicio debe ser menor que la hora de fin', 16, 1)
        RETURN
    END

    -- Determinar el nombre del turno automáticamente usando HoraInicio
    SET @TurnoNombre = CASE 
                          WHEN @HoraInicio < '12:00:00' THEN 'mañana'
                          ELSE 'tarde'
                       END

    -- Obtener el IdTurnoHorario correspondiente
    SELECT @IdTurnoHorario = IdTurnoHorario
    FROM TurnoHorario
    WHERE TurnoHorario = @TurnoNombre

    IF @IdTurnoHorario IS NULL
    BEGIN
        RAISERROR('No se encontró el turno correspondiente en la tabla TurnoHorario', 16, 1)
        RETURN
    END

    -- Insertar el nuevo horario
    INSERT INTO dbo.Horario
        (CodHorario, Dia, HoraInicio, HoraFin, LimitPct, EstadoHorario, IdTurnoHorario)
    VALUES
        (@CodHorario, @Dia, @HoraInicio, @HoraFin, @LimitPct, @EstadoHorario, @IdTurnoHorario)
END
GO



-- MODIFICAR
CREATE OR ALTER PROCEDURE PA_CRUD_ModificarHorario
(
    @CodHorario     int,
    @HoraInicio     TIME,
    @Dia            VARCHAR(10),
    @LimitPct       INT,
    @EstadoHorario  BIT
)
AS
BEGIN
    DECLARE @TurnoNombre VARCHAR(10)
    DECLARE @IdTurnoHorario INT

    -- Validar si el horario existe
    IF NOT EXISTS (SELECT 1 FROM Horario WHERE CodHorario = @CodHorario)
    BEGIN
        RAISERROR('Código NO existe en la tabla Horario', 16, 1)
        RETURN
    END

    -- Determinar el nombre del turno usando HoraInicio
    SET @TurnoNombre = CASE 
                          WHEN @HoraInicio < '12:00:00' THEN 'mañana'
                          ELSE 'tarde'
                       END

    -- Obtener el IdTurnoHorario correspondiente
    SELECT @IdTurnoHorario = IdTurnoHorario
    FROM TurnoHorario
    WHERE TurnoHorario = @TurnoNombre

    IF @IdTurnoHorario IS NULL
    BEGIN
        RAISERROR('No se encontró el turno correspondiente en la tabla TurnoHorario', 16, 1)
        RETURN
    END

    -- Actualizar el horario
    UPDATE dbo.Horario
    SET 
        HoraInicio = @HoraInicio,
        Dia = @Dia,
        LimitPct = @LimitPct,
        EstadoHorario = @EstadoHorario,
        IdTurnoHorario = @IdTurnoHorario
    WHERE CodHorario = @CodHorario
END
GO


-- ELIMINAR
CREATE OR ALTER PROCEDURE PA_CRUD_EliminarHorario
(
    @CodHorario int
)
AS
BEGIN
    -- Validar si el horario existe
    IF NOT EXISTS (SELECT 1 FROM Horario WHERE CodHorario = @CodHorario)
    BEGIN
        RAISERROR('Código NO existe en la tabla Horario.', 16, 1)
        RETURN
    END

    -- Verificar si está relacionado con algún doctor
    IF EXISTS (SELECT 1 FROM Doctor_Horario WHERE CodHorario = @CodHorario)
    BEGIN
        RAISERROR('No se puede eliminar porque está relacionado con doctores.', 16, 1)
        RETURN
    END

    -- Verificar si está referenciado en citas
    IF EXISTS (SELECT 1 FROM Cita WHERE CodHorario = @CodHorario)
    BEGIN
        RAISERROR('No se puede eliminar porque está referenciado en una cita.', 16, 1)
        RETURN
    END

    -- Eliminar el horario
    DELETE FROM Horario
    WHERE CodHorario = @CodHorario
END
GO




-- VISTA
CREATE OR ALTER VIEW Vw_ListarHorario
AS
SELECT 
    h.CodHorario       AS Codigo,
    h.Dia,
    h.HoraInicio,
    h.HoraFin,
    th.TurnoHorario    AS Turno,
    h.LimitPct         AS Limite_Pacientes,
    CASE h.EstadoHorario
        WHEN 1 THEN 'Habilitado'
        WHEN 0 THEN 'Deshabilitado'
    END                AS Estado
FROM Horario h
INNER JOIN TurnoHorario th ON h.IdTurnoHorario = th.IdTurnoHorario;
GO



-- LISTAR TODO
CREATE OR ALTER PROCEDURE PA_CRUD_ListarHorario
AS
BEGIN
    SELECT * FROM Vw_ListarHorario;
END
GO


-- LISTAR CON FILTRO
CREATE OR ALTER PROCEDURE PA_CRUD_ListarHorarioConFiltro 
    @FiltroTexto NVARCHAR(100) = NULL
AS
BEGIN
    SET NOCOUNT ON;
    
    SELECT 
        h.CodHorario AS [ID Horario],
        h.HoraInicio AS [Hora Inicio],
        h.HoraFin AS [Hora Fin],
        h.Dia AS [Día],
        h.LimitPct AS [Límite de Pacientes],
        h.EstadoHorario AS [Estado del Horario],
        d.DniDoc AS [DNI del Doctor],
        d.NomDoc AS [Nombre del Doctor],
        c.NomConst AS [Consultorio]
    FROM dbo.Horario h
    LEFT JOIN dbo.Doctor_Horario dh ON h.CodHorario = dh.CodHorario
    LEFT JOIN dbo.Doctor d ON dh.DniDoc = d.DniDoc
    LEFT JOIN dbo.Consultorio c ON d.CodConst = c.CodConst
    WHERE 
        (@FiltroTexto IS NULL OR @FiltroTexto = '') OR
        (
            h.CodHorario LIKE '%' + @FiltroTexto + '%' OR
            h.Dia LIKE '%' + @FiltroTexto + '%' OR
            CAST(h.HoraInicio AS NVARCHAR) LIKE '%' + @FiltroTexto + '%' OR
            CAST(h.HoraFin AS NVARCHAR) LIKE '%' + @FiltroTexto + '%' OR
            CAST(h.LimitPct AS NVARCHAR) LIKE '%' + @FiltroTexto + '%' OR
            h.EstadoHorario LIKE '%' + @FiltroTexto + '%' OR
            d.DniDoc LIKE '%' + @FiltroTexto + '%' OR
            d.NomDoc LIKE '%' + @FiltroTexto + '%' OR
            c.NomConst LIKE '%' + @FiltroTexto + '%'
        )
    ORDER BY 
        h.CodHorario, 
        h.Dia, 
        h.HoraInicio;
END
go


-------------------------------------------------------------------------------------------

------------------------------------------------------------------------
-- CRUD: TABLA RECEPCIONISTA
----------------------------------------------------------------------

--listar recepcionistas
CREATE OR ALTER VIEW Vw_ListarRecepcionista
AS
SELECT 
    DniRecep AS 'DNI',
    NomRecep AS 'Nombres',
    ApellRecep AS 'Apellidos',
    TelfRecep AS 'Telefono',
    CASE EsAdmin
        WHEN 1 THEN 'Sí'
        WHEN 0 THEN 'No'
    END AS 'Es_Administrador'
FROM Recepcionista;
GO

--INSERTAR un nuevo recepcionista

CREATE OR ALTER PROCEDURE PA_CRUD_InsertarRecepcionista
(
    @DniRecep int,
    @NomRecep VARCHAR(50),
    @ApellRecep VARCHAR(50),
    @TelfRecep numeric (9),
    @Contrasena VARCHAR(20),
    @EsAdmin BIT
)
AS
BEGIN
    IF EXISTS(SELECT 1 FROM Recepcionista WHERE DniRecep = @DniRecep)
    BEGIN
        RAISERROR('Error: El Recepcionista ya existe.', 16, 1);
    END
    ELSE
    BEGIN
        INSERT INTO dbo.Recepcionista(DniRecep, NomRecep, ApellRecep, TelfRecep, Contrasena, EsAdmin)
        VALUES (@DniRecep, @NomRecep, @ApellRecep, @TelfRecep, @Contrasena, @EsAdmin);
    END
    RETURN @@ERROR;
END
GO

--MODIFICAR un recepcionista existente
CREATE OR ALTER PROCEDURE PA_CRUD_ModificarRecepcionista
(
    @DniRecep int,
    @NomRecep VARCHAR(80),
    @ApellRecep VARCHAR(80),
    @TelfRecep numeric (9),
    @Contrasena VARCHAR(20),
    @EsAdmin BIT
)
AS
BEGIN
    IF NOT EXISTS(SELECT 1 FROM Recepcionista WHERE DniRecep = @DniRecep)
    BEGIN
        RAISERROR('Error: El Recepcionista no existe.', 16, 1);
    END
    ELSE
    BEGIN
        UPDATE dbo.Recepcionista
        SET 
            NomRecep = @NomRecep,
            ApellRecep = @ApellRecep,
            TelfRecep = @TelfRecep,
            Contrasena = @Contrasena,
            EsAdmin = @EsAdmin
        WHERE DniRecep = @DniRecep;
    END
    RETURN @@ERROR;
END
GO

--ELIMINAR recepcionista
CREATE OR ALTER PROCEDURE PA_CRUD_EliminarRecepcionista
(@DniRecep AS int)
AS
BEGIN
    IF NOT EXISTS(SELECT 1 FROM Recepcionista WHERE DniRecep = @DniRecep)
    BEGIN
        RAISERROR('Error: El Recepcionista no existe.', 16, 1);
    END

    ELSE IF EXISTS(SELECT 1 FROM Cita WHERE DniRecep = @DniRecep)
    BEGIN
        RAISERROR('Error: No se puede eliminar el recepcionista porque está relacionado con una o más citas.', 16, 1);
    END
    ELSE
    BEGIN
        DELETE FROM Recepcionista
        WHERE DniRecep = @DniRecep;
    END
    RETURN @@ERROR;
END
GO

--LISTAR recepcionistas 
CREATE OR ALTER PROCEDURE PA_CRUD_ListarRecepcionista
AS
BEGIN
    SELECT * FROM Vw_ListarRecepcionista;
END
GO
--LISTAR recepcionistas con filtro
CREATE OR ALTER PROCEDURE PA_CRUD_ListarRecepcionistaConFiltro
(@Filtro VARCHAR(150))
AS
BEGIN
    SELECT * FROM Vw_ListarRecepcionista
    WHERE 
        CAST(DNI AS VARint) LIKE '%' + @Filtro + '%' OR 
        Nombres LIKE '%' + @Filtro + '%' OR
        Apellidos LIKE '%' + @Filtro + '%';
END
GO

--------------------------------------------------------------------------
/*CRUD PACIENTE*/
-------------------------------------------------------------------------

--Insertar

CREATE OR ALTER PROCEDURE PA_CRUD_InsertarPaciente
(
    @DniPct         int,
    @NomPct         VARCHAR(80),
    @ApellPct       VARCHAR(80),
    @TlfPct         numeric (9),
    @GeneroPct      CHAR(1), -- 'M' o 'F'
    @EmailPct       VARCHAR(50),
    @FechNaciPct    DATETIME,
    @DirecPct       VARCHAR(50),
    @OcupPct        VARCHAR(120),
    @GrupSangNombre VARCHAR(5), -- Nombre del grupo sanguíneo
    @ProcedenciaPct VARCHAR(80),
    @EstCivilPct    INT,
    @GrupEtnicoPct  VARCHAR(80),
    @CentrTrabPct   VARCHAR(80),
    @GradInstrPct   INT,
    @HijosPct       INT
)
AS
BEGIN
    DECLARE @GeneroBit BIT
    DECLARE @GrupSangPct INT

    -- Género: 'M' o 'F'
    IF @GeneroPct = 'M'
        SET @GeneroBit = 1
    ELSE IF @GeneroPct = 'F'
        SET @GeneroBit = 0
    ELSE
    BEGIN
        RAISERROR('Género no válido. Use M o F.', 16, 1)
        RETURN @@ERROR
    END

    -- Convertir grupo sanguíneo textual a número
    SET @GrupSangPct = 
        CASE @GrupSangNombre
            WHEN 'A+' THEN 1
            WHEN 'A−' THEN 2
            WHEN 'B+' THEN 3
            WHEN 'B−' THEN 4
            WHEN 'AB+' THEN 5
            WHEN 'AB−' THEN 6
            WHEN 'O+' THEN 7
            WHEN 'O−' THEN 8
            ELSE NULL
        END

    IF @GrupSangPct IS NULL
    BEGIN
        RAISERROR('Grupo sanguíneo no válido.', 16, 1)
        RETURN @@ERROR
    END

    -- Validar si el paciente ya existe
    IF EXISTS (SELECT 1 FROM Paciente WHERE DniPct = @DniPct)
    BEGIN
        RAISERROR('Este paciente ya existe!!', 16, 1)
        RETURN @@ERROR
    END

    -- Insertar paciente
    INSERT INTO dbo.Paciente (
        DniPct, NomPct, ApellPct, TlfPct, GeneroPct, EmailPct, FechNaciPct,
        DirecPct, OcupPct, GrupSangPct, ProcedenciaPct,
        EstCivilPct, GrupEtnicoPct, CentrTrabPct, GradInstrPct, HijosPct
    )
    VALUES (
        @DniPct, @NomPct, @ApellPct, @TlfPct, @GeneroBit, @EmailPct, @FechNaciPct,
        @DirecPct, @OcupPct, @GrupSangPct, @ProcedenciaPct,
        @EstCivilPct, @GrupEtnicoPct, @CentrTrabPct, @GradInstrPct, @HijosPct
    )
END
GO




--Modificar

CREATE OR ALTER PROCEDURE PA_CRUD_ModificarPaciente
(
    @DniPct         int,
    @NomPct         VARCHAR(80),
    @ApellPct       VARCHAR(80),
    @TlfPct         numeric (9),
    @GeneroPct      CHAR(1), -- 'M' o 'F'
    @EmailPct       VARCHAR(50),
    @FechNaciPct    DATETIME,
    @DirecPct       VARCHAR(50),
    @OcupPct        VARCHAR(120),
    @GrupSangNombre VARCHAR(5), -- Nombre del grupo sanguíneo
    @ProcedenciaPct VARCHAR(80),
    @EstCivilPct    INT,
    @GrupEtnicoPct  VARCHAR(80),
    @CentrTrabPct   VARCHAR(80),
    @GradInstrPct   INT,
    @HijosPct       INT
)
AS
BEGIN
    DECLARE @GeneroBit BIT
    DECLARE @GrupSangPct INT

    -- Género
    IF @GeneroPct = 'M'
        SET @GeneroBit = 1
    ELSE IF @GeneroPct = 'F'
        SET @GeneroBit = 0
    ELSE
    BEGIN
        RAISERROR('Género no válido. Use M o F.', 16, 1)
        RETURN @@ERROR
    END

    -- Grupo sanguíneo
    SET @GrupSangPct =
        CASE @GrupSangNombre
            WHEN 'A+' THEN 1
            WHEN 'A−' THEN 2
            WHEN 'B+' THEN 3
            WHEN 'B−' THEN 4
            WHEN 'AB+' THEN 5
            WHEN 'AB−' THEN 6
            WHEN 'O+' THEN 7
            WHEN 'O−' THEN 8
            ELSE NULL
        END

    IF @GrupSangPct IS NULL
    BEGIN
        RAISERROR('Grupo sanguíneo no válido.', 16, 1)
        RETURN @@ERROR
    END

    -- Verificar existencia
    IF NOT EXISTS (SELECT 1 FROM Paciente WHERE DniPct = @DniPct)
    BEGIN
        RAISERROR('Este paciente no existe!!', 16, 1)
        RETURN @@ERROR
    END

    -- Actualizar paciente
    UPDATE dbo.Paciente
    SET
        NomPct         = @NomPct,
        ApellPct       = @ApellPct,
        TlfPct         = @TlfPct,
        GeneroPct      = @GeneroBit,
        EmailPct       = @EmailPct,
        FechNaciPct    = @FechNaciPct,
        DirecPct       = @DirecPct,
        OcupPct        = @OcupPct,
        GrupSangPct    = @GrupSangPct,
        ProcedenciaPct = @ProcedenciaPct,
        EstCivilPct    = @EstCivilPct,
        GrupEtnicoPct  = @GrupEtnicoPct,
        CentrTrabPct   = @CentrTrabPct,
        GradInstrPct   = @GradInstrPct,
        HijosPct       = @HijosPct
    WHERE DniPct = @DniPct
END
GO






--Eliminar

CREATE OR ALTER PROCEDURE PA_CRUD_EliminarPaciente
(
    @DniPct int
)
AS
BEGIN
    -- Verificar si el paciente existe
    IF NOT EXISTS (SELECT 1 FROM Paciente WHERE DniPct = @DniPct)
    BEGIN
        RAISERROR('Este paciente no existe!!', 16, 1)
        RETURN @@ERROR
    END

    -- Eliminar al paciente
    DELETE FROM Paciente
    WHERE DniPct = @DniPct
END
GO


--Listar



--Vista para crud listar
CREATE OR ALTER VIEW Vw_ListarPaciente
AS
SELECT 
    DniPct                AS Dni,
    NomPct                AS Nombre,
    ApellPct              AS Apellido,
    TlfPct                AS Telefono,
    CASE GeneroPct
        WHEN 1 THEN 'M'
        WHEN 0 THEN 'F'
    END                   AS Genero,
    EmailPct              AS Email,
    FechNaciPct           AS [Fecha de Nacimiento],
    DirecPct              AS Direccion,
    OcupPct               AS Ocupacion,
    -- Grupo sanguíneo
    CASE GrupSangPct
        WHEN 1 THEN 'A+'
        WHEN 2 THEN 'A−'
        WHEN 3 THEN 'B+'
        WHEN 4 THEN 'B−'
        WHEN 5 THEN 'AB+'
        WHEN 6 THEN 'AB−'
        WHEN 7 THEN 'O+'
        WHEN 8 THEN 'O−'
        ELSE 'Desconocido'
    END                   AS [Grupo Sanguineo],
    ProcedenciaPct        AS Procedencia,
    -- Estado civil
    CASE EstCivilPct
        WHEN 1 THEN 'Casado'
        WHEN 2 THEN 'Soltero'
        WHEN 3 THEN 'Viudo'
        WHEN 4 THEN 'Divorciado'
        ELSE 'Desconocido'
    END                   AS [Estado Civil],
    GrupEtnicoPct         AS [Grupo_Etnico],
    CentrTrabPct          AS [Centro_Trabajo],
    GradInstrPct          AS [Grado_Instruccion],
    HijosPct              AS Hijos
FROM Paciente;
GO



--listado completo
CREATE OR ALTER PROCEDURE PA_CRUD_ListarPaciente
AS
BEGIN
    SELECT * FROM Vw_ListarPaciente;
END
GO



--listado con filtro
CREATE OR ALTER PROCEDURE PA_CRUD_ListarPacienteConFiltro 
    @FiltroTexto NVARCHAR(100) = NULL
AS
BEGIN
    SET NOCOUNT ON;
    
    SELECT 
        p.DniPct AS [DNI],
        p.NomPct AS [Nombre],
        p.TlfPct AS [Teléfono],
        p.GeneroPct AS [Género],
        -- Conteo de citas asistidas
        (SELECT COUNT(*) 
         FROM dbo.Cita c 
         WHERE c.DniPct = p.DniPct 
         AND c.IdEstadoCita = (SELECT IdEstadoCita FROM dbo.EstadoCita WHERE EstadoCita = 'Atendida')
        ) AS [Citas Asistidas],
        -- Conteo de citas perdidas
        (SELECT COUNT(*) 
         FROM dbo.Cita c 
         WHERE c.DniPct = p.DniPct 
         AND c.IdEstadoCita = (SELECT IdEstadoCita FROM dbo.EstadoCita WHERE EstadoCita = 'Perdida')
        ) AS [Citas Perdidas],
        p.EmailPct AS [Email],
        p.FechNaciPct AS [Fecha de Nacimiento],
        p.DirecPct AS [Dirección],
        p.OcupPct AS [Ocupación],
        p.GrupSangPct AS [Grupo Sanguíneo],
        p.ProcedenciaPct AS [Procedencia],
        p.EstCivilPct AS [Estado Civil],
        p.GrupEtnicoPct AS [Grupo Étnico],
        p.CentrTrabPct AS [Centro de Trabajo],
        p.GradInstrPct AS [Grado de Instrucción],
        p.HijosPct AS [Hijos]
    FROM dbo.Paciente p
    WHERE 
        (@FiltroTexto IS NULL OR @FiltroTexto = '') OR
        (
            p.DniPct LIKE '%' + @FiltroTexto + '%' OR
            p.NomPct LIKE '%' + @FiltroTexto + '%' OR
            p.ApellPct LIKE '%' + @FiltroTexto + '%' OR
            p.TlfPct LIKE '%' + @FiltroTexto + '%' OR
            p.GeneroPct LIKE '%' + @FiltroTexto + '%' OR
            p.EmailPct LIKE '%' + @FiltroTexto + '%' OR
            p.DirecPct LIKE '%' + @FiltroTexto + '%' OR
            p.OcupPct LIKE '%' + @FiltroTexto + '%' OR
            p.GrupSangPct LIKE '%' + @FiltroTexto + '%' OR
            p.ProcedenciaPct LIKE '%' + @FiltroTexto + '%' OR
            p.EstCivilPct LIKE '%' + @FiltroTexto + '%' OR
            p.GrupEtnicoPct LIKE '%' + @FiltroTexto + '%' OR
            p.CentrTrabPct LIKE '%' + @FiltroTexto + '%' OR
            p.GradInstrPct LIKE '%' + @FiltroTexto + '%' OR
            CAST(p.HijosPct AS NVARCHAR) LIKE '%' + @FiltroTexto + '%'
        )
    ORDER BY 
        p.NomPct, 
        p.ApellPct;
END
go



--------------------------
-----------------------------------
--Tabla CITA                
-----------------------------------

--Insertar
CREATE OR ALTER PROCEDURE PA_CRUD_InsertarCita
(
    @DniRecep        int,
    @CodCita         VARCHAR(50),
    @CodHorario      int,
    @DniPct          int,
    @DniDoc          int,
    @HoraInicio      TIME(7),
    @HoraFin         TIME(7),
    @FechaCita       DATE,
    @IdEstadoCita    INT,
    @IdTipoAtencion  INT,
    @FechaReprogra   DATE = NULL,
    @FechaAnulacion  DATE = NULL
)
AS
BEGIN 
    -- Verifica si el paciente ya tiene una cita ACTIVA para esa fecha
    IF EXISTS (
        SELECT 1
        FROM Cita
        WHERE DniPct = @DniPct AND FechaCita = @FechaCita AND IdEstadoCita IN (1) -- Por ejemplo, 1 = activa
    )
    BEGIN
        RAISERROR('El paciente ya tiene una cita activa en esa fecha.', 16, 1);
        RETURN @@ERROR;
    END

    -- Insertar nueva cita
    INSERT INTO dbo.Cita (
        DniRecep, CodCita, CodHorario, DniPct, DniDoc, HoraInicio, HoraFin,
        FechaCita, IdEstadoCita, IdTipoAtencion, FechaReprogra, FechaAnulacion
    )
    VALUES (
        @DniRecep, @CodCita, @CodHorario, @DniPct, @DniDoc, @HoraInicio, @HoraFin,
        @FechaCita, @IdEstadoCita, @IdTipoAtencion, @FechaReprogra, @FechaAnulacion
    );

    RETURN 0;
END
GO


--Modificar

CREATE OR ALTER PROCEDURE PA_CRUD_ModificarCita 
(
    @DniRecep INT,
    @CodCita VARCHAR(50),
    @CodHorario INT,
    @DniPct INT,
    @DniDoc INT,
    @HoraInicio TIME(7),
    @HoraFin TIME(7),
    @FechaCita DATE,
    @IdEstadoCita INT,
    @IdTipoAtencion INT,
    @FechaReprogra DATE,
    @FechaAnulacion DATE
)
AS
BEGIN
    -- Validación: Verificar si la cita existe
    IF NOT EXISTS(SELECT 1 FROM Cita WHERE CodCita = @CodCita)
    BEGIN
        RAISERROR('Esta cita no existe.', 16, 1);
        RETURN @@ERROR;
    END

    -- Actualización
    UPDATE dbo.Cita
    SET 
        DniRecep = @DniRecep,
        CodHorario = @CodHorario,
        DniPct = @DniPct,
        DniDoc = @DniDoc,
        HoraInicio = @HoraInicio,
        HoraFin = @HoraFin,
        FechaCita = @FechaCita,
        IdEstadoCita = @IdEstadoCita,
        IdTipoAtencion = @IdTipoAtencion,
        FechaReprogra = @FechaReprogra,
        FechaAnulacion = @FechaAnulacion
    WHERE CodCita = @CodCita
END
GO


--Eliminar

CREATE or alter PROCEDURE PA_CRUD_EliminarCita
(@DniRecep int,@CodCita varchar(50),@CodHorario int,@DniPct int,
    @DniDoc int,@HoraInicio time(7),@HoraFin time(7),@FechaCita date,@EstadoCita int,
    @TipoAtencion int,@FechaReprogra date,@FechaAnulacion date)
 AS
 BEGIN

	IF NOT EXISTS(SELECT * FROM Cita WHERE DniPct=@DniPct)
	BEGIN
	RAISERROR('Esta cita no existe!!',16,1)
	RETURN @@ERROR
	END

delete from Cita
	   where DniPct=@DniPct
 END
GO




--Listar

-- Listar todas las citas
CREATE OR ALTER PROCEDURE PA_CRUD_ListarCitas
AS
BEGIN
    SELECT * FROM Vw_ListarCitas
END
GO

-- Listar con filtro 
CREATE OR ALTER PROCEDURE PA_CRUD_ListarCitasConFiltro
(
    @Filtro VARCHAR(100)
)
AS
BEGIN
    SELECT * 
    FROM Vw_ListarCitas
    WHERE 
        IdCita LIKE '%' + @Filtro + '%' OR
        DniPaciente LIKE '%' + @Filtro + '%' OR
        NombrePaciente LIKE '%' + @Filtro + '%' OR
        Consultorio LIKE '%' + @Filtro + '%' OR
        NombreDoctor LIKE '%' + @Filtro + '%' OR
        CONVERT(VARCHAR, HoraInicio, 108) LIKE '%' + @Filtro + '%' OR
        CONVERT(VARCHAR, HoraFin, 108) LIKE '%' + @Filtro + '%' OR
        Atencion LIKE '%' + @Filtro + '%' OR
        Estado LIKE '%' + @Filtro + '%' OR
        NombreRecepcionista LIKE '%' + @Filtro + '%';
END
GO



--Vista para crud listar
CREATE OR ALTER VIEW Vw_ListarCitas
AS
SELECT
    c.CodCita                    AS IdCita,
    p.DniPct                    AS DniPaciente,
    (p.NomPct + ' ' + p.ApellPct) AS NombrePaciente,
    con.NomConst             AS Consultorio,
    (d.NomDoc + ' ' + d.ApellDoc) AS NombreDoctor,
    c.HoraInicio,
    c.HoraFin,
    ta.TipoAtencion       AS Atencion,
    ec.EstadoCita         AS Estado,
    (r.NomRecep + ' ' + r.ApellRecep) AS NombreRecepcionista
FROM
    Cita c
    INNER JOIN Paciente p        ON c.DniPct = p.DniPct
    INNER JOIN Doctor d          ON c.DniDoc = d.DniDoc
    INNER JOIN Recepcionista r   ON c.DniRecep = r.DniRecep
    INNER JOIN Horario h         ON c.CodHorario = h.CodHorario
    INNER JOIN Consultorio con   ON d.CodConst = con.CodConst
    INNER JOIN EstadoCita ec     ON c.IdEstadoCita = ec.IdEstadoCita
    INNER JOIN TipoAtencion ta   ON c.IdTipoAtencion = ta.IdTipoAtencion;
GO



-----------------------------------
--CRUD Especialidad_Doctor        
-----------------------------------

--INSERTAR

CREATE or alter  PROCEDURE PA_CRUD_InsertarEspecialidadDoctor
    @CodEspecia int, 
    @DniDoc int
AS
BEGIN
    IF EXISTS (
        SELECT * 
        FROM Especialidad_Doctor 
        WHERE CodEspecia = @CodEspecia AND DniDoc = @DniDoc
    )
    BEGIN
        RAISERROR('Esta relación entre doctor y especialidad ya existe.', 16, 1)
        RETURN @@ERROR
    END

    IF NOT EXISTS (SELECT * FROM Especialidad WHERE CodEspecia = @CodEspecia)
    BEGIN
        RAISERROR('La especialidad no existe.', 16, 1)
        RETURN @@ERROR
    END

    IF NOT EXISTS (SELECT * FROM Doctor WHERE DniDoc = @DniDoc)
    BEGIN
        RAISERROR('El doctor no existe.', 16, 1)
        RETURN @@ERROR
    END

    INSERT INTO Especialidad_Doctor (CodEspecia, DniDoc)
    VALUES (@CodEspecia, @DniDoc)
END
GO

-- ELIMINAR
CREATE  or alter PROCEDURE PA_CRUD_EliminarDoctorEspecialidad
    @CodEspecia int,
    @DniDoc int
AS
BEGIN
    IF NOT EXISTS (
        SELECT * 
        FROM Especialidad_Doctor 
        WHERE CodEspecia = @CodEspecia AND DniDoc = @DniDoc
    )
    BEGIN
        RAISERROR('Esta relación no existe.', 16, 1)
        RETURN @@ERROR
    END

    DELETE FROM Especialidad_Doctor
    WHERE CodEspecia = @CodEspecia AND DniDoc = @DniDoc
END
GO



--LISTAR
--SIN FILTRO

CREATE OR ALTER PROCEDURE PA_CRUD_ListarDoctorEspecialidad
AS
BEGIN
    SELECT * 
    FROM Vw_ListarDoctorEspecialidad;
END
GO
--CON FILTRO
CREATE OR ALTER PROCEDURE PA_CRUD_ListarDoctorEspecialidadConFiltro
    @Filtro VARCHAR(150)
AS
BEGIN
    SELECT * 
    FROM Vw_ListarDoctorEspecialidad
    WHERE 
        [ID especialidad] LIKE '%' + @Filtro + '%' OR
        [Especialidad] LIKE '%' + @Filtro + '%' OR
        [DNI del Doctor] LIKE '%' + @Filtro + '%' OR
        [Nombre del Doctor] LIKE '%' + @Filtro + '%';
END
GO




--CREAR LA VISTA
CREATE OR ALTER VIEW Vw_ListarDoctorEspecialidad
AS
SELECT 
    ed.CodEspecia             AS [ID especialidad],
    e.Especialidad            AS [Especialidad],
    d.DniDoc                  AS [DNI del Doctor],
    d.NomDoc + ' ' + d.ApellDoc AS [Nombre del Doctor]
FROM 
    Especialidad_Doctor ed
    INNER JOIN Especialidad e ON ed.CodEspecia = e.CodEspecia
    INNER JOIN Doctor d       ON ed.DniDoc = d.DniDoc;
GO

-----------------------------------------------------------------------------------------
-----------------------------------------------------------------------------------------

/*ESPECIALIDAD*/
------------------------------------------------------------------------------------------
--INSERTAR

CREATE or alter PROCEDURE PA_CRUD_InsertarEspecialidad
(@CodEspecia AS INT, 
@Especialidad AS Varchar(120))
AS
BEGIN

IF EXISTS(SELECT * FROM Especialidad WHERE CodEspecia=@CodEspecia) BEGIN
	RAISERROR('Codigo YA Existe en la Tabla Especialidad',16,1)
	RETURN @@ERROR
END

INSERT INTO dbo.Especialidad(CodEspecia,Especialidad)
VALUES (@CodEspecia,@Especialidad)
END
GO

--MODIFICAR
CREATE OR ALTER PROCEDURE PA_CRUD_ModificarEspecialidad
(@CodEspecia AS INT, 
@Especialidad AS Varchar(120))
AS
BEGIN

IF NOT EXISTS(SELECT * FROM Especialidad WHERE CodEspecia=@CodEspecia) BEGIN
	RAISERROR('Codigo NO Existe en la Tabla Especialidad',16,1)
	RETURN @@ERROR
END

UPDATE dbo.Especialidad
   SET Especialidad = @Especialidad
WHERE CodEspecia=@CodEspecia

END
GO

--ELIMINAR
CREATE OR ALTER PROCEDURE PA_CRUD_EliminarEspecialidad
(@CodEspecia AS int)
AS
BEGIN

IF NOT EXISTS(SELECT * FROM Especialidad WHERE CodEspecia=@CodEspecia) BEGIN
	RAISERROR('Codigo NO Existe en la Tabla Especialidad',16,1)
	RETURN @@ERROR
END

IF EXISTS(SELECT * FROM Especialidad_Doctor WHERE CodEspecia=@CodEspecia) BEGIN
	RAISERROR('No se puede Eliminar por que ya esta relacionado con los Doctores',16,1)
	RETURN @@ERROR
END

DELETE FROM Especialidad
WHERE CodEspecia=@CodEspecia
END
GO

--LISTAR SIN FILTRO
CREATE OR ALTER PROCEDURE PA_CRUD_ListarEspecialidad
AS
BEGIN
SELECT * FROM Vw_ListarEspecialidad
END
GO

--LISTAR CON FILTRO
CREATE or alter PROCEDURE PA_CRUD_ListarEspecialidadConFiltro
(@Filtro Varchar(150))
AS
BEGIN
SELECT * FROM Vw_ListarEspecialidad
WHERE [Codigo]+[Nombre Especialidad] LIKE '%'+@Filtro+'%'
END
GO
--CREAR VISTA

CREATE or alter VIEW Vw_ListarEspecialidad
AS
	SELECT CodEspecia AS 'Codigo', Especialidad AS 'Nombre Especialidad'
	FROM Especialidad
GO

-------------------------------------------------------------------------------------
/*DOCTOR */
------------------------------------------------------------------------------------
--INSERT
CREATE or alter PROCEDURE PA_insert_Doctor
(
    @DniDoc            int,
    @NomDoc         VARCHAR(50),
    @ApellDoc       VARCHAR(50),
    @CodConst int,
    @CorreoDoctor         VARCHAR(120),
    @TelfDoctor        numeric (9)
)
AS
BEGIN
    -- Verificar existencia del consultorio
    IF NOT EXISTS (SELECT 1 FROM Consultorio WHERE CodConst = @CodConst)
    BEGIN
        RAISERROR('No se encontró el consultorio.', 16, 1);
        RETURN @@ERROR;
    END

    -- Insertar doctor
    INSERT INTO Doctor (
        DniDoc, NomDoc, ApellDoc, CodConst, CorreoDoctor, TelfDoctor
    )
    VALUES (
        @DniDoc, @NomDoc, @ApellDoc, @CodConst, @CorreoDoctor, @TelfDoctor
    );

    RETURN 0;
END
GO



--ELIMINAR

CREATE OR ALTER PROCEDURE PA_delete_Doctor
(
    @dni int
)
AS
BEGIN
    -- Validación: si el doctor está referenciado en Doctor_Horario, no permitir eliminar
    IF EXISTS (SELECT 1 FROM Doctor_Horario WHERE DniDoc = @dni)
    BEGIN
        RAISERROR('El doctor está siendo usado en Doctor_Horario', 16, 1);
        RETURN @@ERROR;
    END

    -- Validación opcional: si no existe el doctor
    IF NOT EXISTS (SELECT 1 FROM Doctor WHERE DniDoc = @dni)
    BEGIN
        RAISERROR('El doctor no existe', 16, 1);
        RETURN @@ERROR;
    END

    -- Eliminación
    DELETE FROM Doctor
    WHERE DniDoc = @dni;

    RETURN 0;
END
GO


-- Vista
CREATE OR ALTER VIEW Vw_ListarDoctores
AS
SELECT
    d.DniDoc          AS DNI,
    d.NomDoc          AS Nombre,
    d.ApellDoc        AS Apellido,
    e.Especialidad    AS Especialidad,
    d.CodConst        AS CodConsultorio,
    d.CorreoDoctor    AS Correo,
    d.TelfDoctor      AS Telefono
FROM Doctor d
INNER JOIN Especialidad_Doctor ed ON d.DniDoc = ed.DniDoc
INNER JOIN Especialidad e         ON ed.CodEspecia = e.CodEspecia;
GO

-- Listar sin filtro
CREATE OR ALTER PROCEDURE PA_ListarDoctores
AS
BEGIN
    SELECT * FROM Vw_ListarDoctores;
END
GO

-- Listar con filtro
CREATE OR ALTER PROCEDURE PA_CRUD_ListarDoctorConFiltro 
    @FiltroTexto NVARCHAR(100) = NULL
AS
BEGIN
    SET NOCOUNT ON;
    
    SELECT 
        d.DniDoc AS [DNI],
        d.NomDoc AS [Nombre],
        e.Especialidad AS [Especialidad],
        -- Asumiendo que hay un campo de estado en Doctor, si no, puedes ajustarlo
        CASE 
            WHEN d.DniDoc IS NOT NULL THEN 'Activo'
            ELSE 'Inactivo'
        END AS [Estado],
        -- Conteo de citas pendientes
        (SELECT COUNT(*) 
         FROM dbo.Cita c 
         WHERE c.DniDoc = d.DniDoc 
         AND c.IdEstadoCita = (SELECT IdEstadoCita FROM dbo.EstadoCita WHERE EstadoCita = 'Pendiente')
        ) AS [Citas Pendientes],
        -- Conteo de citas atendidas
        (SELECT COUNT(*) 
         FROM dbo.Cita c 
         WHERE c.DniDoc = d.DniDoc 
         AND c.IdEstadoCita = (SELECT IdEstadoCita FROM dbo.EstadoCita WHERE EstadoCita = 'Atendida')
        ) AS [Citas Atendidas],
        c.NomConst AS [Consultorio],
        d.CorreoDoctor AS [Correo],
        d.TelfDoctor AS [Teléfono]
    FROM dbo.Doctor d
    LEFT JOIN dbo.Consultorio c ON d.CodConst = c.CodConst
    LEFT JOIN dbo.Especialidad e ON c.CodEspecia = e.CodEspecia
    WHERE 
        (@FiltroTexto IS NULL OR @FiltroTexto = '') OR
        (
            d.DniDoc LIKE '%' + @FiltroTexto + '%' OR
            d.NomDoc LIKE '%' + @FiltroTexto + '%' OR
            d.ApellDoc LIKE '%' + @FiltroTexto + '%' OR
            d.CorreoDoctor LIKE '%' + @FiltroTexto + '%' OR
            d.TelfDoctor LIKE '%' + @FiltroTexto + '%' OR
            e.Especialidad LIKE '%' + @FiltroTexto + '%' OR
            c.NomConst LIKE '%' + @FiltroTexto + '%'
        )
    ORDER BY 
        d.NomDoc, 
        d.ApellDoc;
END
go



--modificar
CREATE OR ALTER PROCEDURE PA_actualizacion_Doctor
(
    @dni INT,
    @nombre VARCHAR(50),
    @apellido VARCHAR(50),
    @codconsultorio INT,
    @correo VARCHAR(120),
    @celular NUMERIC(9),
    @codEspecialidad INT  -- nuevo parámetro
)
AS
BEGIN
    -- Validar existencia del doctor
    IF NOT EXISTS (SELECT 1 FROM Doctor WHERE DniDoc = @dni)
    BEGIN
        RAISERROR('El doctor no existe.', 16, 1);
        RETURN @@ERROR;
    END

    -- Validar existencia del consultorio
    IF NOT EXISTS (SELECT 1 FROM Consultorio WHERE CodConst = @codconsultorio)
    BEGIN
        RAISERROR('No se encontró el consultorio.', 16, 1);
        RETURN @@ERROR;
    END

    -- Validar existencia de especialidad
    IF NOT EXISTS (SELECT 1 FROM Especialidad WHERE CodEspecia = @codEspecialidad)
    BEGIN
        RAISERROR('No se encontró la especialidad.', 16, 1);
        RETURN @@ERROR;
    END

    -- Actualizar datos del doctor
    UPDATE Doctor
    SET 
        NomDoc = @nombre,
        ApellDoc = @apellido,
        CodConst = @codconsultorio,
        CorreoDoctor = @correo,
        TelfDoctor = @celular
    WHERE DniDoc = @dni;

    -- Actualizar o insertar la especialidad del doctor
    IF EXISTS (SELECT 1 FROM Especialidad_Doctor WHERE DniDoc = @dni)
    BEGIN
        UPDATE Especialidad_Doctor
        SET CodEspecia = @codEspecialidad
        WHERE DniDoc = @dni;
    END
    ELSE
    BEGIN
        INSERT INTO Especialidad_Doctor (DniDoc, CodEspecia)
        VALUES (@dni, @codEspecialidad);
    END

    RETURN 0;
END
GO



-----------------------------------------
--CRUD HORARIO_DOCTOR
-----------------------------------

--INSERTAR
CREATE OR ALTER PROCEDURE PA_CRUD_InsertarDoctorHorario
    @CodHorario INT, 
    @DniDoc INT
AS
BEGIN
    -- Validar si ya existe la relación
    IF EXISTS (
        SELECT 1
        FROM Doctor_Horario 
        WHERE CodHorario = @CodHorario AND DniDoc = @DniDoc
    )
    BEGIN
        RAISERROR('Esta relación entre doctor y horario ya existe.', 16, 1);
        RETURN @@ERROR;
    END

    -- Validar existencia del horario
    IF NOT EXISTS (
        SELECT 1
        FROM Horario 
        WHERE CodHorario = @CodHorario
    )
    BEGIN
        RAISERROR('El horario no existe.', 16, 1);
        RETURN @@ERROR;
    END

    -- Validar existencia del doctor
    IF NOT EXISTS (
        SELECT 1
        FROM Doctor 
        WHERE DniDoc = @DniDoc
    )
    BEGIN
        RAISERROR('El doctor no existe.', 16, 1);
        RETURN @@ERROR;
    END

    -- Insertar relación
    INSERT INTO Doctor_Horario (CodHorario, DniDoc)
    VALUES (@CodHorario, @DniDoc);
END
GO


-- ELIMINAR
CREATE or alter PROCEDURE PA_CRUD_EliminarDoctorEspecialidad
    @CodEspecia int,
    @DniDoc int
AS
BEGIN
    IF NOT EXISTS (
        SELECT * 
        FROM Especialidad_Doctor 
        WHERE CodEspecia = @CodEspecia AND DniDoc = @DniDoc
    )
    BEGIN
        RAISERROR('Esta relación no existe.', 16, 1)
        RETURN @@ERROR
    END

    DELETE FROM Especialidad_Doctor
    WHERE CodEspecia = @CodEspecia AND DniDoc = @DniDoc
END
GO



--LISTAR
--SIN FILTRO

CREATE OR ALTER PROCEDURE PA_CRUD_ListarDoctorEspecialidad
AS
BEGIN
    SELECT * 
    FROM Vw_ListarDoctorEspecialidad;
END
GO
--CON FILTRO
CREATE OR ALTER PROCEDURE PA_CRUD_ListarDoctorEspecialidadConFiltro
    @Filtro VARCHAR(150)
AS
BEGIN
    SELECT * 
    FROM Vw_ListarDoctorEspecialidad
    WHERE 
        [ID especialidad] LIKE '%' + @Filtro + '%' OR
        [Especialidad] LIKE '%' + @Filtro + '%' OR
        [DNI del Doctor] LIKE '%' + @Filtro + '%' OR
        [Nombre del Doctor] LIKE '%' + @Filtro + '%';
END
GO




--CREAR LA VISTA
CREATE OR ALTER VIEW Vw_ListarDoctorEspecialidad
AS
SELECT 
    ed.CodEspecia             AS [ID especialidad],
    e.Especialidad            AS [Especialidad],
    d.DniDoc                  AS [DNI del Doctor],
    d.NomDoc + ' ' + d.ApellDoc AS [Nombre del Doctor]
FROM 
    Especialidad_Doctor ed
    INNER JOIN Especialidad e ON ed.CodEspecia = e.CodEspecia
    INNER JOIN Doctor d       ON ed.DniDoc = d.DniDoc;
GO

--eliminar
CREATE or alter PROCEDURE PA_CRUD_EliminarDoctorHorario
    @DniDoc INT,
    @CodHorario INT
AS
BEGIN
    IF NOT EXISTS (
        SELECT 1 FROM Doctor_Horario
        WHERE DniDoc = @DniDoc AND CodHorario = @CodHorario
    )
    BEGIN
        RAISERROR('La relación doctor-horario no existe.', 16, 1)
        RETURN @@ERROR
    END

    DELETE FROM Doctor_Horario
    WHERE DniDoc = @DniDoc AND CodHorario = @CodHorario
END
GO

--vistas
CREATe OR ALTER VIEW Vw_ListarDoctorHorario
AS
SELECT 
    dh.DniDoc,
    d.NomDoc + ' ' + d.ApellDoc AS NombreDoctor,
    d.CorreoDoctor,
    d.TelfDoctor,
    h.CodHorario,
    h.Dia,
    h.HoraInicio,
    h.HoraFin,
    h.LimitPct,
    h.IdTurnoHorario,
    h.EstadoHorario
FROM 
    Doctor_Horario dh
    INNER JOIN Doctor d ON dh.DniDoc = d.DniDoc
    INNER JOIN Horario h ON dh.CodHorario = h.CodHorario
GO

--listar sin filtro

CREATE or alter PROCEDURE PA_CRUD_ListarDoctorHorario
AS
BEGIN
    SELECT * FROM Vw_ListarDoctorHorario
END
GO

--listar con filtro
CREATE or alter PROCEDURE PA_CRUD_ListarDoctorHorarioConFiltro
    @Filtro VARCHAR(100)
AS
BEGIN
    SELECT * 
    FROM Vw_ListarDoctorHorario
    WHERE 
        CAST(DniDoc AS VARCHAR) LIKE '%' + @Filtro + '%' OR
        NombreDoctor LIKE '%' + @Filtro + '%' OR
        CorreoDoctor LIKE '%' + @Filtro + '%' OR
        TelfDoctor LIKE '%' + @Filtro + '%' OR
        CAST(CodHorario AS VARCHAR) LIKE '%' + @Filtro + '%' OR
        Dia LIKE '%' + @Filtro + '%'
END
GO


------------------------------------------------------------------
CREATE OR ALTER PROCEDURE PA_CRUD_MostrarCitasPendientes
AS
BEGIN
    SET NOCOUNT ON;
    
    SELECT 
        c.CodCita AS ID,
        p.NomPct AS NombrePaciente,
        p.ApellPct AS ApellidoPaciente,
        c.FechaCita AS Fecha,
        h.HoraInicio,
        h.HoraFin,
        c.IdEstadoCita AS Estado
    FROM dbo.Cita c
    INNER JOIN dbo.Paciente p ON c.DniPct = p.DniPct
    INNER JOIN dbo.Horario h ON c.CodHorario = h.CodHorario
    WHERE c.IdEstadoCita = 1  -- Solo citas pendientes/activas
    ORDER BY c.FechaCita, h.HoraInicio;
    
END
GO