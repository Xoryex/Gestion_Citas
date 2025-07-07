create procedure paSelectRecepcionista
(
	@dni numeric(8),
	@contrasena varchar(20)
)
as 
begin
	if not exists(select DniRecep from Recepcionista where DniRecep = @dni)begin
		raiserror('Recepcionita no existe',16,1)
		return @@ERROR
	end
	
	if not exists (select DniRecep from Recepcionista where DniRecep = @dni and Contrasena=@contrasena)begin
		raiserror('Constrase�a incorrecta',16,1)
		return @@ERROR
	end
	select * from Recepcionista where DniRecep=@dni
end
go

create procedure paInsertRecepcionista
(
	@dni numeric(8),
	@nombre varchar(50),
	@apellido varchar(50),
	@celular numeric(9),
	@contrase�a varchar(20),
	@admin bit
)
as
begin
	if exists(select DniRecep from Recepcionista where DniRecep=@dni) begin
		raiserror('El usuario ya existe',16,1)
		return @@ERROR
	end

	insert into Recepcionista (DniRecep,NomRecep,ApellRecep,TelfRecep,Contrasena,EsAdmin) 
	values(@dni,@nombre,@apellido,@celular,@contrase�a,@admin)


end
go


