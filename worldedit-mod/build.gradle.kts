import net.fabricmc.loom.task.RemapJarTask

plugins {
    base
}

applyCommonConfiguration()

tasks.register<Jar>("jar") {
    dependsOn(
        project(":worldedit-forge").tasks.named("reobfShadowJar")
    )
    from(zipTree({project(":worldedit-forge").tasks.getByName("shadowJar").outputs.files.singleFile}))

    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    archiveClassifier.set("dist")
}

tasks.named("assemble") {
    dependsOn("jar")
}
