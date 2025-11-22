def call() {
    def yaml = libraryResource("config.yaml")
    return readYaml(text: yaml)
}
