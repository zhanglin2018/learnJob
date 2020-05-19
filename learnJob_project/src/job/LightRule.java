package job;

import org.eclipse.core.runtime.jobs.ISchedulingRule;

public class LightRule implements ISchedulingRule {

	@Override
	public boolean contains(ISchedulingRule rule) {
		return this.equals(rule);
	}

	@Override
	public boolean isConflicting(ISchedulingRule rule) {
		return this.equals(rule);
	}
}
