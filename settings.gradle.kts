rootProject.name = "flypass-demo"

include(":application")
project(":application").projectDir = file("./application")

include(":domain")
project(":domain").projectDir = file("./domain")

include(":rest-api")
project(":rest-api").projectDir = file("./infrastructure/entry-points/rest-api")

include(":repository")
project(":repository").projectDir = file("./infrastructure/driven-adapters/repository")

include(":security")
project(":security").projectDir = file("./infrastructure/driven-adapters/security")
