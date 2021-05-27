# DataManagement_API
Une Api dev par Sothis_

Bon je vais expliquer vite fais comment la rajouter a ton plugin

D'abord tu telecharge le code et tu met dans ton plugin et apres tu suis ce que j'ai mit en dessous

# Main

D'abord mettez ça au minimum dans votre Main de votre plugin

```java
private static Main INSTANCE;

    @Override
    public void onEnable() {
        INSTANCE = this;
        DataManagement.init();
        getCommand("datatransfert").setExecutor(new DataManagementAdmin());
    }

    public static Main getINSTANCE() {
        return INSTANCE;
    }

    @Override
    public void onDisable() {
        DataManagement.close();
    }
```

# Maven

Si t'es pas un blaireau tu utilises Maven parce que c'est largement plus partique pour les librairies :)

```xml
    <properties>
        <spigot.version>1.16.5-R0.1-SNAPSHOT</spigot.version>
        <HikariCP.version>4.0.3</HikariCP.version>
        <slf4j-api.version>1.7.5</slf4j-api.version>
        <redisson.version>3.15.4</redisson.version>
        <jackson-core.version>2.12.3</jackson-core.version>
        <jackson-databind.version>2.12.3</jackson-databind.version>
        <jackson-annotations.version>2.12.3</jackson-annotations.version>
        <jackson-dataformat-yaml.version>2.12.3</jackson-dataformat-yaml.version>
    </properties>
    
    <dependencies>
        <dependency>
            <groupId>org.spigotmc</groupId>
            <artifactId>spigot</artifactId>
            <version>${spigot.version}</version>
            <scope>provided</scope>
        </dependency>
        <dependency>
            <groupId>com.zaxxer</groupId>
            <artifactId>HikariCP</artifactId>
            <version>${HikariCP.version}</version>
        </dependency>
        <dependency>
            <groupId>org.slf4j</groupId>
            <artifactId>slf4j-api</artifactId>
            <version>${slf4j-api.version}</version>
        </dependency>
        <dependency>
            <groupId>org.redisson</groupId>
            <artifactId>redisson</artifactId>
            <version>${redisson.version}</version>
        </dependency>
        <dependency>
            <groupId>com.fasterxml.jackson.core</groupId>
            <artifactId>jackson-core</artifactId>
            <version>${jackson-core.version}</version>
        </dependency>
        <dependency>
            <groupId>com.fasterxml.jackson.core</groupId>
            <artifactId>jackson-databind</artifactId>
            <version>${jackson-databind.version}</version>
        </dependency>
        <dependency>
            <groupId>com.fasterxml.jackson.core</groupId>
            <artifactId>jackson-annotations</artifactId>
            <version>${jackson-annotations.version}</version>
        </dependency>
        <dependency>
            <groupId>com.fasterxml.jackson.dataformat</groupId>
            <artifactId>jackson-dataformat-yaml</artifactId>
            <version>${jackson-dataformat-yaml.version}</version>
        </dependency>
    </dependencies>
```

# Plugin.yml

Pour finir n'oublie pas de rajoutez ça dans ton plugin .yml

```yml
commands:
  datatransfert:
    description: ""
    usage: ""
```

Si il y a un probleme viens me voir en MP sur Discord
