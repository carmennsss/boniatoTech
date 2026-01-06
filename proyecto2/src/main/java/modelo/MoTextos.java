package modelo;

/**
 * Clase que almacena todas las cadenas de texto utilizadas en la aplicaci�n.
 * Cambiando los valores de las variables est�ticas seg�n el idioma
 * seleccionado.
 */
public class MoTextos {

	/** Idioma actual de la aplicaci�n (0 = ingl�s, 1 = espa�ol). */
	private static int idiomaActual = 0;

	// --- T�TULOS Y LOGIN ---
	public static String app_title = "Serwo Manager";
	public static String login_title = "Login";
	public static String menu_title = "Main Menu";
	public static String lbl_user = "User:";
	public static String lbl_password = "Password:";
	public static String btn_login = "Log in";

	// --- MEN� PRINCIPAL ---
	public static String lbl_select_option = "Select an option";
	public static String btn_manage_data = "Manage Data";
	public static String btn_file_manager = "File Manager";
	public static String btn_mail_controller = "Mail Controller";
	public static String btn_administrate = "Administrate";
	public static String btn_logout = "Log Out";

	// --- GESTOR DE ARCHIVOS ---
	public static String file_manager_title = "File Manager";
	public static String file_repo_title = "File Repository";
	public static String file_repo_subtitle = "Manage your server files efficiently";
	public static String btn_upload = "Upload";
	public static String btn_download = "Download";
	public static String btn_delete = "Delete";
	public static String btn_new_folder = "New Folder";
	public static String btn_delete_folder = "Delete Folder";
	public static String btn_rename = "Rename";
	public static String lbl_actions = "Actions";
	public static String lbl_current_path = "Current Path:";
	public static String btn_back = "Back";
	public static String btn_main_menu = "Main Menu";

	// --- CRUD Y FORMULARIOS ---
	public static String btn_new = "New";
	public static String lbl_crud_instructions = "Double click on a record to update or delete it.";
	public static String form_title = "Form";
	public static String btn_save = "Save";
	public static String btn_cancel = "Cancel";

	// --- WHITELIST ---
	public static String whitelist_title = "Whitelist Management";
	public static String btn_add = "Add";
	public static String btn_unassign = "Unassign";
	public static String btn_back_whitelist = "Back";
	public static String whitelist_dialog_title = "Add User";
	public static String whitelist_lbl_title = "Add to Whitelist";
	public static String whitelist_lbl_email = "Email:";
	public static String whitelist_lbl_name = "Name:";
	public static String whitelist_col_email = "Email";
	public static String whitelist_col_name = "Name";
	public static String whitelist_col_date = "Registration Date";
	public static String btn_dialog_add = "Add";
	public static String btn_dialog_cancel = "Cancel";

	// --- CORREO ---
	public static String mail_title_compose = "Compose new Mail";
	public static String mail_title_check = "Check Mail";
	public static String mail_lbl_for = "For:";
	public static String mail_lbl_from = "From:";
	public static String mail_lbl_subject = "Subject:";
	public static String mail_lbl_message = "Message:";
	public static String btn_send = "Send";
	public static String btn_mark_unread = "Mark unread";
	public static String btn_export = "Export";
	public static String btn_attach = "Attach";
	public static String mail_title_inbox = "Inbox";
	public static String mail_btn_compose = "Compose";
	public static String mail_btn_refresh = "Refresh";
	public static String mail_col_subject = "Subject";
	public static String mail_col_from = "From";
	public static String mail_col_date = "Date";

	// --- ROLES Y ADMINISTRACI�N ---
	public static String roles_title_create = "Create Role";
	public static String roles_title_available = "Available Roles";
	public static String roles_btn_create = "Add Role";
	public static String roles_lbl_name = "Name:";
	public static String roles_lbl_desc = "Description:";
	public static String roles_lbl_role = "Role:";
	public static String roles_dialog_title = "Add Role";
	public static String roles_btn_assign = "Assign Role";
	public static String roles_title_assign = "Assign Roles";
	public static String admin_title = "Administration Panel";
	public static String btn_manage_users = "Manage Users";
	public static String btn_manage_roles = "Manage Roles";
	public static String btn_logs = "Logs";
	public static String btn_whitelist = "Whitelist";

	// --- REGISTRO Y ELIMINACI�N ---
	public static String reg_title_window = "User Register";
	public static String reg_title_main = "Register User";
	public static String reg_lbl_name = "Name:";
	public static String reg_lbl_email = "Email Address:";
	public static String reg_lbl_key = "Address Key:";
	public static String reg_lbl_pass = "Password:";
	public static String reg_lbl_conf_pass = "Confirm Password:";
	public static String reg_btn_register = "Register";
	public static String del_user_title = "Delete User";
	public static String del_user_col_user = "User";
	public static String del_user_col_email = "Email";
	public static String del_btn_delete = "Delete";
	public static String del_btn_back = "Back";
	public static String msg_invalid_address_key = "Invalid format for Address Key, it must be: xxxx xxxx xxxx xxxx (all in lowercase)";

	// --- LOGS ---
	public static String logs_title = "Logs";
	public static String logs_col_action = "Operation";
	public static String logs_col_user = "User";
	public static String logs_col_date = "Date";
	public static String logs_col_result = "Result";
	public static String logs_btn_export = "Export CSV";
	public static String logs_dialog_save_title = "Save logs";

	// --- MENSAJES DE SISTEMA ---
	public static String msg_error_title = "Error";
	public static String msg_success_title = "Success";
	public static String msg_info_title = "Info";
	public static String msg_confirm_title = "Confirm";
	public static String msg_connection_error = "Could not connect to the server";
	public static String msg_fill_all_fields = "All fields must be filled";
	public static String msg_unexpected_error_prefix = "An unexpected error occurred: ";
	public static String msg_not_admin = "You are not an administrator";
	public static String msg_incorrect_creds = "Incorrect credentials";
	public static String msg_pass_mismatch = "Passwords do not match";
	public static String msg_invalid_email = "Invalid email format";
	public static String msg_user_registered = "User registered successfully";
	public static String msg_user_exists = "A user with this name or email already exists";
	public static String msg_err_pop3 = "[ERROR POP3] - Could not connect to inbox";
	public static String msg_err_mail_init = "Error: Mail view not initialized correctly";
	public static String dialog_select_action = "Select action";
	public static String btn_create_new = "Create new";
	public static String btn_sys_update = "Update";
	public static String btn_sys_delete = "Delete";
	public static String btn_sys_cancel = "Cancel";

	// --- CRUD MESSAGES ---
	public static String msg_saved_ok = "Saved successfully";
	public static String msg_save_error = "Error while saving";
	public static String msg_updated_ok = "Updated successfully";
	public static String msg_update_error = "Error while updating";
	public static String msg_confirm_delete = "Are you sure you want to delete this record?";
	public static String msg_deleted_ok = "Deleted successfully";
	public static String msg_delete_error = "Error while deleting";
	public static String gender_male = "Male";
	public static String gender_female = "Female";

	// --- ROLES MESSAGES ---
	public static String msg_role_name_empty = "The role name cannot be empty";
	public static String msg_role_desc_empty = "The role description cannot be empty";
	public static String msg_role_assigned = "assigned";
	public static String msg_role_unassigned = "unassigned";
	public static String msg_role_already_status_prefix = "The user ";
	public static String msg_role_already_status_middle = " already has the role ";
	public static String msg_role_already_status_suffix = " ";
	public static String col_user = "User";
	public static String col_email = "Email";
	public static String col_roles = "Roles";
	public static String msg_no_roles = "No roles assigned";
	public static String col_id = "ID";
	public static String col_role = "Role";
	public static String col_desc = "Description";

	// --- WHITELIST MESSAGES ---
	public static String msg_no_users_selected = "No users selected to remove.";
	public static String title_warning = "Warning";
	public static String msg_confirm_remove_prefix = "Are you sure you want to remove ";
	public static String msg_confirm_remove_suffix = " user(s) from Whitelist?";
	public static String title_confirm_removal = "Confirm Removal";
	public static String msg_fill_email_name = "Please fill in the Email and Name fields";
	public static String msg_user_exists_whitelist = "User already exists in Whitelist";
	public static String msg_user_added_whitelist = "User added to Whitelist successfully";
	public static String msg_err_adding_user = "Error adding user";

	// --- FILE MESSAGES ---
	public static String msg_upload_success = "File uploaded successfully";
	public static String msg_upload_error = "Error uploading file";
	public static String msg_download_success = "File downloaded successfully";
	public static String msg_download_error = "Error downloading file";
	public static String msg_folder_created = "Folder created successfully";
	public static String msg_permission_denied_title = "Permission Denied";
	public static String msg_permission_denied_upload = "You do not have permission to upload files";
	public static String msg_select_file = "Please select a file";
	public static String msg_permission_denied_download = "You do not have permission to download this file";
	public static String msg_permission_denied_delete = "You do not have permission to delete this file";
	public static String msg_permission_denied_create_folder = "You do not have permission to create folders";
	public static String msg_enter_folder_name = "Enter a name for the folder";
	public static String msg_select_folder = "Please select a folder";
	public static String msg_permission_denied_delete_folder = "You do not have permission to delete this folder";
	public static String msg_select_folder_not_file = "You must select a folder, not a file";
	public static String msg_permission_denied_rename = "You do not have permission to rename this file";

	// --- MAIL MESSAGES ---
	public static String mail_msg_recipient_obligatory = "The recipient and the message body are obligatory";
	public static String mail_msg_sent_prefix = "Email successfully sent to ";
	public static String mail_msg_sent_error_recipient = "The message could not be sent; please check the recipient's email address.";
	public static String mail_msg_attached_prefix = "File attached: ";

	// --- DELETE USER MESSAGES ---
	public static String del_msg_select = "Select a user to delete";
	public static String del_msg_confirm = "Are you sure you want to delete this user?";
	public static String del_msg_success = "User deleted successfully";
	public static String del_msg_error = "Error deleting user";

	// --- LOGS FILTERS & CSV ---
	public static String logs_filter_actions = " - Actions";
	public static String logs_filter_users = " - Users";
	public static String logs_filter_dates = " - Dates";
	public static String logs_filter_results = " - Results";
	public static String logs_csv_no_data = "There aren't logs registered in the database.";
	public static String logs_csv_header = "Date,User,Action,Result";
	public static String btn_log_actions = "By Actions";
	public static String btn_log_users = "By Users";
	public static String btn_log_dates = "By Dates";
	public static String btn_log_results = "By Results";

	// --- FILEMANAGER STRINGS ---
	public static String msg_confirm_delete_file = "Do you want to delete the selected file?";
	public static String msg_could_not_delete = "Could not be deleted...";
	public static String msg_confirm_delete_folder = "Do you want to delete the selected folder?";
	public static String msg_folder_not_empty = "The folder may not be empty.";
	public static String msg_could_not_create = "Could not be created...";

	// --- OYENTEARCHIVOS STRINGS ---
	public static String title_select_upload = "Select the file to upload";
	public static String title_select_download = "Select where to download the file";
	public static String msg_enter_new_name = "Enter new name for the file:";
	public static String msg_invalid_name = "Invalid name.";
	public static String msg_rename_canceled = "Renaming canceled or invalid name.";

	// --- VISTACRUD STRINGS ---
	public static String title_crud_animals = "Serwo Management - Animals";
	public static String menu_species = "Species";
	public static String menu_enclosures = "Enclosures";
	public static String menu_caretakers = "Caretakers";
	public static String menu_animals = "Animals";
	public static String menu_transfers = "Transfers";
	public static String menu_species_enclosures = "Species-Enclosures";
	public static String menu_elements = "Elements";

	// --- CONTROLADORWHITELIST STRINGS ---
	public static String msg_removed_prefix = "Removed ";
	public static String msg_removed_suffix = " users.";

	// --- GETTERS Y SETTERS ---

	/**
	 * Obtiene el idioma actual de la aplicaci�n.
	 *
	 * @return C�digo del idioma (0 = ingl�s, 1 = espa�ol).
	 */
	public static int getIdioma() {
		return idiomaActual;
	}

	/**
	 * Establece el idioma actual de la aplicaci�n y actualiza todas las cadenas de
	 * texto.
	 *
	 * @param idioma C�digo del idioma (e.g., 0 para ingl�s, 1 para espa�ol).
	 */
	public static void setIdioma(int idioma) {
		idiomaActual = idioma;
		if (idioma == 1) {
			// --- ESPA�OL ---
			app_title = "Gestor de Serwo";
			login_title = "Iniciar Sesi�n";
			menu_title = "Men� Principal";

			lbl_user = "Usuario:";
			lbl_password = "Clave:";
			btn_login = "Entrar";

			lbl_select_option = "Seleccione una opci�n";
			btn_manage_data = "Gesti�n de Datos";
			btn_file_manager = "Gestor de Archivos";
			btn_mail_controller = "Gestor de Correo";
			btn_administrate = "Administrar";
			btn_logout = "Cerrar Sesi�n";

			file_manager_title = "Gestor de Archivos";
			file_repo_title = "Repositorio de Archivos";
			file_repo_subtitle = "Gestiona los archivos del servidor eficientemente";
			btn_upload = "Subir";
			btn_download = "Descargar";
			btn_delete = "Borrar";
			btn_new_folder = "Nueva Carpeta";
			btn_delete_folder = "Borrar Carpeta";
			btn_rename = "Renombrar";
			lbl_actions = "Acciones";
			lbl_current_path = "Ruta Actual:";
			btn_back = "Volver";
			btn_main_menu = "Men� Principal";

			btn_new = "Nuevo";
			lbl_crud_instructions = "Doble clic en un registro para actualizar o borrar.";

			form_title = "Formulario";
			btn_save = "Guardar";
			btn_cancel = "Cancelar";

			whitelist_title = "Gesti�n de Whitelist";
			btn_add = "A�adir";
			btn_unassign = "Quitar";
			btn_back_whitelist = "Volver";
			whitelist_dialog_title = "Agregar Usuario";
			whitelist_lbl_title = "Agregar a Whitelist";
			whitelist_lbl_email = "Email:";
			whitelist_lbl_name = "Nombre:";
			whitelist_col_email = "Email";
			whitelist_col_name = "Nombre";
			whitelist_col_date = "Fecha Registro";
			btn_dialog_add = "Agregar";
			btn_dialog_cancel = "Cancelar";

			del_user_title = "Eliminar Usuario";
			del_user_col_user = "Usuario";
			del_user_col_email = "Correo";
			del_btn_delete = "Eliminar";
			del_btn_back = "Volver";
			del_msg_select = "Seleccione un usuario para eliminar";
			del_msg_confirm = "�Est� seguro de que desea eliminar este usuario?";
			del_msg_success = "Usuario eliminado correctamente";
			del_msg_error = "Error al eliminar usuario";

			mail_title_compose = "Redactar Nuevo Correo";
			mail_title_check = "Leer Correo";
			mail_lbl_for = "Para:";
			mail_lbl_from = "De:";
			mail_lbl_subject = "Asunto:";
			mail_lbl_message = "Mensaje:";
			btn_send = "Enviar";
			btn_mark_unread = "Marcar no le�do";
			btn_export = "Exportar";
			btn_attach = "Adjuntar";

			msg_role_name_empty = "El nombre del rol no puede estar vacío";
			msg_role_desc_empty = "La descripción del rol no puede estar vacía";

			roles_title_create = "Crear Rol";
			roles_title_available = "Roles disponibles";
			roles_btn_create = "Agregar Rol";
			roles_lbl_name = "Nombre:";
			roles_lbl_desc = "Descripción:";
			roles_lbl_role = "Rol:";
			roles_dialog_title = "Agregar Rol";
			roles_btn_assign = "Asignar Rol";
			roles_title_assign = "Asignar Roles";

			admin_title = "Panel de Administraci�n";
			btn_manage_users = "Gesti�n de Usuarios";
			btn_manage_roles = "Gesti�n de Roles";
			btn_logs = "Logs";
			btn_whitelist = "Whitelist";

			reg_title_window = "Registro de Usuario";
			reg_title_main = "Registrar Usuario";
			reg_lbl_name = "Nombre:";
			reg_lbl_email = "Email:";
			reg_lbl_key = "Clave del Correo:";
			reg_lbl_pass = "Contrase�a:";
			reg_lbl_conf_pass = "Confirmar Contrase�a:";
			reg_btn_register = "Registrar";
			msg_invalid_address_key = "Formato inválido para la Clave del Correo, debe ser: xxxx xxxx xxxx xxxx (todo en minúsculas)";

			mail_title_inbox = "Bandeja de Entrada";
			mail_btn_compose = "Redactar";
			mail_btn_refresh = "Actualizar";
			mail_col_subject = "Asunto";
			mail_col_from = "De";
			mail_col_date = "Fecha";

			msg_err_pop3 = "[ERROR POP3] - No se pudo conectar a la bandeja de entrada";

			msg_error_title = "Error";
			msg_success_title = "�xito";
			msg_info_title = "Informaci�n";
			msg_connection_error = "No se pudo conectar al servidor";
			msg_fill_all_fields = "Todos los campos deben ser rellenados";
			msg_unexpected_error_prefix = "Ocurri� un error inesperado: ";
			msg_confirm_title = "Confirmar";
			dialog_select_action = "Seleccionar Acci�n";
			btn_create_new = "Crear Nuevo";
			btn_sys_update = "Actualizar";
			btn_sys_delete = "Eliminar";
			btn_sys_cancel = "Cancelar";

			msg_not_admin = "No eres el administrador";
			msg_incorrect_creds = "Credenciales incorrectas";
			msg_err_mail_init = "Error: La vista de correos no se ha inicializado correctamente";

			msg_pass_mismatch = "Las contrase�as no coinciden";
			msg_invalid_email = "Formato de correo inv�lido";
			msg_user_registered = "Usuario registrado correctamente";
			msg_user_exists = "Ya existe un usuario con este nombre o correo";

			msg_no_users_selected = "No hay usuarios seleccionados para eliminar.";
			title_warning = "Advertencia";
			msg_confirm_remove_prefix = "¿Está seguro de que desea eliminar ";
			msg_confirm_remove_suffix = " usuario(s) de la Whitelist?";
			title_confirm_removal = "Confirmar Eliminación";
			msg_fill_email_name = "Por favor, llena los campos de Email y Nombre";
			msg_user_exists_whitelist = "El usuario ya existe en la Whitelist";
			msg_user_added_whitelist = "Usuario añadido correctamente a la Whitelist";
			msg_err_adding_user = "Error al añadir usuario";

			msg_upload_success = "Archivo subido correctamente";
			msg_upload_error = "Error al subir el archivo";
			msg_download_success = "Archivo descargado correctamente";
			msg_download_error = "Error al descargar el archivo";
			msg_folder_created = "Carpeta creada correctamente";
			msg_permission_denied_title = "Permiso denegado";
			msg_permission_denied_upload = "No tienes permiso para subir archivos";
			msg_select_file = "Por favor, selecciona un archivo";
			msg_permission_denied_download = "No tienes permiso para descargar este archivo";
			msg_permission_denied_delete = "No tienes permiso para eliminar este archivo";
			msg_permission_denied_create_folder = "No tienes permiso para crear carpetas";
			msg_enter_folder_name = "Ingresa un nombre para la carpeta";
			msg_select_folder = "Por favor, selecciona una carpeta";
			msg_permission_denied_delete_folder = "No tienes permiso para eliminar esta carpeta";
			msg_select_folder_not_file = "Debes seleccionar una carpeta, no un archivo";
			msg_permission_denied_rename = "No tienes permiso para renombrar este archivo";

			mail_msg_recipient_obligatory = "El destinatario y el cuerpo del mensaje son obligatorios";
			mail_msg_sent_prefix = "Correo enviado correctamente a ";
			mail_msg_sent_error_recipient = "El mensaje no pudo ser enviado; verifica la direcci�n del destinatario.";
			mail_msg_attached_prefix = "Archivo adjuntado: ";

			logs_filter_actions = " - Acciones";
			logs_filter_users = " - Usuarios";
			logs_filter_dates = " - Fechas";
			logs_filter_results = " - Resultados";

			logs_csv_no_data = "No hay logs registrados en la base de datos.";
			logs_csv_header = "Fecha,Usuario,Acción,Resultado";

			logs_title = "Registros";
			logs_col_action = "Operaci�n";
			logs_col_user = "Usuario";
			logs_col_date = "Fecha";
			logs_col_result = "Resultado";
			logs_btn_export = "Exportar CSV";

			btn_log_actions = "Por Acciones";
			btn_log_users = "Por Usuarios";
			btn_log_dates = "Por Fechas";
			btn_log_results = "Por Resultados";
			logs_dialog_save_title = "Guardar registros";

			msg_confirm_delete_file = "�Quieres borrar el archivo seleccionado?";
			msg_could_not_delete = "No se pudo borrar...";
			msg_confirm_delete_folder = "�Quieres borrar la carpeta seleccionada?";
			msg_folder_not_empty = "La carpeta podr�a no estar vac�a.";
			msg_could_not_create = "No se pudo crear...";

			title_select_upload = "Selecciona el archivo a subir";
			title_select_download = "Selecciona d�nde descargar el archivo";
			msg_enter_new_name = "Ingresa el nuevo nombre para el archivo:";
			msg_invalid_name = "Nombre inv�lido.";
			msg_rename_canceled = "Renombrado cancelado o nombre inv�lido.";

			title_crud_animals = "Gesti�n Serwo - Animales";
			menu_species = "ESPECIES";
			menu_enclosures = "RECINTOS";
			menu_caretakers = "CUIDADORES";
			menu_animals = "ANIMALES";
			menu_transfers = "TRASLADOS";
			menu_species_enclosures = "ESPECIES_RECINTOS";
			menu_elements = "ELEMENTOS";

			msg_removed_prefix = "Eliminados ";
			msg_removed_suffix = " usuarios.";

			col_user = "Usuario";
			col_email = "Email";
			col_roles = "Roles";
			msg_no_roles = "Sin roles asignados";
			msg_role_assigned = "asignado";
			msg_role_unassigned = "desasignado";
			msg_role_already_status_prefix = "El usuario ";
			msg_role_already_status_middle = " ya tiene el rol ";
			msg_role_already_status_suffix = " ";
			col_id = "ID";
			col_role = "Rol";
			col_desc = "Descripcin";
			msg_saved_ok = "Guardado con xito";
			msg_save_error = "Error al guardar";
			msg_updated_ok = "Actualizado con xito";
			msg_update_error = "Error al actualizar";
			msg_confirm_delete = "Est seguro de que desea eliminar este registro?";
			msg_deleted_ok = "Eliminado con xito";
			msg_delete_error = "Error al eliminar";
			gender_male = "Macho";
			gender_female = "Hembra";

		} else {
			// --- INGLS (DEFAULT) ---
			app_title = "Zoo Manager";
			login_title = "Login";
			menu_title = "Main Menu";

			lbl_user = "User:";
			lbl_password = "Password:";
			btn_login = "Log in";

			lbl_select_option = "Select an option";
			btn_manage_data = "Manage Data";
			btn_file_manager = "File Manager";
			btn_mail_controller = "Mail Controller";
			btn_administrate = "Administrate";
			btn_logout = "Log Out";

			file_manager_title = "File Manager";
			file_repo_title = "File Repository";
			file_repo_subtitle = "Manage your server files efficiently";
			btn_upload = "Upload";
			btn_download = "Download";
			btn_delete = "Delete";
			btn_new_folder = "New Folder";
			btn_delete_folder = "Delete Folder";
			btn_rename = "Rename";
			lbl_actions = "Actions";
			lbl_current_path = "Current Path:";
			btn_back = "Back";
			btn_main_menu = "Main Menu";

			btn_new = "New";
			lbl_crud_instructions = "Double click on a record to update or delete it.";

			form_title = "Form";
			btn_save = "Save";
			btn_cancel = "Cancel";

			whitelist_title = "Whitelist Management";
			btn_add = "Add";
			btn_unassign = "Unassign";
			btn_back_whitelist = "Back";
			whitelist_dialog_title = "Add User";
			whitelist_lbl_title = "Add to Whitelist";
			whitelist_lbl_email = "Email:";
			whitelist_lbl_name = "Name:";
			whitelist_col_email = "Email";
			whitelist_col_name = "Name";
			whitelist_col_date = "Registration Date";
			btn_dialog_add = "Add";
			btn_dialog_cancel = "Cancel";

			del_user_title = "Delete User";
			del_user_col_user = "User";
			del_user_col_email = "Email";
			del_btn_delete = "Delete";
			del_btn_back = "Back";
			del_msg_select = "Select a user to delete";
			del_msg_confirm = "Are you sure you want to delete this user?";
			del_msg_success = "User deleted successfully";
			del_msg_error = "Error deleting user";

			mail_title_compose = "Compose new Mail";
			mail_title_check = "Check Mail";
			mail_lbl_for = "For:";
			mail_lbl_from = "From:";
			mail_lbl_subject = "Subject:";
			mail_lbl_message = "Message:";
			btn_send = "Send";
			btn_mark_unread = "Mark unread";
			btn_export = "Export";
			btn_attach = "Attach";

			roles_title_create = "Create Role";
			roles_title_available = "Available Roles";
			roles_btn_create = "Add Role";
			roles_lbl_name = "Name:";
			roles_lbl_desc = "Description:";
			roles_lbl_role = "Role:";
			roles_dialog_title = "Add Role";
			roles_btn_assign = "Assign Role";
			roles_title_assign = "Assign Roles";

			admin_title = "Administration Panel";
			btn_manage_users = "Manage Users";
			btn_manage_roles = "Manage Roles";
			btn_logs = "Logs";
			btn_whitelist = "Whitelist";

			reg_title_window = "User Register";
			reg_title_main = "Register User";
			reg_lbl_name = "Name:";
			reg_lbl_email = "Email Address:";
			reg_lbl_key = "Address Key:";
			reg_lbl_pass = "Password:";
			reg_lbl_conf_pass = "Confirm Password:";
			reg_btn_register = "Register";
			msg_invalid_address_key = "Invalid format for Address Key, it must be: xxxx xxxx xxxx xxxx (all in lowercase)";

			mail_title_inbox = "Inbox";
			mail_btn_compose = "Compose";
			mail_btn_refresh = "Refresh";
			mail_col_subject = "Subject";
			mail_col_from = "From";
			mail_col_date = "Date";

			msg_err_pop3 = "[ERROR POP3] - Could not connect to inbox";

			msg_error_title = "Error";
			msg_success_title = "Success";
			msg_info_title = "Info";
			msg_connection_error = "Could not connect to the server";
			msg_fill_all_fields = "All fields must be filled";
			msg_unexpected_error_prefix = "An unexpected error occurred: ";
			msg_confirm_title = "Confirm";
			dialog_select_action = "Select Action";
			btn_create_new = "Create New";
			btn_sys_update = "Update";
			btn_sys_delete = "Delete";
			btn_sys_cancel = "Cancel";

			msg_not_admin = "You are not the administrator";
			msg_incorrect_creds = "Incorrect credentials";
			msg_err_mail_init = "Error: Mail view not initialized correctly";

			msg_pass_mismatch = "Password doesn't match";
			msg_invalid_email = "Invalid email format";
			msg_user_registered = "User registered correctly";
			msg_user_exists = "User with this name or email already exists";

			msg_no_users_selected = "No users selected to remove.";
			title_warning = "Warning";
			msg_confirm_remove_prefix = "Are you sure you want to remove ";
			msg_confirm_remove_suffix = " user(s) from Whitelist?";
			title_confirm_removal = "Confirm Removal";
			msg_fill_email_name = "Please fill in the Email and Name fields";
			msg_user_exists_whitelist = "User already exists in Whitelist";
			msg_user_added_whitelist = "User added to Whitelist successfully";
			msg_err_adding_user = "Error adding user";

			msg_upload_success = "File uploaded successfully";
			msg_upload_error = "Error uploading the file";
			msg_download_success = "File downloaded successfully";
			msg_download_error = "Error downloading the file";
			msg_folder_created = "Folder created successfully";
			msg_permission_denied_title = "Permission denied";
			msg_permission_denied_upload = "You do not have permission to upload files";
			msg_select_file = "Please select a file";
			msg_permission_denied_download = "You do not have permission to download this file";
			msg_permission_denied_delete = "You do not have permission to delete this file";
			msg_permission_denied_create_folder = "You do not have permission to create folders";
			msg_enter_folder_name = "Please enter a name for the folder";
			msg_select_folder = "Please select a folder";
			msg_permission_denied_delete_folder = "You do not have permission to delete this folder";
			msg_select_folder_not_file = "You must select a folder, not a file";
			msg_permission_denied_rename = "You do not have permission to rename this file";

			msg_confirm_delete_file = "Do you want to delete the selected file?";
			msg_could_not_delete = "Could not be deleted...";
			msg_confirm_delete_folder = "Do you want to delete the selected folder?";
			msg_folder_not_empty = "The folder may not be empty.";
			msg_could_not_create = "Could not be created...";

			mail_msg_recipient_obligatory = "The recipient and the message body are obligatory";
			mail_msg_sent_prefix = "Email successfully sent to ";
			mail_msg_sent_error_recipient = "The message could not be sent; please check the recipient's email address.";
			mail_msg_attached_prefix = "File attached: ";

			title_select_upload = "Select the file to upload";
			title_select_download = "Select where to download the file";
			msg_enter_new_name = "Enter new name for the file:";
			msg_invalid_name = "Invalid name.";
			msg_rename_canceled = "Renaming canceled or invalid name.";

			logs_filter_actions = " - Actions";
			logs_filter_users = " - Users";
			logs_filter_dates = " - Dates";
			logs_filter_results = " - Results";

			logs_csv_no_data = "There aren't logs registered in the database.";
			logs_csv_header = "Date,User,Action,Result";

			btn_log_actions = "By Actions";
			btn_log_users = "By Users";
			btn_log_dates = "By Dates";
			btn_log_results = "By Results";

			logs_title = "Logs";
			logs_col_action = "Operation";
			logs_col_user = "User";
			logs_col_date = "Date";
			logs_col_result = "Result";
			logs_btn_export = "Export CSV";
			logs_dialog_save_title = "Save logs";

			title_crud_animals = "Serwo Management - Animals";
			menu_species = "Species";
			menu_enclosures = "Enclosures";
			menu_caretakers = "Caretakers";
			menu_animals = "Animals";
			menu_transfers = "Transfers";
			menu_species_enclosures = "Species-Enclosures";
			menu_elements = "Elements";

			msg_removed_prefix = "Removed ";
			msg_removed_suffix = " users.";

			col_user = "User";
			col_email = "Email";
			col_roles = "Roles";
			msg_no_roles = "No roles assigned";
			msg_role_assigned = "assigned";
			msg_role_unassigned = "unassigned";
			msg_role_already_status_prefix = "The user ";
			msg_role_already_status_middle = " already has the role ";
			msg_role_already_status_suffix = " ";
			col_id = "ID";
			col_role = "Role";
			col_desc = "Description";
			msg_saved_ok = "Saved successfully";
			msg_save_error = "Error while saving";
			msg_updated_ok = "Updated successfully";
			msg_update_error = "Error while updating";
			msg_confirm_delete = "Are you sure you want to delete this record?";
			msg_deleted_ok = "Deleted successfully";
			msg_delete_error = "Error while deleting";
			gender_male = "Male";
			gender_female = "Female";
			msg_role_name_empty = "The role name cannot be empty";
			msg_role_desc_empty = "The role description cannot be empty";
		}
	}
}