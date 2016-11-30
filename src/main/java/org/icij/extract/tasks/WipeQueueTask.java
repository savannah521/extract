package org.icij.extract.tasks;

import org.icij.extract.core.PathQueue;
import org.icij.extract.tasks.factories.PathQueueFactory;
import org.icij.task.DefaultTask;
import org.icij.task.annotation.Option;
import org.icij.task.annotation.Task;

/**
 * CLI class for wiping a {@link PathQueue} from the backend.
 *
 * @author Matthew Caruana Galizia <mcaruana@icij.org>
 * @since 1.0.0-beta
 */
@Task("Wipe a queue. The name option is respected.")
@Option(name = "queue-type", description = "Set the report backend type. For now, the only valid value is " +
		"\"redis\".", parameter = "type", code = "r")
@Option(name = "queue-name", description = "The name of the report, the default of which is type-dependent" +
		".", parameter = "name")
@Option(name = "redis-address", description = "Set the Redis backend address. Defaults to " +
		"127.0.0.1:6379.", parameter = "address")
public class WipeQueueTask extends DefaultTask<Integer> {

	@Override
	public Integer run() throws Exception {
		final int cleared;

		try (final PathQueue queue = new PathQueueFactory(options).createShared()) {
			cleared = queue.size();
			queue.clear();
		} catch (Exception e) {
			throw new RuntimeException("Exception while wiping queue.", e);
		}

		return cleared;
	}
}
