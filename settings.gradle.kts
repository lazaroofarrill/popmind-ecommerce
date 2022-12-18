
rootProject.name = "ecommerce"
include("projects:backend:api")
findProject(":projects:backend:api")?.name = "api"
