package io.fouad.backend.tests;

import com.github.dockerjava.api.command.InspectContainerResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.ext.ScriptUtils;
import org.testcontainers.mssqlserver.MSSQLServerContainer;
import org.testcontainers.utility.DockerImageName;

import java.nio.file.Files;
import java.nio.file.Path;

public class MssqlServerContainer extends MSSQLServerContainer {

    private static final Logger LOGGER = LoggerFactory.getLogger(MssqlServerContainer.class);

    public static final int MSSQL_SERVER_PORT = 4567;
    private static final String MSSQL_DOCKER_IMAGE_NAME = System.getProperty("azureSqlEdgeImage");
    private static final String PRE_DDL_SQL_FILE_RELATIVE_PATH = "./src/main/sql/pre-ddl.sql";
    private static final String DDL_SQL_FILE_RELATIVE_PATH = "./src/main/sql/ddl.sql";
    private static final String DML_SQL_FILE_RELATIVE_PATH = "./src/main/sql/dml.sql";

    @SuppressWarnings("resource")
    public MssqlServerContainer() {
        super(DockerImageName.parse(MSSQL_DOCKER_IMAGE_NAME)
                             .asCompatibleSubstituteFor("mcr.microsoft.com/mssql/server"));
        addEnv("ACCEPT_EULA", "Y");
        addExposedPort(1433);
        addFixedExposedPort(MSSQL_SERVER_PORT, 1433);
        withPassword("P@ssw0rd");
        withReuse(true);
    }
    
    @Override
    protected void containerIsStarted(InspectContainerResponse containerInfo) {
        super.containerIsStarted(containerInfo);

        try {
            long initStartTimeNanos = System.nanoTime();
            LOGGER.info("Initializing DB...");

            String preDdlSql = Files.readString(Path.of(PRE_DDL_SQL_FILE_RELATIVE_PATH));
            ScriptUtils.executeDatabaseScript(getDatabaseDelegate(), PRE_DDL_SQL_FILE_RELATIVE_PATH, preDdlSql);

            String ddlSql = Files.readString(Path.of(DDL_SQL_FILE_RELATIVE_PATH));
            ScriptUtils.executeDatabaseScript(getDatabaseDelegate(), DDL_SQL_FILE_RELATIVE_PATH, ddlSql);

            String dmlSql = Files.readString(Path.of(DML_SQL_FILE_RELATIVE_PATH));
            ScriptUtils.executeDatabaseScript(getDatabaseDelegate(), DML_SQL_FILE_RELATIVE_PATH, dmlSql);

            long initElapsedNanos = System.nanoTime() - initStartTimeNanos;
            LOGGER.info("Done initializing DB in %.2f ms!".formatted(initElapsedNanos / 1_000_000.00));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}