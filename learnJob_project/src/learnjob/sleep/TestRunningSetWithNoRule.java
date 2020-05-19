package learnjob.sleep;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.internal.jobs.JobManager;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.core.runtime.jobs.Job;

import job.LightOnJobWithNonRull;
import learnJob.DebugOptionImp;
import learnJob.JobManagerUtil;

public class TestRunningSetWithNoRule {
	public static void main(String[] args) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		((JobManager)(Job.getJobManager())).optionsChanged(new DebugOptionImp());
		testDifferentInstance();
	}

	private static void testDifferentInstance() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		LightOnJobWithNonRull lightOn1 = new LightOnJobWithNonRull();
		LightOnJobWithNonRull lightOn2 = new LightOnJobWithNonRull();
		LightOnJobWithNonRull lightOn3 = new LightOnJobWithNonRull();
		
		lightOn1.schedule();
		JobManagerUtil.printAllJobs();
		
		lightOn2.schedule();
		JobManagerUtil.printAllJobs();

		lightOn3.schedule();
		JobManagerUtil.printAllJobs();
	}
}
