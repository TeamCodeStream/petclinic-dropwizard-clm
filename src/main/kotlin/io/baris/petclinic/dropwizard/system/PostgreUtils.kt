package io.baris.petclinic.dropwizard.system

import org.jdbi.v3.core.Handle
import org.jdbi.v3.core.Jdbi
import java.util.*

/**
 * Utilities for Postgre database
 */
object PostgreUtils {
    fun applySqlScript(jdbi: Jdbi, path: String) {
        val tables = PetClinicUtils.readFileToString(path)
        jdbi.withHandle<Int, RuntimeException> { handle: Handle ->
            Arrays.stream(tables.split(";".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray())
                .forEach { sql: String? ->
                    handle.execute(
                        sql
                    )
                }
            1
        }
    }
}
