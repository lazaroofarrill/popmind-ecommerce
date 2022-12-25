package com.espoletatecnologias.api.framework.common.exceptions

class DalInsertError(override val message: String = "Error inserting record") : Error(message)
