package io.baris.petclinic.dropwizard

/**
 * Simple method that waits so the spans take some time to execute.
 */
object Util {
    @JvmOverloads
    fun doWait(timeout: Long = 200) {
        try {
            Thread.sleep(timeout)
        } catch (ex: InterruptedException) {
            // ignore
        }
    }
}
