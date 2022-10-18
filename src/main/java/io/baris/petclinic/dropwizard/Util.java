package io.baris.petclinic.dropwizard;

/**
 * Simple method that waits so the spans take some time to execute.
 */
public class Util {
	public static void doWait() {
		doWait(200);
	}

	public static void doWait(long timeout) {
		try {
			Thread.sleep(timeout);
		} catch (InterruptedException ex) {
			// ignore
		}
	}
}
