A modified version of The Expanse 3.0, increased build and generation height

## NeoForm troubleshooting

If NeoForm's `createMinecraftArtifacts` task fails or appears to rerun unnecessarily:

1. Stop Gradle daemons with `./gradlew --stop`.
2. Remove only the NeoForm caches:
   - `%USERPROFILE%\.gradle\caches\neoform`
   - `%USERPROFILE%\.gradle\caches\neoforge`
3. Re-run the desired Gradle task.
