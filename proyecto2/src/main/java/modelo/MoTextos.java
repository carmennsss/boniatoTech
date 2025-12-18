package modelo;

public class MoTextos {
    // General
    public static String app_title = "Serwo Manager";
    public static String login_title = "Login";
    public static String menu_title = "Main Menu";

    // Login
    public static String lbl_user = "User:";
    public static String lbl_password = "Password:";
    public static String btn_login = "Log in";

    // Menu Principal
    public static String lbl_select_option = "Select an option";
    public static String btn_manage_data = "Manage Data";
    public static String btn_file_manager = "File Manager";
    public static String btn_mail_controller = "Mail Controller";
    public static String btn_administrate = "Administrate";
    public static String btn_logout = "Log Out";

    // Gestor Archivos
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
    public static String btn_back = "Back";
    public static String btn_main_menu = "Main Menu";

    // CRUD
    public static String btn_new = "New";
    public static String lbl_crud_instructions = "Double click on a record to update or delete it.";

    public static int getIdioma() {
        return idiomaActual;
    }

    // Language state
    private static int idiomaActual = 0; // 0 = EN, 1 = ES

    // Forms
    public static String form_title = "Formulario";
    public static String btn_save = "Guardar";
    public static String btn_cancel = "Cancelar";

    // Whitelist
    public static String whitelist_title = "Gestión de Whitelist";
    public static String btn_add = "Añadir";
    public static String btn_unassign = "Desasignar";
    public static String btn_back_whitelist = "Volver";
    public static String whitelist_dialog_title = "Agregar Usuario";
    public static String whitelist_lbl_title = "Agregar a Whitelist";
    public static String whitelist_lbl_email = "Email:";
    public static String whitelist_lbl_name = "Nombre:";
    public static String btn_dialog_add = "Agregar";
    public static String btn_dialog_cancel = "Cancelar";

    // Mail
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

    // Roles
    public static String roles_title_create = "Crear Rol";
    public static String roles_title_available = "Roles disponibles";
    public static String roles_btn_create = "Agregar Rol";
    public static String roles_lbl_name = "Nombre:";
    public static String roles_lbl_desc = "Descripción:";
    public static String roles_lbl_role = "Rol:";
    public static String roles_dialog_title = "Agregar Rol";
    public static String roles_btn_assign = "Asignar Rol";
    public static String roles_title_assign = "Asignar Roles";

    // Admin
    public static String admin_title = "Panel de Administración";
    public static String btn_manage_users = "Gestión de Usuarios";
    public static String btn_manage_roles = "Gestión de Roles";

    // Register
    public static String reg_title_window = "Registro de Usuario";
    public static String reg_title_main = "Registrar Usuario";
    public static String reg_lbl_name = "Nombre:";
    public static String reg_lbl_email = "Email:";
    public static String reg_lbl_key = "Clave del Correo:";
    public static String reg_lbl_pass = "Contraseña:";
    public static String reg_lbl_conf_pass = "Confirmar Contraseña:";
    public static String reg_btn_register = "Registrar";

    // Mail
    public static String mail_title_inbox = "Bandeja de Entrada";
    public static String mail_btn_compose = "Redactar";
    public static String mail_btn_refresh = "Actualizar";
    public static String mail_col_subject = "Asunto";
    public static String mail_col_from = "De";
    public static String mail_col_date = "Fecha";

    // Messages - General
    public static String msg_error_title = "Error";
    public static String msg_success_title = "Success";
    public static String msg_connection_error = "Could not connect to the server";
    public static String msg_fill_all_fields = "All fields must be filled";
    public static String msg_unexpected_error_prefix = "An unexpected error occurred: ";
    public static String msg_confirm_title = "Confirm";
    public static String dialog_select_action = "Select Action";
    public static String btn_create_new = "Create New";
    public static String btn_sys_update = "Update";
    public static String btn_sys_delete = "Delete";
    public static String btn_sys_cancel = "Cancel";

    // Messages - Login/Auth
    public static String msg_not_admin = "You are not an administrator";
    public static String msg_incorrect_creds = "Incorrect credentials";
    public static String msg_err_mail_init = "Error: Mail view has not been initialized correctly";

    // Messages - User Registration
    public static String msg_pass_mismatch = "Passwords do not match";
    public static String msg_invalid_email = "Invalid email format";
    public static String msg_user_registered = "User registered successfully";
    public static String msg_user_exists = "A user with this name or email already exists";

    // Messages - Whitelist
    public static String msg_fill_email_name = "Please fill in the Email and Name fields";
    public static String msg_user_exists_whitelist = "User already exists in Whitelist";
    public static String msg_user_added_whitelist = "User added to Whitelist successfully";
    public static String msg_err_adding_user = "Error adding user";

    // Messages - Files
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

    // Mail Messages
    public static String mail_msg_recipient_obligatory = "The recipient and the message body are obligatory";
    public static String mail_msg_sent_prefix = "Email successfully sent to ";
    public static String mail_msg_sent_error_recipient = "The message could not be sent; please check the recipient's email address.";
    public static String mail_msg_attached_prefix = "File attached: ";

    // Logs
    public static String logs_title = "Logs";
    public static String logs_col_action = "Action";
    public static String logs_col_user = "User";
    public static String logs_col_date = "Date";
    public static String logs_col_result = "Result";
    public static String logs_btn_export = "Export CSV";
    public static String logs_dialog_save_title = "Save logs";

    // Delete User
    public static String del_user_title = "Delete User";
    public static String del_user_col_user = "User";
    public static String del_user_col_email = "Email";
    public static String del_btn_delete = "Delete";
    public static String del_btn_back = "Back";
    public static String del_msg_select = "Select a user to delete";
    public static String del_msg_confirm = "Are you sure you want to delete this user?";
    public static String del_msg_success = "User deleted successfully";
    public static String del_msg_error = "Error deleting user";

    // Messages - Mail
    public static String msg_err_pop3 = "[ERROR POP3] - Could not connect to inbox";

    public static void setIdioma(int idioma) {
        idiomaActual = idioma;
        if (idioma == 1) { // SPANISH
            app_title = "Gestor de Serwo";
            login_title = "Iniciar Sesión";
            menu_title = "Menú Principal";

            lbl_user = "Usuario:";
            lbl_password = "Clave:";
            btn_login = "Entrar";

            lbl_select_option = "Seleccione una opción";
            btn_manage_data = "Gestión de Datos";
            btn_file_manager = "Gestor de Archivos";
            btn_mail_controller = "Gestor de Correo";
            btn_administrate = "Administrar";
            btn_logout = "Cerrar Sesión";

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
            btn_back = "Volver";
            btn_main_menu = "Menú Principal";

            btn_new = "Nuevo";
            lbl_crud_instructions = "Doble click en un registro para actualizar o borrar.";

            form_title = "Formulario";
            btn_save = "Guardar";
            btn_cancel = "Cancelar";

            whitelist_title = "Gestión de Whitelist";
            btn_add = "Añadir";
            btn_unassign = "Quitar";
            btn_back_whitelist = "Volver";
            whitelist_dialog_title = "Agregar Usuario";
            whitelist_lbl_title = "Agregar a Whitelist";
            whitelist_lbl_email = "Email:";
            whitelist_lbl_name = "Nombre:";
            btn_dialog_add = "Agregar";
            btn_dialog_cancel = "Cancelar";

            del_user_title = "Eliminar Usuario";
            del_user_col_user = "Usuario";
            del_user_col_email = "Correo";
            del_btn_delete = "Eliminar";
            del_btn_back = "Volver";
            del_msg_select = "Seleccione un usuario para eliminar";
            del_msg_confirm = "¿Estás seguro de que deseas eliminar este usuario?";
            del_msg_success = "Usuario eliminado correctamente";
            del_msg_error = "Error al eliminar usuario";

            mail_title_compose = "Redactar Nuevo Correo";
            mail_title_check = "Leer Correo";
            mail_lbl_for = "Para:";
            mail_lbl_from = "De:";
            mail_lbl_subject = "Asunto:";
            mail_lbl_message = "Mensaje:";
            btn_send = "Enviar";
            btn_mark_unread = "Marcar no leído";
            btn_export = "Exportar";
            btn_attach = "Adjuntar";

            roles_title_create = "Crear Rol";
            roles_title_available = "Roles disponibles";
            roles_btn_create = "Agregar Rol";
            roles_lbl_name = "Nombre:";
            roles_lbl_desc = "Descripción:";
            roles_lbl_role = "Rol:";
            roles_dialog_title = "Agregar Rol";
            roles_btn_assign = "Asignar Rol";
            roles_title_assign = "Asignar Roles";

            admin_title = "Panel de Administración";
            btn_manage_users = "Gestión de Usuarios";
            btn_manage_roles = "Gestión de Roles";

            reg_title_window = "Registro de Usuario";
            reg_title_main = "Registrar Usuario";
            reg_lbl_name = "Nombre:";
            reg_lbl_email = "Email:";
            reg_lbl_key = "Clave del Correo:";
            reg_lbl_pass = "Contraseña:";
            reg_lbl_conf_pass = "Confirmar Contraseña:";
            reg_btn_register = "Registrar";

            mail_title_inbox = "Bandeja de Entrada";
            mail_btn_compose = "Redactar";
            mail_btn_refresh = "Actualizar";
            mail_col_subject = "Asunto";
            mail_col_from = "De";
            mail_col_date = "Fecha";

            msg_err_pop3 = "[ERROR POP3] - No se pudo conectar a la bandeja de entrada";

            msg_error_title = "Error";
            msg_success_title = "Éxito";
            msg_connection_error = "No se pudo conectar al servidor";
            msg_fill_all_fields = "Todos los campos deben ser rellenados";
            msg_unexpected_error_prefix = "Ocurrió un error inesperado: ";
            msg_confirm_title = "Confirmar";
            dialog_select_action = "Seleccionar Acción";
            btn_create_new = "Crear Nuevo";
            btn_sys_update = "Actualizar";
            btn_sys_delete = "Eliminar";
            btn_sys_cancel = "Cancelar";

            msg_not_admin = "No eres el administrador";
            msg_incorrect_creds = "Credenciales incorrectas";
            msg_err_mail_init = "Error: La vista de correos no se ha inicializado correctamente";

            msg_pass_mismatch = "Las contraseñas no coinciden";
            msg_invalid_email = "Formato de correo inválido";
            msg_user_registered = "Usuario registrado correctamente";
            msg_user_exists = "Ya existe un usuario con este nombre o correo";

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

            mail_msg_recipient_obligatory = "El destinatario y el cuerpo del mensaje son obligatorios";
            mail_msg_sent_prefix = "Correo enviado correctamente a ";
            mail_msg_sent_error_recipient = "El mensaje no pudo ser enviado; verifica la dirección del destinatario.";
            mail_msg_attached_prefix = "Archivo adjuntado: ";

            logs_title = "Registros";
            logs_col_action = "Acción";
            logs_col_user = "Usuario";
            logs_col_date = "Fecha";
            logs_col_result = "Resultado";
            logs_btn_export = "Exportar CSV";
            logs_dialog_save_title = "Guardar registros";

        } else { // ENGLISH (Default)
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
            lbl_actions = "Actions";
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

            reg_title_window = "User Register";
            reg_title_main = "Register User";
            reg_lbl_name = "Name:";
            reg_lbl_email = "Email Address:";
            reg_lbl_key = "Address Key:";
            reg_lbl_pass = "Password:";
            reg_lbl_conf_pass = "Confirm Password:";
            reg_btn_register = "Register";

            mail_title_inbox = "Inbox";
            mail_btn_compose = "Compose";
            mail_btn_refresh = "Refresh";
            mail_col_subject = "Subject";
            mail_col_from = "From";
            mail_col_date = "Date";

            msg_err_pop3 = "[ERROR POP3] - Could not connect to inbox";

            msg_error_title = "Error";
            msg_success_title = "Success";
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

            msg_fill_email_name = "Please fill in both Email and Name fields";
            msg_user_exists_whitelist = "User with this email already exists in Whitelist";
            msg_user_added_whitelist = "User added successfully to Whitelist";
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

            mail_msg_recipient_obligatory = "The recipient and the message body are obligatory";
            mail_msg_sent_prefix = "Email successfully sent to ";
            mail_msg_sent_error_recipient = "The message could not be sent; please check the recipient's email address.";
            mail_msg_attached_prefix = "File attached: ";

            logs_title = "Logs";
            logs_col_action = "Action";
            logs_col_user = "User";
            logs_col_date = "Date";
            logs_col_result = "Result";
            logs_btn_export = "Export CSV";
            logs_dialog_save_title = "Save logs";
        }
    }
}
