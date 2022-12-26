package com.espoletatecnologias.api.framework.common.exceptions

class DalInsertError(override val message: String = "Error inserting record") :
    Error(message)

class DalUpdateError(override val message: String = "Error updating record") :
    Error(message)

class DalWrongColumnContent(override val message: String = "The contents loaded don't match with entity") :
    Error(message)

class DalReadError(override val message: String = "Error specifying the read query") :
    Error(message)
